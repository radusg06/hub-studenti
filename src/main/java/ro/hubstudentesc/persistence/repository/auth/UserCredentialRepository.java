package ro.hubstudentesc.persistence.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.auth.UserCredential;

import java.util.UUID;

public interface UserCredentialRepository extends JpaRepository<UserCredential , UUID> {
}
