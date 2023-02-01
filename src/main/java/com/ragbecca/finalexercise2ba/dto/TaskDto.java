package com.ragbecca.finalexercise2ba.dto;

import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

@Getter
public class TaskDto {

    private String taskName;
    private Long taskCategoryId;
    private Date deadlineDate;
    private Time deadlineTime;
    private String username;

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setTaskCategoryId(Long taskCategoryId) {
        this.taskCategoryId = taskCategoryId;
    }

    public void setDeadlineTime(String deadlineTime) {
        String[] stringTime = deadlineTime.split(":");
        this.deadlineTime = new Time(Integer.valueOf(stringTime[0]), Integer.valueOf(stringTime[1]), 0);
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
