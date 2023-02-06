package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.TaskDto;
import com.ragbecca.finalexercise2ba.dto.TaskEditDto;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.service.TaskCategoryService;
import com.ragbecca.finalexercise2ba.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ragbecca.finalexercise2ba.security.Constant.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;


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
        return taskService.createTaskAndReturn(taskDto);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/edit")
    public Task editTask(@Valid @RequestBody TaskEditDto taskEditDto) {
        return taskService.editTaskAndReturn(taskEditDto);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/status/done")
    public Task editStatusTaskToTrue(@Valid @RequestBody Long taskId) {
        return taskService.changeStatusAndReturn(taskId, true);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/status/undone")
    public Task editStatusTaskToFalse(@Valid @RequestBody Long taskId) {
        return taskService.changeStatusAndReturn(taskId, false);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/delete")
    public void deleteTask(@Valid @RequestBody Long taskId) {
        taskService.deleteTask(taskId);
    }
}
