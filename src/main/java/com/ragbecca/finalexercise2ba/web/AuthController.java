package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.AuthResponse;
import com.ragbecca.finalexercise2ba.dto.SignUpRequest;
import com.ragbecca.finalexercise2ba.security.LoginRequest;
import com.ragbecca.finalexercise2ba.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping(value = "/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userServiceImpl.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userServiceImpl.signup(signUpRequest);
    }
}
