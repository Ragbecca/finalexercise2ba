package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.dto.WebsiteDto;
import com.ragbecca.finalexercise2ba.entity.Website;
import com.ragbecca.finalexercise2ba.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    public Website createWebsiteAndReturn(WebsiteDto websiteDto) {
        Website website = new Website();
        website.setClicks(0);
        website.setUsername(websiteDto.username());
        website.setUrl(websiteDto.url());
        website.setDescription(websiteDto.description());
        website.setName(websiteDto.websiteName());
        website.setIcon("google.png");

        websiteRepository.save(website);
        return website;
    }

    public List<Website> findAllByUsername(String username) {
        return websiteRepository.findAllByUsername(username);
    }

    public void addClickToWebsite(Long websiteId) {
        if (websiteRepository.findById(websiteId).isEmpty()) {
            return;
        }
        Website website = websiteRepository.findById(websiteId).get();
        website.setClicks(website.getClicks() + 1);
        websiteRepository.save(website);
    }

}
