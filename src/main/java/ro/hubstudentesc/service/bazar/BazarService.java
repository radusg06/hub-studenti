package ro.hubstudentesc.service.bazar;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.hubstudentesc.dto.bazar.BazarRecordDto;
import ro.hubstudentesc.mapper.bazar.BazarMapper;
import ro.hubstudentesc.persistence.entity.auth.User;
import ro.hubstudentesc.persistence.entity.marketplace.MarketPlace;
import ro.hubstudentesc.persistence.repository.auth.UserRepository;
import ro.hubstudentesc.persistence.repository.bazar.BazarRepository;

@Service
@RequiredArgsConstructor
public class BazarService {
    private final UserRepository userRepository;
    private final BazarMapper bazarMapper;
    private final BazarRepository bazarRepository;

    public void addBazar(BazarRecordDto dto){
        User user = userRepository.findById(dto.userId()).orElseThrow();
        MarketPlace bazar = bazarMapper.toEntity(dto);
        bazar.setUser(user);
        bazarRepository.save(bazar);
    }

    public Page<BazarRecordDto> findAll(Pageable pageable){
        return bazarRepository.findAll(pageable).map(bazarMapper::toDto);
    }

    public void updateBazar(Long id ,BazarRecordDto dto){
        MarketPlace bazar = bazarRepository.findById(id).orElseThrow();
        User user = userRepository.findById(dto.userId()).orElseThrow();

        bazar.setUser(user);
        bazar.setTitle(dto.title());
        bazar.setDescription(dto.description());
        bazar.setPrice(dto.price());
        bazar.setCreatedAt(dto.createdAt());

        bazarRepository.save(bazar);
    }

    public void deleteBazar(Long id){
        MarketPlace bazar = bazarRepository.findById(id).orElseThrow();
        bazarRepository.delete(bazar);
    }
}
