package ro.hubstudentesc.persistence.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.hubstudentesc.persistence.entity.auth.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
