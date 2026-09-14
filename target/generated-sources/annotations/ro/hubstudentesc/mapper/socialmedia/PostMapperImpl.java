package ro.hubstudentesc.mapper.socialmedia;

import java.time.LocalDateTime;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T14:49:04+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostRecordDto toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostType type = null;
        String title = null;
        String content = null;
        String[] mediaUrls = null;
        LocalDateTime eventDate = null;
        String location = null;

        type = post.getType();
        title = post.getTitle();
        content = post.getContent();
        String[] mediaUrls1 = post.getMediaUrls();
        if ( mediaUrls1 != null ) {
            mediaUrls = Arrays.copyOf( mediaUrls1, mediaUrls1.length );
        }
        eventDate = post.getEventDate();
        location = post.getLocation();

        PostRecordDto postRecordDto = new PostRecordDto( type, title, content, mediaUrls, eventDate, location );

        return postRecordDto;
    }

    @Override
    public Post toEntity(PostRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Post post = new Post();

        post.setType( dto.type() );
        post.setTitle( dto.title() );
        post.setContent( dto.content() );
        String[] mediaUrls = dto.mediaUrls();
        if ( mediaUrls != null ) {
            post.setMediaUrls( Arrays.copyOf( mediaUrls, mediaUrls.length ) );
        }
        post.setEventDate( dto.eventDate() );
        post.setLocation( dto.location() );

        return post;
    }
}
