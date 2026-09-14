package ro.hubstudentesc.mapper.socialmedia;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostRecordDto toDto(Post post);

    @Mapping(target ="id", ignore = true)
    @Mapping(target ="author", ignore = true)
    @Mapping(target ="likesCount", ignore = true)
    @Mapping(target ="commentsCount", ignore = true)
    @Mapping(target ="pinned", ignore = true)
    @Mapping(target ="createdAt", ignore = true)
    @Mapping(target ="updatedAt", ignore = true)
    Post toEntity(PostRecordDto dto);

}
