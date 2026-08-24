package ro.hubstudentesc.mapper.bazar;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.bazar.BazarRecordDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.marketplace.MarketPlace;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T19:56:32+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class BazarMapperImpl implements BazarMapper {

    @Override
    public BazarRecordDto toDto(MarketPlace bazar) {
        if ( bazar == null ) {
            return null;
        }

        UUID userId = null;
        String title = null;
        String description = null;
        BigDecimal price = null;
        LocalDateTime createdAt = null;

        userId = bazarUserId( bazar );
        title = bazar.getTitle();
        description = bazar.getDescription();
        price = bazar.getPrice();
        createdAt = bazar.getCreatedAt();

        BazarRecordDto bazarRecordDto = new BazarRecordDto( userId, title, description, price, createdAt );

        return bazarRecordDto;
    }

    @Override
    public MarketPlace toEntity(BazarRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        MarketPlace bazar = new MarketPlace();

        bazar.setTitle( dto.title() );
        bazar.setDescription( dto.description() );
        bazar.setPrice( dto.price() );
        bazar.setCreatedAt( dto.createdAt() );

        return bazar;
    }

    private UUID bazarUserId(MarketPlace bazar) {
        User user = bazar.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
