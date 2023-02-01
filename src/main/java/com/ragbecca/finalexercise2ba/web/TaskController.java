package com.ragbecca.finalexercise2ba.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ragbecca.finalexercise2ba.dto.CreateCategoryRequest;
import com.ragbecca.finalexercise2ba.dto.TaskDto;
import com.ragbecca.finalexercise2ba.dto.UserDto;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import com.ragbecca.finalexercise2ba.service.TaskCategoryService;
import com.ragbecca.finalexercise2ba.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;


    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/categories")
    public List<TaskCategory> getTaskCategories() {
        return taskCategoryService.getAllTaskCategories();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{username}")
    public List<Task> getTasks(@PathVariable String username) {
        return taskService.getAllTasksUser(username);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Task createTask(@Valid @RequestBody TaskDto taskDto) {
        Task task = new Task();
        TaskCategory category = taskCategoryRepository.findById(taskDto.getTaskCategoryId()).get();
        task.setTaskCategory(category);
        task.setStatus(false);
        task.setTaskName(taskDto.getTaskName());
        if (taskDto.getDeadlineDate() != null) {
            task.setDeadlineDate(taskDto.getDeadlineDate());
        }
        if (taskDto.getDeadlineTime() != null) {
            task.setDeadlineTime(taskDto.getDeadlineTime());
        }
        task.setUsername(taskDto.getUsername());
        taskRepository.save(task);
        return task;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/status/done")
    public Task editStatusTaskToTrue(@Valid @RequestBody Long taskId) {
        if (taskRepository.findById(String.valueOf(taskId)).isEmpty()) {
            return null;
        }
        Task task = taskRepository.findById(String.valueOf(taskId)).get();
        task.setStatus(true);
        taskRepository.save(task);
        return task;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/status/undone")
    public Task editStatusTaskToFalse(@Valid @RequestBody Long taskId) {
        if (taskRepository.findById(String.valueOf(taskId)).isEmpty()) {
            return null;
        }
        Task task = taskRepository.findById(String.valueOf(taskId)).get();
        task.setStatus(false);
        taskRepository.save(task);
        return task;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/delete")
    public boolean deleteTask(@Valid @RequestBody Long taskId) {
        if (taskRepository.findById(String.valueOf(taskId)).isEmpty()) {
            return false;
        }
        Task task = taskRepository.findById(String.valueOf(taskId)).get();
        taskRepository.delete(task);
        return true;
    }

    private static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
}
