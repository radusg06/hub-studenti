package ro.hubstudentesc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.EventRecordDto;
import ro.hubstudentesc.persistence.entity.Event;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventRecordDto toDto(Event events) {
        if ( events == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String description = null;
        String location = null;
        LocalDateTime date = null;
        String organizer = null;

        id = events.getId();
        title = events.getTitle();
        description = events.getDescription();
        location = events.getLocation();
        date = events.getDate();
        organizer = events.getOrganizer();

        EventRecordDto eventRecordDto = new EventRecordDto( id, title, description, location, date, organizer );

        return eventRecordDto;
    }

    @Override
    public List<EventRecordDto> toDto(List<Event> event) {
        if ( event == null ) {
            return null;
        }

        List<EventRecordDto> list = new ArrayList<EventRecordDto>( event.size() );
        for ( Event event1 : event ) {
            list.add( toDto( event1 ) );
        }

        return list;
    }

    @Override
    public Event toEntity(EventRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( dto.id() );
        event.setTitle( dto.title() );
        event.setDescription( dto.description() );
        event.setLocation( dto.location() );
        event.setDate( dto.date() );
        event.setOrganizer( dto.organizer() );

        return event;
    }
}
