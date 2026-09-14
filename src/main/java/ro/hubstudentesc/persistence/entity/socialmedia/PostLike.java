package ro.hubstudentesc.persistence.entity.socialmedia;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "post_likes" , schema = "feed")
@IdClass(PostLikeId.class)
public class PostLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id" , nullable = false)
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Post getPost(){return post;}
    public void setPost(Post post){this.post = post;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
