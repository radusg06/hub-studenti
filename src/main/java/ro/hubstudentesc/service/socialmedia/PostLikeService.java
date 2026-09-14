package ro.hubstudentesc.service.socialmedia;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.hubstudentesc.dto.socialmedia.PostLikeResponseDto;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;
import ro.hubstudentesc.persistence.entity.socialmedia.PostLike;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostLikeRepository;
import ro.hubstudentesc.persistence.repository.socialmedia.PostRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final PostService postService;

    /**
     * Adaugă sau elimină like-ul utilizatorului.
     *
     * likesCount este actualizat automat de trigger-ul PostgreSQL.
     */
    @Transactional
    public PostLikeResponseDto toggleLike(
            UUID postId,
            UUID userId
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

        // Căutăm like-ul utilizatorului pe această postare.
        var existingLike =
                postLikeRepository.findByPost_IdAndUser_Id(
                        postId,
                        userId
                );

        boolean liked;

        if (existingLike.isPresent()) {

            // Like existent -> îl eliminăm.
            postLikeRepository.delete(
                    existingLike.get()
            );

            liked = false;

        } else {

            // Nu există like -> îl adăugăm.
            PostLike postLike = new PostLike();

            postLike.setPost(post);
            postLike.setUser(user);
            postLike.setCreatedAt(
                    LocalDateTime.now()
            );

            postLikeRepository.save(postLike);

            liked = true;
        }

        /*
         * Forțăm executarea INSERT/DELETE în PostgreSQL.
         *
         * Trigger-ul PostgreSQL va actualiza automat
         * feed.posts.likes_count.
         */
        postLikeRepository.flush();

        /*
         * Reîmprospătăm entitatea Post din baza de date
         * pentru a primi valoarea actualizată de trigger.
         */
        entityManager.refresh(post);

        /*
         * Feed-ul conține likesCount și hasLiked,
         * deci invalidăm cache-ul.
         */
        postService.clearFeedCache();

        return new PostLikeResponseDto(
                true,
                liked,
                post.getLikesCount()
        );
    }
}