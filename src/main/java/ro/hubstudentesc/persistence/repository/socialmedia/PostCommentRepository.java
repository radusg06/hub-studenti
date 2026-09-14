package ro.hubstudentesc.persistence.repository.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.socialmedia.PostComment;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostCommentRepository
        extends JpaRepository<PostComment, UUID> {

    List<PostComment> findByPostIdOrderByCreatedAtAsc(
            UUID postId
    );
}