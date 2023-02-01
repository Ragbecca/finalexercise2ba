package com.ragbecca.finalexercise2ba.repository;

import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    Optional<TaskCategory> findByCategoryName(String categoryName);
}