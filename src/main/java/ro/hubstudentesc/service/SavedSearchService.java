package ro.hubstudentesc.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.SavedSearchRecordDto;
import ro.hubstudentesc.exception.UnauthorizedSavedSearchException;
import ro.hubstudentesc.persistence.entity.SavedSearch;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.repository.SavedSearchRepository;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavedSearchService {
    private final SavedSearchRepository savedSearchRepository;
    private final SavedSearchMapper savedSearchMapper;
    private final UserRepository userRepository;

    public void saveSearch(SavedSearchRecordDto dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow();

        SavedSearch savedSearch = savedSearchMapper.toEntity(dto);
        savedSearch.setUser(user);

        savedSearchRepository.save(savedSearch);
    }

    public List<SavedSearchRecordDto> findAll(UUID userId){
        return savedSearchMapper.toDto(savedSearchRepository.findByUserId(userId));
    }

    public void deleteSearch(Long id , UUID userId){
        SavedSearch savedSearch = savedSearchRepository.findById(id).orElseThrow();

        if(!savedSearch.getUser().getId().equals(userId)){
            throw new UnauthorizedSavedSearchException();
        }

        savedSearchRepository.delete(savedSearch);
    }
}
