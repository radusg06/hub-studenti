package ro.hubstudentesc.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.SavedSearchRecordDto;
import ro.hubstudentesc.service.SavedSearchService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/saved-searches")
public class SavedSearchController {
    private final SavedSearchService savedSearchService;

    @GetMapping
    public ResponseEntity<List<SavedSearchRecordDto>> getSavedSearches(
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(savedSearchService.findAll(userId));
    }

    @PostMapping
    public ResponseEntity<String> saveSearch(
            @RequestBody SavedSearchRecordDto dto
    ) {
        savedSearchService.saveSearch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Search saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSearch(
            @PathVariable Long id,
            @RequestParam UUID userId
    ) {
        savedSearchService.deleteSearch(id, userId);
        return ResponseEntity.ok("Search deleted successfully");
    }

}
