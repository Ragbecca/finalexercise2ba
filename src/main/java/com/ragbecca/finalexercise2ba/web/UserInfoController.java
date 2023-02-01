package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.entity.UserInfo;
import com.ragbecca.finalexercise2ba.repository.UserInfoRepository;
import com.ragbecca.finalexercise2ba.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/info")
    public UserInfo deleteTaskCategory(@Valid @RequestBody String username) {
        if (username.endsWith("=")) {
            username = username.replace("=", "");
        }
        User user = userRepository.findByUsername(username).get();
        if (userInfoRepository.findByUser(user).isEmpty()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            userInfoRepository.save(userInfo);
        }
        UserInfo userInfo = userInfoRepository.findByUser(user).get();
        userInfo.getUser().setPassword("");
        userInfo.getUser().setRole("");
        userInfo.getUser().setEmail("");
        return userInfo;
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
}
