package ro.hubstudentesc.service;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.SavedSearchRecordDto;
import ro.hubstudentesc.persistence.entity.SavedSearch;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavedSearchMapper {
    @Mapping(source="user.id", target = "userId")
    SavedSearchRecordDto toDto(SavedSearch savedSearch);

    List<SavedSearchRecordDto> toDto(List<SavedSearch> savedSearches);

    @Mapping(target = "user", ignore = true)
    SavedSearch toEntity(SavedSearchRecordDto dto);
}
