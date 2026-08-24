package ro.hubstudentesc.mapper.socialmedia;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-20T13:40:00+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.19 (Amazon.com Inc.)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostRecordDto toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        UUID userId = null;
        String title = null;
        String content = null;
        PostType type = null;
        LocalDateTime createdAt = null;

        userId = postUserId( post );
        title = post.getTitle();
        content = post.getContent();
        type = post.getType();
        createdAt = post.getCreatedAt();

        PostRecordDto postRecordDto = new PostRecordDto( userId, title, content, type, createdAt );

        return postRecordDto;
    }

    @Override
    public Post toEntity(PostRecordDto dto) {
        if ( dto == null ) {
            return null;
        }

        Post post = new Post();

        post.setTitle( dto.title() );
        post.setContent( dto.content() );
        post.setType( dto.type() );
        post.setCreatedAt( dto.createdAt() );

        return post;
    }

    private UUID postUserId(Post post) {
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
