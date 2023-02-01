package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.entity.Website;
import com.ragbecca.finalexercise2ba.repository.QuoteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/user/{username}")
    public List<Quote> getQuotes(@PathVariable String username) {
        if (quoteRepository.findAllByUsername(username).isEmpty()) {
            return quoteRepository.findAllByUsername("");
        }
        return quoteRepository.findAllByUsername(username);
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    private final QuoteRepository quoteRepository;

}
