package ro.hubstudentesc.mapper.bazar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.bazar.BazarRecordDto;
import ro.hubstudentesc.persistence.entity.marketplace.MarketPlace;

@Mapper(componentModel = "spring")
public interface BazarMapper {
    @Mapping(source = "user.id" , target = "userId")
    BazarRecordDto toDto(MarketPlace bazar);

    @Mapping(target = "user" , ignore = true)
    MarketPlace toEntity(BazarRecordDto dto);
}
