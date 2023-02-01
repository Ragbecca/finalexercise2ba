package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
public class QuoteToday {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_today_gen")
    @SequenceGenerator(name = "quote_today_gen", sequenceName = "quote_today_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @Getter
    private Long id;

    @Getter
    @Setter
    @JoinColumn(name = "quote", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Quote quote;

    @Getter
    @Setter
    private Date dateActive;
}
