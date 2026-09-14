package ro.hubstudentesc.mapper.marketplace;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.marketplace.ListingCreateDto;
import ro.hubstudentesc.persistence.entity.marketplace.Listing;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T14:49:04+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class ListingMapperImpl implements ListingMapper {

    @Override
    public Listing toEntity(ListingCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        Listing listing = new Listing();

        listing.setTitle( dto.title() );
        listing.setDescription( dto.description() );
        listing.setPrice( dto.price() );
        listing.setCurrency( dto.currency() );
        listing.setNegotiable( dto.negotiable() );
        listing.setCondition( dto.condition() );
        listing.setLocation( dto.location() );
        listing.setRequiresPayment( dto.requiresPayment() );
        listing.setContactPhone( dto.contactPhone() );
        listing.setContactEmail( dto.contactEmail() );
        listing.setContactWhatsapp( dto.contactWhatsapp() );
        listing.setPreferredContactInterval( dto.preferredContactInterval() );

        return listing;
    }
}
