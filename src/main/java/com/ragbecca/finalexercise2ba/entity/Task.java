package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_gen")
    @SequenceGenerator(name = "task_gen", sequenceName = "task_seq", allocationSize = 1)
    @Column(name = "taskID", nullable = false)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "creatorUsername", nullable = false)
    private String username;

    @Getter
    @Setter
    @Column(name = "taskName", nullable = false)
    private String taskName;

    @Getter
    @Setter
    @JoinColumn(name = "taskCategory", nullable = false)
    @ManyToOne
    private TaskCategory taskCategory;

    @Getter
    @Setter
    @Column(name = "deadlineDate")
    private Date deadlineDate;

    @Getter
    @Setter
    @Column(name = "deadlineTime")
    private Time deadlineTime;

    @Getter
    @Setter
    @Column(name = "status")
    private boolean status;
}
