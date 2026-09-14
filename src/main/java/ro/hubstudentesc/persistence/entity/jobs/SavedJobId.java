package ro.hubstudentesc.persistence.entity.jobs;

import java.io.Serializable;
import java.util.UUID;

public class SavedJobId implements Serializable {

    private UUID user;
    private UUID job;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavedJobId that)) return false;
        return user.equals(that.user) && job.equals(that.job);
    }

    @Override
    public int hashCode() {
        return 31 * user.hashCode() + job.hashCode();
    }
}