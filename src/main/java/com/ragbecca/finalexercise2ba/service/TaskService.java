package com.ragbecca.finalexercise2ba.service;

import com.ragbecca.finalexercise2ba.dto.TaskDto;
import com.ragbecca.finalexercise2ba.dto.TaskEditDto;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    public List<Task> getAllTasksUser(String username) {
        return taskRepository.findAllByUsername(username);
    }

    public Task createTaskAndReturn(TaskDto taskDto) {
        Task task = new Task();
        TaskCategory category = taskCategoryRepository.findById(taskDto.getTaskCategoryId()).get();
        task.setTaskCategory(category);
        task.setStatus(false);
        task.setTaskName(taskDto.getTaskName());
        if (taskDto.getDeadlineDate() != null) {
            task.setDeadlineDate(taskDto.getDeadlineDate());
            if (taskDto.getDeadlineTime() != null) {
                task.setDeadlineTime(taskDto.getDeadlineTime());
            } else {
                task.setDeadlineTime(Time.valueOf(LocalTime.MIDNIGHT));
            }
        }
        task.setUsername(taskDto.getUsername());
        taskRepository.save(task);
        return task;
    }

    public Task editTaskAndReturn(TaskEditDto taskEditDto) {
        Task task = taskRepository.findById(String.valueOf(taskEditDto.getId())).get();

        if (!Objects.equals(task.getTaskName(), taskEditDto.getTaskName())) {
            task.setTaskName(taskEditDto.getTaskName());
        }

        if (!Objects.equals(task.getTaskCategory().getId(), taskEditDto.getTaskCategoryId())) {
            task.setTaskCategory(taskCategoryRepository.findById(taskEditDto.getTaskCategoryId()).get());
        }

        if (task.getDeadlineDate() != taskEditDto.getDeadlineDate()) {
            task.setDeadlineDate(taskEditDto.getDeadlineDate());
        }

        if (task.getDeadlineTime() != taskEditDto.getDeadlineTime()) {
            task.setDeadlineTime(taskEditDto.getDeadlineTime());
        }
        taskRepository.save(task);
        return task;
    }

    public Task changeStatusAndReturn(Long taskId, boolean statusToSet) {
        if (taskRepository.findById(String.valueOf(taskId)).isEmpty()) {
            return null;
        }
        Task task = taskRepository.findById(String.valueOf(taskId)).get();
        task.setStatus(statusToSet);
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long taskId) {
        if (taskRepository.findById(String.valueOf(taskId)).isEmpty()) {
            return;
        }
        Task task = taskRepository.findById(String.valueOf(taskId)).get();
        taskRepository.delete(task);
    }

}
