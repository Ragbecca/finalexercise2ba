package com.ragbecca.finalexercise2ba.repository;

import com.ragbecca.finalexercise2ba.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByUsername(String username);
}