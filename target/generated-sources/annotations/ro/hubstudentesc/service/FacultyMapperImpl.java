package ro.hubstudentesc.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.FacultyRecordDto;
import ro.hubstudentesc.persistence.entity.Faculty;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class FacultyMapperImpl implements FacultyMapper {

    @Override
    public FacultyRecordDto toDto(Faculty faculty) {
        if ( faculty == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String university = null;
        String city = null;
        Double annualFee = null;
        Integer ranking = null;

        id = faculty.getId();
        name = faculty.getName();
        university = faculty.getUniversity();
        city = faculty.getCity();
        annualFee = faculty.getAnnualFee();
        ranking = faculty.getRanking();

        FacultyRecordDto facultyRecordDto = new FacultyRecordDto( id, name, university, city, annualFee, ranking );

        return facultyRecordDto;
    }

    @Override
    public List<FacultyRecordDto> toDto(List<Faculty> faculties) {
        if ( faculties == null ) {
            return null;
        }

        List<FacultyRecordDto> list = new ArrayList<FacultyRecordDto>( faculties.size() );
        for ( Faculty faculty : faculties ) {
            list.add( toDto( faculty ) );
        }

        return list;
    }

    @Override
    public Faculty toEntity(FacultyRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Faculty faculty = new Faculty();

        faculty.setId( dto.id() );
        faculty.setName( dto.name() );
        faculty.setUniversity( dto.university() );
        faculty.setCity( dto.city() );
        faculty.setAnnualFee( dto.annualFee() );
        faculty.setRanking( dto.ranking() );

        return faculty;
    }
}
