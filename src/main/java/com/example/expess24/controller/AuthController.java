package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.AuthLoginDto;
import com.example.expess24.payload.UserDto;
import com.example.expess24.repository.UserRepository;
import com.example.expess24.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/sign-up")
    public HttpEntity<?> signUp(@RequestParam String phoneNumber, String firstName, String password){
        ApiResponse apiResponse = userService.signUp(phoneNumber, firstName, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PostMapping("/sign-in")
    public HttpEntity<?> login(@RequestBody AuthLoginDto authLoginDto) {
        ApiResponse apiResponse = userService.forLogin(authLoginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 400).body(apiResponse);
    }

}
