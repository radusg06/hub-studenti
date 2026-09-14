package ro.hubstudentesc.persistence.entity.socialmedia;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostLikeId implements Serializable {

    private UUID post;
    private UUID user;

    public UUID getPost() {
        return post;
    }

    public void setPost(UUID post) {
        this.post = post;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}