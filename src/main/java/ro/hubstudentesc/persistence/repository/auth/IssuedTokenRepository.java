package ro.hubstudentesc.persistence.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.auth.IssuedToken;

import java.util.UUID;

public interface IssuedTokenRepository extends JpaRepository<IssuedToken , UUID> {
}
