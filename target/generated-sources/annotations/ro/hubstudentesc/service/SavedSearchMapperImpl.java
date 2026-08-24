package ro.hubstudentesc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.SavedSearchRecordDto;
import ro.hubstudentesc.persistence.entity.SavedSearch;
import ro.hubstudentesc.persistence.entity.auth.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class SavedSearchMapperImpl implements SavedSearchMapper {

    @Override
    public SavedSearchRecordDto toDto(SavedSearch savedSearch) {
        if ( savedSearch == null ) {
            return null;
        }

        UUID userId = null;
        Long id = null;
        String keyword = null;
        String city = null;
        String program = null;

        userId = savedSearchUserId( savedSearch );
        id = savedSearch.getId();
        keyword = savedSearch.getKeyword();
        city = savedSearch.getCity();
        program = savedSearch.getProgram();

        SavedSearchRecordDto savedSearchRecordDto = new SavedSearchRecordDto( id, userId, keyword, city, program );

        return savedSearchRecordDto;
    }

    @Override
    public List<SavedSearchRecordDto> toDto(List<SavedSearch> savedSearches) {
        if ( savedSearches == null ) {
            return null;
        }

        List<SavedSearchRecordDto> list = new ArrayList<SavedSearchRecordDto>( savedSearches.size() );
        for ( SavedSearch savedSearch : savedSearches ) {
            list.add( toDto( savedSearch ) );
        }

        return list;
    }

    @Override
    public SavedSearch toEntity(SavedSearchRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        SavedSearch savedSearch = new SavedSearch();

        savedSearch.setId( dto.id() );
        savedSearch.setCity( dto.city() );
        savedSearch.setKeyword( dto.keyword() );
        savedSearch.setProgram( dto.program() );

        return savedSearch;
    }

    private UUID savedSearchUserId(SavedSearch savedSearch) {
        User user = savedSearch.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
