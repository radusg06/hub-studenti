package ro.hubstudentesc.service.socialmedia;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MediaStorageService {

    private final BlobContainerClient containerClient;

    public MediaStorageService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName
    ) {
        this.containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        if (!containerClient.exists()) {
            containerClient.create();
        }
    }

    public String upload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Fișierul este gol");
        }

        String fileName = UUID.randomUUID()
                + "_"
                + file.getOriginalFilename();

        BlobClient blobClient =
                containerClient.getBlobClient(fileName);

        try {
            blobClient.upload(
                    file.getInputStream(),
                    file.getSize(),
                    true
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Eroare la încărcarea fișierului",
                    e
            );
        }

        return blobClient.getBlobUrl();
    }
}