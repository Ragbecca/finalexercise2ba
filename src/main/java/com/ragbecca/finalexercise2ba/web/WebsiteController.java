package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.TaskDto;
import com.ragbecca.finalexercise2ba.dto.WebsiteDto;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.Website;
import com.ragbecca.finalexercise2ba.repository.WebsiteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/websites")
public class WebsiteController {

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void createWebsite(@Valid @RequestBody WebsiteDto websiteDto) {
        Website website = new Website();
        website.setClicks(0);
        website.setUsername(websiteDto.username());
        website.setUrl(websiteDto.url());
        website.setDescription(websiteDto.description());
        website.setName(websiteDto.websiteName());
        website.setIcon("google.png");

        websiteRepository.save(website);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/user/{username}")
    public List<Website> getWebsites(@PathVariable String username) {
        return websiteRepository.findAllByUsername(username);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/clicks/{websiteId}")
    public void addClickToWebsite(@PathVariable String websiteId) {
        if (websiteRepository.findById(Long.valueOf(websiteId)).isEmpty()) {
            return;
        }
        Website website = websiteRepository.findById(Long.valueOf(websiteId)).get();
        website.setClicks(website.getClicks() + 1);
        websiteRepository.save(website);
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    private final WebsiteRepository websiteRepository;
}
