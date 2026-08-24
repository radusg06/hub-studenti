package ro.hubstudentesc.web.socialmedia;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.socialmedia.PostRecordDto;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.service.socialmedia.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostRecordDto>> getPosts(
            @RequestParam(required = false) PostType type,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable){
        if(type != null){
            return ResponseEntity.ok(postService.findByType(type,pageable));
        }
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestBody @Valid PostRecordDto dto
    ){
        postService.addPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRecordDto dto
    ){
        postService.updatePost(id,dto);
        return ResponseEntity.ok("Post updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id
    ){
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
