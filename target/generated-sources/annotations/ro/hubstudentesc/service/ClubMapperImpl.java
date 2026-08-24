package ro.hubstudentesc.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.ClubRecordDto;
import ro.hubstudentesc.persistence.entity.Club;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class ClubMapperImpl implements ClubMapper {

    @Override
    public ClubRecordDto toDto(Club club) {
        if ( club == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String faculty = null;
        Integer membersCount = null;

        id = club.getId();
        name = club.getName();
        description = club.getDescription();
        faculty = club.getFaculty();
        membersCount = club.getMembersCount();

        ClubRecordDto clubRecordDto = new ClubRecordDto( id, name, description, faculty, membersCount );

        return clubRecordDto;
    }

    @Override
    public List<ClubRecordDto> toDto(List<Club> clubs) {
        if ( clubs == null ) {
            return null;
        }

        List<ClubRecordDto> list = new ArrayList<ClubRecordDto>( clubs.size() );
        for ( Club club : clubs ) {
            list.add( toDto( club ) );
        }

        return list;
    }

    @Override
    public Club toEntity(ClubRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Club club = new Club();

        club.setId( dto.id() );
        club.setName( dto.name() );
        club.setDescription( dto.description() );
        club.setFaculty( dto.faculty() );
        club.setMembersCount( dto.membersCount() );

        return club;
    }
}
