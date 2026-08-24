package ro.hubstudentesc.persistence.repository.socialmedia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.socialmedia.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByType(PostType type, Pageable pageable);
}
