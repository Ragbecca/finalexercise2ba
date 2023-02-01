package com.ragbecca.finalexercise2ba.repository;

import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, User> {

    Optional<UserInfo> findByUser(User user);

}