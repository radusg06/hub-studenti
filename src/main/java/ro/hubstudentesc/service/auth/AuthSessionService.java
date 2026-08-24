package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.auth.AuthSessionRecordDto;
import ro.hubstudentesc.mapper.auth.AuthSessionMapper;
import ro.hubstudentesc.persistence.repository.auth.AuthSessionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthSessionService {
    private final AuthSessionRepository authSessionRepository;
    private final AuthSessionMapper authSessionMapper;

    public AuthSessionRecordDto findById(UUID id){
        return authSessionRepository.findById(id).map(authSessionMapper::toDto).orElseThrow();
    }
}
