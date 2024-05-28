package com.fmap.user.controller;

import com.fmap.common.ApiResponse;
import com.fmap.user.dto.UserReq;
import com.fmap.user.dto.UserRes;
import com.fmap.user.entity.User;
import com.fmap.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * 회원 가입
     *
     * @param userReq
     * @return
     */
    @PostMapping
    public ResponseEntity join(@Valid UserReq userReq) {

        UserRes savedUser = userServiceImpl.join(userReq);

        if (savedUser == null) {
            return ResponseEntity.ok(ApiResponse.failure());
        } else {
            return ResponseEntity.ok(ApiResponse.success("가입 성공!"));
        }

    }

    /**
     * 아이디로 user 검색
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {

        Optional<User> findUser = userServiceImpl.findById(id);

        return findUser.map(user -> ResponseEntity.ok(ApiResponse.success(user)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success()));
    }


}
