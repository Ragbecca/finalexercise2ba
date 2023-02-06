package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.entity.QuoteToday;
import com.ragbecca.finalexercise2ba.repository.QuoteRepository;
import com.ragbecca.finalexercise2ba.repository.QuoteTodayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class QuoteTodayService {

    private final QuoteTodayRepository quoteTodayRepository;
    private final QuoteRepository quoteRepository;

    public QuoteToday getQuoteTodayOrCreate(String username) {
        if (checkIfQuoteTodayExistsFromUsername(username)) {
            QuoteToday quoteToday = quoteTodayRepository.findByQuote_Username(username).get();
            if (checkIfQuoteIsToday(quoteToday)) {
                return quoteToday;
            } else {
                deleteQuoteToday(username);
                quoteTodayRepository.save(createNewQuoteToday(username));
                return quoteTodayRepository.findByQuote_Username(username).get();
            }
        } else {
            if (checkIfUserHasQuotes(username)) {
                quoteTodayRepository.save(createNewQuoteToday(username));
                return quoteTodayRepository.findByQuote_Username(username).get();
            } else {
                quoteTodayRepository.save(createNewQuoteToday(username));
                return quoteTodayRepository.findByQuote_Username("unknown").get();
            }
        }
    }

    private boolean checkIfUserHasQuotes(String username) {
        return !quoteRepository.findAllByUsername(username).isEmpty();
    }

    private boolean checkIfQuoteTodayExistsFromUsername(String username) {
        return quoteTodayRepository.findByQuote_Username(username).isPresent();
    }

    private boolean checkIfQuoteIsToday(QuoteToday quoteToday) {
        return Objects.equals(quoteToday.getDateActive().toLocalDate(), LocalDate.now());
    }

    private void deleteQuoteToday(String username) {
        quoteTodayRepository.delete(quoteTodayRepository.findByQuote_Username(username).get());
    }

    private QuoteToday createNewQuoteToday(String username) {
        List<Quote> quotes;
        if (quoteRepository.findAllByUsername(username).isEmpty()) {
            if (quoteTodayRepository.findByQuote_Username("unknown").isPresent()) {
                return quoteTodayRepository.findByQuote_Username("unknown").get();
            }
            quotes = quoteRepository.findAllByUsername("unknown");
        } else {
            quotes = quoteRepository.findAllByUsername(username);
        }
        int randomNumber = getRandomNumber(0, quotes.size());
        Quote quote = quotes.get(randomNumber);
        QuoteToday quoteToday = new QuoteToday();
        quoteToday.setQuote(quote);
        quoteToday.setDateActive(Date.valueOf(LocalDate.now()));
        return quoteToday;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
