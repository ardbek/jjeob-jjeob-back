package com.fmap.user.service;

import com.fmap.user.entity.User;
import com.fmap.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }
}
