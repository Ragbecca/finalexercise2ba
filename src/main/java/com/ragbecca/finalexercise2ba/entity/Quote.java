package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_gen")
    @SequenceGenerator(name = "quote_gen", sequenceName = "quote_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "quote")
    private String quote;

    @Getter
    @Setter
    @Column(name = "author")
    private String author;

}
