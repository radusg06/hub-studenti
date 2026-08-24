package ro.hubstudentesc.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.auth.UserRecordDto;
import ro.hubstudentesc.mapper.auth.UserMapper;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserRecordDto> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    public void addUser(UserRecordDto userRecordDto) {
        userRepository.save(userMapper.toEntity(userRecordDto));
    }

    public UserRecordDto findById(UUID id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow();
    }
}