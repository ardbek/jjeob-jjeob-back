package com.fmap.user.service;

import com.fmap.jjeobcommon.dto.user.UserReq;
import com.fmap.jjeobcommon.dto.user.UserRes;
import com.fmap.user.entity.User;
import java.util.Optional;

public interface UserService {

    // 회원 가입
    UserRes join(UserReq userReq);

    // 아이디로 유저 검색
    Optional<User> findById(Long id);


}
