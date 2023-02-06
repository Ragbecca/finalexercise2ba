package com.ragbecca.finalexercise2ba.repository;

import com.ragbecca.finalexercise2ba.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebsiteRepository extends JpaRepository<Website, Long> {

    List<Website> findAllByUsername(String username);

}