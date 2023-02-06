package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.entity.UserInfo;
import com.ragbecca.finalexercise2ba.repository.UserInfoRepository;
import com.ragbecca.finalexercise2ba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public UserInfo getUserInfoFromUsername(String username) {
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

}
