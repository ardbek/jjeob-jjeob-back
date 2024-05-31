package com.fmap.user.repository;


import com.fmap.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String username);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);




}
