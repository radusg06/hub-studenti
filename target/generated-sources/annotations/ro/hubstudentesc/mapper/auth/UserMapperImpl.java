package ro.hubstudentesc.mapper.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.auth.UserRecordDto;
import ro.hubstudentesc.enums.authEnums.UserRole;
import ro.hubstudentesc.persistence.entity.auth.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRecordDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String givenName = null;
        String familyName = null;
        String sub = null;
        String email = null;
        String picture = null;
        UserRole role = null;
        Boolean isVerified = null;
        Boolean isActive = null;

        id = user.getId();
        givenName = user.getGivenName();
        familyName = user.getFamilyName();
        sub = user.getSub();
        email = user.getEmail();
        picture = user.getPicture();
        role = user.getRole();
        isVerified = user.getIsVerified();
        isActive = user.getIsActive();

        UserRecordDto userRecordDto = new UserRecordDto( id, givenName, familyName, sub, email, picture, role, isVerified, isActive );

        return userRecordDto;
    }

    @Override
    public List<UserRecordDto> toDto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserRecordDto> list = new ArrayList<UserRecordDto>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public User toEntity(UserRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.id() );
        user.setSub( dto.sub() );
        user.setEmail( dto.email() );
        user.setGivenName( dto.givenName() );
        user.setFamilyName( dto.familyName() );
        user.setPicture( dto.picture() );
        user.setRole( dto.role() );
        user.setIsVerified( dto.isVerified() );
        user.setIsActive( dto.isActive() );

        return user;
    }
}
