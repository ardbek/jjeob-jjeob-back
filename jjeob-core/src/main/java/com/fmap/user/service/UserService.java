package com.fmap.user.service;

import com.fmap.user.dto.UserReq;
import com.fmap.user.entity.User;
import java.util.Optional;

public interface UserService {

    // 회원 가입
    User join(UserReq userReq);

    // 아이디로 유저 검색
    Optional<User> findById(Long id);


}
