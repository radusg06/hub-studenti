package ro.hubstudentesc.persistence.repository.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.socialmedia.PostLike;
import ro.hubstudentesc.persistence.entity.socialmedia.PostLikeId;

import java.util.Optional;
import java.util.UUID;

public interface PostLikeRepository
        extends JpaRepository<PostLike, PostLikeId> {

    boolean existsByPost_IdAndUser_Id(
            UUID postId,
            UUID userId
    );

    Optional<PostLike> findByPost_IdAndUser_Id(
            UUID postId,
            UUID userId
    );
}