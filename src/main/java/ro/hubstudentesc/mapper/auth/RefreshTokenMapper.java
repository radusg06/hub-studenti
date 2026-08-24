package ro.hubstudentesc.mapper.auth;

import org.mapstruct.Mapper;
import ro.hubstudentesc.dto.auth.RefreshTokenRecordDto;
import ro.hubstudentesc.persistence.entity.auth.RefreshToken;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenRecordDto toDto(RefreshToken refreshToken);
}
