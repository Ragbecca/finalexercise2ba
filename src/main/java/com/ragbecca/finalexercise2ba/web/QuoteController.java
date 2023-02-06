package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.QuoteRequest;
import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.entity.QuoteToday;
import com.ragbecca.finalexercise2ba.service.QuoteService;
import com.ragbecca.finalexercise2ba.service.QuoteTodayService;
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
@RequestMapping("/api/quote")
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteTodayService quoteTodayService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/user/{username}")
    public List<Quote> getQuotes(@PathVariable String username) {
        return quoteService.getQuotesUsername(username);
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
        return quoteService.createQuoteUser(quoteRequest);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/delete")
    public void deleteQuote(@Valid @RequestBody Long quoteId) {
        quoteService.deleteQuoteFromIdAndAllDailyQuotesConnected(quoteId);
    }

}
