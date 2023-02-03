package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.QuoteRequest;
import com.ragbecca.finalexercise2ba.dto.TaskDto;
import com.ragbecca.finalexercise2ba.entity.*;
import com.ragbecca.finalexercise2ba.repository.QuoteRepository;
import com.ragbecca.finalexercise2ba.repository.QuoteTodayRepository;
import com.ragbecca.finalexercise2ba.service.QuoteTodayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/user/{username}")
    public List<Quote> getQuotes(@PathVariable String username) {
        if (quoteRepository.findAllByUsername(username).isEmpty()) {
            return quoteRepository.findAllByUsername("unknown");
        }
        return quoteRepository.findAllByUsername(username);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/today/user/{username}")
    public QuoteToday getQuoteToday(@PathVariable String username) {
        return quoteTodayService.getQuoteTodayOrCreate(username);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Quote createQuote(@Valid @RequestBody QuoteRequest quoteRequest) {
        Quote quote = new Quote();
        quote.setQuote(quoteRequest.quote());
        quote.setAuthor(quoteRequest.author());
        quote.setUsername(quoteRequest.username());
        quoteRepository.save(quote);
        return quote;
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    private final QuoteRepository quoteRepository;
    private final QuoteTodayRepository quoteTodayRepository;

    private final QuoteTodayService quoteTodayService;

}
