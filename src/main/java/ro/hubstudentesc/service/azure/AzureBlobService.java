package ro.hubstudentesc.service.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AzureBlobService {

    private final BlobServiceClient blobServiceClient;
    private final String postMediaContainerName;
    private final String marketplaceContainerName;

    public AzureBlobService(
            @Value("${azure.storage.connection-string}")
            String connectionString,

            @Value("${azure.storage.container-name}")
            String postMediaContainerName,

            @Value("${azure.storage.marketplace-container-name}")
            String marketplaceContainerName
    ) {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        this.postMediaContainerName = postMediaContainerName;
        this.marketplaceContainerName = marketplaceContainerName;
    }

    /**
     * Genereaza un nume unic pentru un material PDF Marketplace.
     */
    public String generateMarketplacePdfBlobName() {
        return "materials/" + UUID.randomUUID() + ".pdf";
    }

    /**
     * Incarca un PDF in containerul privat Marketplace.
     *
     * Returneaza numele blob-ului, nu un URL public.
     */
    public String uploadMarketplacePdf(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Fisierul PDF este obligatoriu"
            );
        }

        String contentType = file.getContentType();

        if (contentType == null
                || !contentType.equalsIgnoreCase("application/pdf")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Este permis doar formatul PDF"
            );
        }

        String blobName = generateMarketplacePdfBlobName();

        BlobContainerClient containerClient =
                blobServiceClient.getBlobContainerClient(
                        marketplaceContainerName
                );

        BlobClient blobClient =
                containerClient.getBlobClient(blobName);

        try {
            blobClient.upload(
                    file.getInputStream(),
                    file.getSize(),
                    true
            );

            blobClient.setHttpHeaders(
                    new BlobHttpHeaders()
                            .setContentType("application/pdf")
                            .setContentDisposition(
                                    "attachment; filename=\"material.pdf\""
                            )
            );

            return blobName;

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Nu s-a putut incarca fisierul PDF in Azure Blob Storage",
                    e
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Eroare la comunicarea cu Azure Blob Storage",
                    e
            );
        }
    }

    /**
     * Genereaza un URL SAS temporar pentru citirea unui PDF.
     */
    public String generateMarketplaceReadSasUrl(
            String blobName,
            int validForMinutes
    ) {
        if (blobName == null || blobName.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Numele blob-ului este obligatoriu"
            );
        }

        if (validForMinutes <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Durata URL-ului SAS trebuie sa fie pozitiva"
            );
        }

        BlobContainerClient containerClient =
                blobServiceClient.getBlobContainerClient(
                        marketplaceContainerName
                );

        BlobClient blobClient =
                containerClient.getBlobClient(blobName);

        if (!blobClient.exists()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Fisierul PDF nu exista in Azure Blob Storage"
            );
        }

        BlobSasPermission permission = new BlobSasPermission()
                .setReadPermission(true);

        OffsetDateTime expiry =
                OffsetDateTime.now().plusMinutes(validForMinutes);

        BlobServiceSasSignatureValues values =
                new BlobServiceSasSignatureValues(
                        expiry,
                        permission
                );

        String sasToken = blobClient.generateSas(values);

        return blobClient.getBlobUrl() + "?" + sasToken;
    }

    public BlobContainerClient getMarketplaceContainerClient() {
        return blobServiceClient.getBlobContainerClient(
                marketplaceContainerName
        );
    }

    public BlobContainerClient getPostMediaContainerClient() {
        return blobServiceClient.getBlobContainerClient(
                postMediaContainerName
        );
    }
}