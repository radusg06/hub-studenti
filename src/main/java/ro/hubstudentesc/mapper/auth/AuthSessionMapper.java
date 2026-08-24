package ro.hubstudentesc.mapper.auth;

import org.mapstruct.Mapper;
import ro.hubstudentesc.dto.auth.AuthSessionRecordDto;
import ro.hubstudentesc.persistence.entity.auth.AuthSession;

@Mapper(componentModel = "spring")
public interface AuthSessionMapper {
    AuthSessionRecordDto toDto(AuthSession authSession);
}
