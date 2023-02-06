package com.ragbecca.finalexercise2ba.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_gen")
    @SequenceGenerator(name = "user_info_gen", sequenceName = "user_info_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @Getter
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "username", nullable = false)
    @Getter
    @Setter
    private User user;

    @Column(name = "birthday")
    @Getter
    @Setter
    private Date birthday;

    @Getter
    @Setter
    @Column(name = "firstname")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "lastname")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "country")
    private String countryCode;

    @Getter
    @Setter
    @Column(name = "avatarURL")
    private String avatarURL;
}
