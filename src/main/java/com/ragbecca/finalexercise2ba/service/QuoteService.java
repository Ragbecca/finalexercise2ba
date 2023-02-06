package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.dto.QuoteRequest;
import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.repository.QuoteRepository;
import com.ragbecca.finalexercise2ba.repository.QuoteTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteTodayRepository quoteTodayRepository;

    public List<Quote> getQuotesUsername(String username) {
        if (quoteRepository.findAllByUsername(username).isEmpty()) {
            return quoteRepository.findAllByUsername("unknown");
        }
        return quoteRepository.findAllByUsername(username);
    }

    public Quote createQuoteUser(QuoteRequest quoteRequest) {
        Quote quote = new Quote();
        quote.setQuote(quoteRequest.quote());
        quote.setAuthor(quoteRequest.author());
        quote.setUsername(quoteRequest.username());
        quoteRepository.save(quote);
        return quote;
    }

    public void deleteQuoteFromIdAndAllDailyQuotesConnected(Long quoteId) {
        if (quoteRepository.findById(quoteId).isEmpty()) {
            return;
        }
        Quote quote = quoteRepository.findById(quoteId).get();
        if (quoteTodayRepository.findByQuote(quote).isPresent()) {
            quoteTodayRepository.delete(quoteTodayRepository.findByQuote(quote).get());
        }
        quoteRepository.delete(quote);
    }
}
