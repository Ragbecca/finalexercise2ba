package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.entity.UserInfo;
import com.ragbecca.finalexercise2ba.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ragbecca.finalexercise2ba.security.Constant.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/info")
    public UserInfo getUserInfo(@Valid @RequestBody String username) {
        return userInfoService.getUserInfoFromUsername(username);
    }
}
