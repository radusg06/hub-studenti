package ro.hubstudentesc.mapper.socialmedia;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "user.id" , target = "userId")
    PostRecordDto toDto(Post post);

    @Mapping(target = "user" , ignore = true)
    Post toEntity(PostRecordDto dto);

}
