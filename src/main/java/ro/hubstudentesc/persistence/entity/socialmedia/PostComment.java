package ro.hubstudentesc.persistence.entity.socialmedia;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "post_comments" , schema = "feed")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id" , nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;

    @Column(nullable = false)
    @NotBlank
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UUID getId(){return id;}
    public void setId(UUID id){this.id = id;}

    public Post getPost(){return post;}
    public void setPost(Post post){this.post = post;}

    public User getAuthor(){return author;}
    public void setAuthor(User author){this.author = author;}

    public String getContent(){return content;}
    public void setContent(String content){this.content = content;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
