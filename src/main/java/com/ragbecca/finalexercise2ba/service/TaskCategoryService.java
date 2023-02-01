package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;

    public List<TaskCategory> getAllTaskCategories() {
        return taskCategoryRepository.findAll();
    }
}

