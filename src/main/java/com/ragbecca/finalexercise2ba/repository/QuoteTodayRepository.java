package com.ragbecca.finalexercise2ba.repository;

import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.entity.QuoteToday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteTodayRepository extends JpaRepository<QuoteToday, Long> {

    Optional<QuoteToday> findByQuote_Username(final String username);

    Optional<QuoteToday> findByQuote(final Quote quote);

}