package com.fmap.user.service;

import com.fmap.user.entity.User;
import com.fmap.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * id로 user 검색
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    /**
     * user 등록
     * @param joinUser
     */
    public boolean join(User joinUser) {
        User savedUser = userRepository.save(joinUser);
        if (savedUser != null) {
            return true;
        } else {
            return false;
        }
    }
}
