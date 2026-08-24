package ro.hubstudentesc.mapper.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.auth.UserCredentialRecordDto;
import ro.hubstudentesc.persistence.entity.auth.UserCredential;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class UserCredentialMapperImpl implements UserCredentialMapper {

    @Override
    public UserCredentialRecordDto toDto(UserCredential userCredential) {
        if ( userCredential == null ) {
            return null;
        }

        UUID userId = null;
        String algorithm = null;
        Integer passwordVersion = null;
        Boolean mustChange = null;
        LocalDateTime passwordChangedAt = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        userId = userCredential.getUserId();
        algorithm = userCredential.getAlgorithm();
        passwordVersion = userCredential.getPasswordVersion();
        mustChange = userCredential.getMustChange();
        passwordChangedAt = userCredential.getPasswordChangedAt();
        createdAt = userCredential.getCreatedAt();
        updatedAt = userCredential.getUpdatedAt();

        UserCredentialRecordDto userCredentialRecordDto = new UserCredentialRecordDto( userId, algorithm, passwordVersion, mustChange, passwordChangedAt, createdAt, updatedAt );

        return userCredentialRecordDto;
    }
}
