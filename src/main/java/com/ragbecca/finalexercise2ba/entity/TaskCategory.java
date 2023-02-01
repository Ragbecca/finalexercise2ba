package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TaskCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_category_gen")
    @SequenceGenerator(name = "task_category_gen", sequenceName = "task_category_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "categoryName", nullable = false)
    private String categoryName;
}
