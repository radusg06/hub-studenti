package ro.hubstudentesc.web.bazar;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.bazar.BazarRecordDto;
import ro.hubstudentesc.service.bazar.BazarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bazar")
public class BazarController {
    private final BazarService bazarService;

    @GetMapping
    public ResponseEntity<Page<BazarRecordDto>> getBazar(
        Pageable pageable
    ){
        return ResponseEntity.ok(bazarService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<String> createBazar(
            @RequestBody @Valid BazarRecordDto dto
    ){
        bazarService.addBazar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Bazar created successfully");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBazar(
            @PathVariable Long id,
            @RequestBody @Valid BazarRecordDto dto
    ){
        bazarService.updateBazar(id,dto);
        return ResponseEntity.ok("Bazar updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBazar(
            @PathVariable Long id
    ){
        bazarService.deleteBazar(id);
        return ResponseEntity.ok("Bazar deleted successfully");
    }
}
