package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.auth.IssuedTokenRecordDto;
import ro.hubstudentesc.mapper.auth.IssuedTokenMapper;
import ro.hubstudentesc.persistence.repository.auth.IssuedTokenRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssuedTokenService {
    private final IssuedTokenRepository issuedTokenRepository;
    private final IssuedTokenMapper issuedTokenMapper;

    public IssuedTokenRecordDto findById(UUID id){
        return issuedTokenRepository.findById(id).map(issuedTokenMapper::toDto).orElseThrow();
    }
}
