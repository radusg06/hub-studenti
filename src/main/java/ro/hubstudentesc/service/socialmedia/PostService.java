package ro.hubstudentesc.service.socialmedia;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.mapper.socialmedia.PostMapper;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostRepository;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public void addPost(PostRecordDto dto){
        User user = userRepository.findById(dto.userId()).orElseThrow();
        Post post = postMapper.toEntity(dto);
        post.setUser(user);
        postRepository.save(post);
    }

    public Page<PostRecordDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    public Page<PostRecordDto> findByType(PostType type, Pageable pageable){
        return postRepository.findByType(type, pageable).map(postMapper::toDto);
    }

    public void updatePost(Long id, PostRecordDto dto){
        Post post = postRepository.findById(id).orElseThrow();
        User user = userRepository.findById(dto.userId()).orElseThrow();
        post.setUser(user);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setType(dto.type());
        post.setCreatedAt(dto.createdAt());

        postRepository.save(post);

    }

    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }

}
