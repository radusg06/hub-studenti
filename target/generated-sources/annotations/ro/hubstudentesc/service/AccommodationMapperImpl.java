package ro.hubstudentesc.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.AccommodationRecordDto;
import ro.hubstudentesc.persistence.entity.Accommodation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class AccommodationMapperImpl implements AccommodationMapper {

    @Override
    public AccommodationRecordDto toDto(Accommodation accommodation) {
        if ( accommodation == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String address = null;
        String city = null;
        Double price = null;
        Integer availablePlaces = null;
        String description = null;

        id = accommodation.getId();
        name = accommodation.getName();
        address = accommodation.getAddress();
        city = accommodation.getCity();
        price = accommodation.getPrice();
        availablePlaces = accommodation.getAvailablePlaces();
        description = accommodation.getDescription();

        AccommodationRecordDto accommodationRecordDto = new AccommodationRecordDto( id, name, address, city, price, availablePlaces, description );

        return accommodationRecordDto;
    }

    @Override
    public List<AccommodationRecordDto> toDto(List<Accommodation> accommodations) {
        if ( accommodations == null ) {
            return null;
        }

        List<AccommodationRecordDto> list = new ArrayList<AccommodationRecordDto>( accommodations.size() );
        for ( Accommodation accommodation : accommodations ) {
            list.add( toDto( accommodation ) );
        }

        return list;
    }

    @Override
    public Accommodation toEntity(AccommodationRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Accommodation accommodation = new Accommodation();

        accommodation.setId( dto.id() );
        accommodation.setName( dto.name() );
        accommodation.setAddress( dto.address() );
        accommodation.setCity( dto.city() );
        accommodation.setPrice( dto.price() );
        accommodation.setAvailablePlaces( dto.availablePlaces() );
        accommodation.setDescription( dto.description() );

        return accommodation;
    }
}
