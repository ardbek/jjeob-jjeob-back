package com.fmap.user.controller;

import com.fmap.common.ApiResponse;
import com.fmap.user.entity.User;
import com.fmap.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {

        Optional<User> findUser = userService.findById(id);

        return findUser.map(user -> ResponseEntity.ok(ApiResponse.success(user)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success()));
    }


}
