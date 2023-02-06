package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.dto.AuthResponse;
import com.ragbecca.finalexercise2ba.dto.SignUpRequest;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.exception.DuplicatedUserInfoException;
import com.ragbecca.finalexercise2ba.exception.UserNotFoundException;
import com.ragbecca.finalexercise2ba.repository.UserRepository;
import com.ragbecca.finalexercise2ba.security.LoginRequest;
import com.ragbecca.finalexercise2ba.security.SecurityConfiguration;
import com.ragbecca.finalexercise2ba.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
