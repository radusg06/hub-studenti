package ro.hubstudentesc.persistence.repository.socialmedia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByType(PostType type, Pageable pageable);

    @Query("""
    SELECT p
    FROM Post p
    WHERE p.createdAt < :cursor
    ORDER BY p.createdAt DESC
""")
    List<Post> findPostsBefore(
    @Param("cursor") LocalDateTime cursor,
    Pageable pageable
    );

    @Query("""
    SELECT p
    FROM Post p
    WHERE p.createdAt < :cursor
      AND p.type = :type
    ORDER BY p.createdAt DESC
""")
    List<Post> findPostsBeforeAndType(
            @Param("cursor") LocalDateTime cursor,
            @Param("type") PostType type,
            Pageable pageable
    );
}
