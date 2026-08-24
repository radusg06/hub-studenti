package ro.hubstudentesc.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.hubstudentesc.dto.auth.UserRecordDto;
import ro.hubstudentesc.service.auth.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRecordDto>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRecordDto> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        userService.addUser(userRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }


}