package ro.hubstudentesc.dto.profiles;

import java.util.UUID;

public record StudentProfileDto(
        UUID userId,
        String university,
        String faculty,
        String specialization,
        String studyCycle,
        Short studyYear,
        String bio,
        String[] skills,
        String cvUrl,
        String githubUrl,
        String linkedinUrl,
        String portfolioUrl
) {
}
