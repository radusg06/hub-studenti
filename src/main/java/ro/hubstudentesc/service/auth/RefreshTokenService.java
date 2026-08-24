package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.auth.RefreshTokenRecordDto;
import ro.hubstudentesc.mapper.auth.RefreshTokenMapper;
import ro.hubstudentesc.persistence.repository.auth.RefreshTokenRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenRecordDto findById(UUID id){
        return refreshTokenRepository.findById(id).map(refreshTokenMapper::toDto).orElseThrow();
    }
}
