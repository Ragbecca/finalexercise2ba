package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
public class Website {
    @Id
    @Getter
    @SequenceGenerator(name = "websiteGen", sequenceName = "websiteSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "websiteGen")
    @Column(name = "websiteID", nullable = false)
    private Long websiteID;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "url", nullable = false)
    private String url;

    @Getter
    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Getter
    @Setter
    @Column(name = "clicks", nullable = false)
    @Value("0")
    private int clicks;

    @Getter
    @Setter
    @Column(name = "iconURL")
    private String icon;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;
}
