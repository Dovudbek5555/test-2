package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.UserDto;
import com.example.expess24.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 400).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable UUID id) {
        UserDto userDto = userService.getOneUser(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        userDto.setId(id);  // Ensure the ID in the path and body match
        ApiResponse apiResponse = userService.updateUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}
