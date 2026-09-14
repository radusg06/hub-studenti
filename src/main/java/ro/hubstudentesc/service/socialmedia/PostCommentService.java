package ro.hubstudentesc.service.socialmedia;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.socialmedia.PostCommentCreateResponseDto;
import ro.hubstudentesc.dto.socialmedia.PostCommentRecordDto;
import ro.hubstudentesc.dto.socialmedia.PostCommentResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;
import ro.hubstudentesc.persistence.entity.socialmedia.PostComment;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostCommentRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final PostService postService;

    /**
     * Adaugă un comentariu la o postare.
     *
     * userId este primit din JWT.
     *
     * commentsCount este actualizat automat
     * de trigger-ul PostgreSQL.
     */
    @Transactional
    public PostCommentCreateResponseDto addComment(
            UUID postId,
            UUID userId,
            PostCommentRecordDto dto
    ) {

        // Verificăm dacă postarea există.
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Postare inexistenta"
                        )
                );

        // Verificăm dacă utilizatorul există.
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Utilizator inexistent"
                        )
                );

        PostComment comment = new PostComment();

        comment.setPost(post);
        comment.setAuthor(user);
        comment.setContent(dto.content());
        comment.setCreatedAt(LocalDateTime.now());

        postCommentRepository.save(comment);

        /*
         * Forțăm executarea INSERT-ului în PostgreSQL.
         *
         * Trigger-ul PostgreSQL va actualiza automat
         * feed.posts.comments_count.
         */
        postCommentRepository.flush();

        /*
         * Reîmprospătăm entitatea Post pentru a primi
         * valoarea actualizată de trigger.
         */
        entityManager.refresh(post);

        /*
         * Feed-ul conține commentsCount,
         * deci invalidăm cache-ul.
         */
        postService.clearFeedCache();

        return new PostCommentCreateResponseDto(
                true,
                new PostCommentResponseDto(
                        comment.getId(),
                        post.getId(),
                        comment.getContent(),
                        buildAuthorName(user),
                        comment.getCreatedAt()
                )
        );
    }

    /**
     * Returnează toate comentariile unei postări.
     */
    @Transactional(readOnly = true)
    public List<PostCommentResponseDto> getComments(
            UUID postId
    ) {

        // Verificăm dacă postarea există.
        postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Postare inexistenta"
                        )
                );

        List<PostComment> comments =
                postCommentRepository
                        .findByPostIdOrderByCreatedAtAsc(postId);

        return comments.stream()
                .map(comment -> new PostCommentResponseDto(
                        comment.getId(),
                        comment.getPost().getId(),
                        comment.getContent(),
                        buildAuthorName(comment.getAuthor()),
                        comment.getCreatedAt()
                ))
                .toList();
    }

    /**
     * Construiește numele autorului comentariului.
     */
    private String buildAuthorName(User user) {

        String givenName = user.getGivenName();
        String familyName = user.getFamilyName();

        if (givenName == null && familyName == null) {
            return user.getEmail();
        }

        if (givenName == null) {
            return familyName;
        }

        if (familyName == null) {
            return givenName;
        }

        return givenName + " " + familyName;
    }
}