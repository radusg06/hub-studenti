package ro.hubstudentesc.persistence.entity.socialmedia;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}

    public PostType getType(){return type;}
    public void setType(PostType type){this.type=type;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

}
