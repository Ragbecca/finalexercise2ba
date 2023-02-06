package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.dto.CreateCategoryRequest;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserService userService;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;

    public List<User> getUsersAllWithoutPassword() {
        List<User> tempUserList = new ArrayList<>();
        for (User user : userService.getUsers()) {
            user.setPassword("");
            tempUserList.add(user);
        }
        return tempUserList;
    }

    public TaskCategory createCategoryAndReturn(CreateCategoryRequest createCategoryRequest) {
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setCategoryName(createCategoryRequest.categoryName());
        if (taskCategoryRepository.findByCategoryName(createCategoryRequest.categoryName()).isEmpty()) {
            taskCategoryRepository.save(taskCategory);
        }
        return taskCategory;
    }

    public void deleteCategoryAndDailyIfNeeded(Long id) {
        if (taskCategoryRepository.findById(id).isPresent()) {
            TaskCategory taskCategory = taskCategoryRepository.findById(id).get();
            if (!taskRepository.findAllByTaskCategory(taskCategory).isEmpty()) {
                List<Task> taskList = taskRepository.findAllByTaskCategory(taskCategory);
                for (Task task : taskList) {
                    taskRepository.delete(task);
                }
            }
            taskCategoryRepository.delete(taskCategory);
        }
    }

}
