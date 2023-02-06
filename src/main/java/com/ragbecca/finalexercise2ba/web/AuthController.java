package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.AuthResponse;
import com.ragbecca.finalexercise2ba.dto.SignUpRequest;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.exception.DuplicatedUserInfoException;
import com.ragbecca.finalexercise2ba.security.LoginRequest;
import com.ragbecca.finalexercise2ba.security.SecurityConfiguration;
import com.ragbecca.finalexercise2ba.security.TokenProvider;
import com.ragbecca.finalexercise2ba.service.UserService;
import com.ragbecca.finalexercise2ba.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
            if (userService.hasUserWithUsername(signUpRequest.getUsername()) || Objects.equals(signUpRequest.getUsername(), "unknown")) {
                throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
            }
            if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
                throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
            }

            userService.saveUser(mapSignUpRequestToUser(signUpRequest));

            String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
            return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRole(SecurityConfiguration.USER);
        return user;
    }
}
