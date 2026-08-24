package ro.hubstudentesc.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.auth.UserRecordDto;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRecordDto toDto(User user);

    List<UserRecordDto> toDto(List<User> users);

    User toEntity(UserRecordDto dto);
}
