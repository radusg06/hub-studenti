package ro.hubstudentesc.persistence.entity.socialmedia;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ro.hubstudentesc.enums.socialmediaEnums.PostType;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "posts", schema = "feed")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type;

    @Column
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String content;

    //salveaza mai multe link-uri catre poze/videoclipuri atasate postarii.

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "media_urls")
    private String[] mediaUrls;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column
    private String location;

    @Column(name = "likes_count" , nullable = false)
    private Integer likesCount = 0;

    @Column(name = "comments_count" , nullable = false)
    private Integer commentsCount = 0;

    @Column(name = "is_pinned", nullable = false)
    private boolean pinned = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UUID getId(){return id;}
    public void setId(UUID id){this.id=id;}

    public User getAuthor(){return author;}
    public void setAuthor(User author){this.author=author;}

    public PostType getType(){return type;}
    public void setType(PostType type){this.type=type;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}

    public String[] getMediaUrls(){return mediaUrls;}
    public void setMediaUrls(String[] mediaUrls){this.mediaUrls=mediaUrls;}

    public LocalDateTime getEventDate(){return eventDate;}
    public void setEventDate(LocalDateTime eventDate){this.eventDate=eventDate;}

    public String getLocation(){return location;}
    public void setLocation(String location){this.location=location;}

    public Integer getLikesCount(){return likesCount;}
    public void setLikesCount(Integer likesCount){this.likesCount=likesCount;}

    public Integer getCommentsCount(){return commentsCount;}
    public void setCommentsCount(Integer commentsCount){this.commentsCount=commentsCount;}

    public boolean isPinned(){return pinned;}
    public void setPinned(boolean pinned){this.pinned=pinned;}

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

    public LocalDateTime getUpdatedAt(){return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt=updatedAt;}

}
