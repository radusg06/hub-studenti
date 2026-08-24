package ro.hubstudentesc.mapper.auth;

import org.mapstruct.Mapper;
import ro.hubstudentesc.dto.auth.UserCredentialRecordDto;
import ro.hubstudentesc.persistence.entity.auth.UserCredential;

@Mapper(componentModel = "spring")
public interface UserCredentialMapper {

    UserCredentialRecordDto toDto(UserCredential userCredential);
}
