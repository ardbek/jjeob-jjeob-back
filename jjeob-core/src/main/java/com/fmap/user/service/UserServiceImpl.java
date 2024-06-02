package com.fmap.user.service;

import com.fmap.jjeobcommon.dto.user.UserReq;
import com.fmap.jjeobcommon.dto.user.UserRes;
import com.fmap.user.entity.User;
import com.fmap.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
     * Email 중복 검사
     * @param email
     * @return
     */
    @Override
    public boolean checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @Override
    public boolean checkNickname(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        return user.isPresent();
    }

    /**
     * user 등록
     * @param userReq
     */
    public UserRes join(UserReq userReq) {

        // email, nickname 중복 검사
        Optional<User> existEmail = userRepository.findByEmail(userReq.getEmail());
        Optional<User> existNickname = userRepository.findByNickname(userReq.getNickname());

        if (existEmail.isPresent()) {
            log.debug("UserServiceImpl - join :: duplicate email");
            return null;
        }
        if (existNickname.isPresent()) {
            log.debug("UserServiceImpl - join :: duplicate nickname");
            return null;
        }
        
        // User 객체 생성
        User joinUser = User.builder()
                .email(userReq.getEmail())
                .password(userReq.getPassword())
                .nickname(userReq.getNickname())
                .build();

        User savedUser = userRepository.save(joinUser);

        UserRes savedUserRes = UserRes.builder()
                .nickName(savedUser.getNickname())
                .email(savedUser.getEmail())
                .build();

        return savedUserRes;
    }
}
