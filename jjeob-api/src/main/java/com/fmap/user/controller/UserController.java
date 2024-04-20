package com.fmap.user.controller;

import com.fmap.common.ApiResponse;
import com.fmap.user.dto.UserReq;
import com.fmap.user.entity.User;
import com.fmap.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 아이디로 user 검색
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {

        Optional<User> findUser = userService.findById(id);

        return findUser.map(user -> ResponseEntity.ok(ApiResponse.success(user)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success()));
    }

    /**
     * user 등록
     * @param userReq
     * @return
     */
    @PostMapping
    public ResponseEntity join(UserReq userReq) {

        User joinUser = User.builder()
                .email(userReq.getEmail())
                .userId(userReq.getUserId())
                .name(userReq.getName())
                .password(userReq.getPassword())
                .build();

        boolean joinFlag = userService.join(joinUser);

        if (joinFlag) {
            return ResponseEntity.ok(ApiResponse.success("가입 성공!"));
        } else {
            return ResponseEntity.ok(ApiResponse.failure());
        }
    }


}
