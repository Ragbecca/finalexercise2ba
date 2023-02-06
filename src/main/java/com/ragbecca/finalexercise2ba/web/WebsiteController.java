package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.WebsiteDto;
import com.ragbecca.finalexercise2ba.entity.Website;
import com.ragbecca.finalexercise2ba.service.WebsiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ragbecca.finalexercise2ba.security.Constant.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/websites")
public class WebsiteController {

    private final WebsiteService websiteService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Website createWebsite(@Valid @RequestBody WebsiteDto websiteDto) {
        return websiteService.createWebsiteAndReturn(websiteDto);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/user/{username}")
    public List<Website> getWebsites(@PathVariable String username) {
        return websiteService.findAllByUsername(username);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/clicks/{websiteId}")
    public void addClickToWebsite(@PathVariable String websiteId) {
        websiteService.addClickToWebsite(Long.valueOf(websiteId));
    }
}
