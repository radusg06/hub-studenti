package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.auth.UserCredentialRecordDto;
import ro.hubstudentesc.mapper.auth.UserCredentialMapper;
import ro.hubstudentesc.persistence.repository.auth.UserCredentialRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;
    private final UserCredentialMapper userCredentialMapper;

    public UserCredentialRecordDto findByUserId(UUID userId){
        return userCredentialRepository.findById(userId).map(userCredentialMapper::toDto).orElseThrow();
    }

}
