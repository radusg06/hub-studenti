package ro.hubstudentesc.mapper.marketplace;

import org.mapstruct.Mapper;
import ro.hubstudentesc.dto.marketplace.ListingCreateDto;
import ro.hubstudentesc.dto.marketplace.ListingResponseDto;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    Listing toEntity(ListingCreateDto dto);
}
