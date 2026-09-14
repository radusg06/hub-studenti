package ro.hubstudentesc.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        Argon2PasswordEncoder encoder =
                Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        String password = "Password123!";
        String hash = encoder.encode(password);

        System.out.println(hash);
    }
}