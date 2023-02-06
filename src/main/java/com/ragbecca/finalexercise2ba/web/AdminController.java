package com.ragbecca.finalexercise2ba.web;

import com.ragbecca.finalexercise2ba.dto.CreateCategoryRequest;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.service.AdminService;
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
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/users")
    public List<User> getNumberOfUsers() {
        return adminService.getUsersAllWithoutPassword();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add/category")
    public TaskCategory createTaskCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        return adminService.createCategoryAndReturn(createCategoryRequest);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/delete/category")
    public void deleteTaskCategory(@Valid @RequestBody Long categoryId) {
        adminService.deleteCategoryAndDailyIfNeeded(categoryId);
    }
}
