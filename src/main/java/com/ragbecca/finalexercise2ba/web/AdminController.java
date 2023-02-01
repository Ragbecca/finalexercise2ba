package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.CreateCategoryRequest;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import com.ragbecca.finalexercise2ba.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/users")
    public List<User> getNumberOfUsers() {
        List<User> tempUserList = new ArrayList<>();
        for (User user: userService.getUsers()) {
            user.setPassword("");
            tempUserList.add(user);
        }
        return tempUserList;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add/category")
    public TaskCategory createTaskCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setCategoryName(createCategoryRequest.categoryName());
        if (taskCategoryRepository.findByCategoryName(createCategoryRequest.categoryName()).isEmpty()) {
            taskCategoryRepository.save(taskCategory);
        }
        return taskCategory;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/delete/category")
    public boolean deleteTaskCategory(@Valid @RequestBody Long categoryId) {
        if (taskCategoryRepository.findById(categoryId).isPresent()) {
            TaskCategory taskCategory = taskCategoryRepository.findById(categoryId).get();
            if (!taskRepository.findAllByTaskCategory(taskCategory).isEmpty()) {
                List<Task> taskList = taskRepository.findAllByTaskCategory(taskCategory);
                for (Task task: taskList) {
                    taskRepository.delete(task);
                }
            }
            taskCategoryRepository.delete(taskCategory);
            return true;
        }
        return false;
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
}
