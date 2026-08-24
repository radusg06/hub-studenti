package ro.hubstudentesc.mapper.auth;

import org.mapstruct.Mapper;
import ro.hubstudentesc.dto.auth.IssuedTokenRecordDto;
import ro.hubstudentesc.persistence.entity.auth.IssuedToken;

@Mapper(componentModel = "spring")
public interface IssuedTokenMapper {
    IssuedTokenRecordDto toDto(IssuedToken issuedToken);
}
