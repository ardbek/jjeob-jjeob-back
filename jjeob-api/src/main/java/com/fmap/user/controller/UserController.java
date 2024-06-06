package com.fmap.user.controller;

import com.fmap.common.ResponseData;
import com.fmap.email.service.EmailVerificationService;
import com.fmap.jjeobcommon.dto.user.UserReq;
import com.fmap.jjeobcommon.dto.user.UserRes;
import com.fmap.jjeobcommon.util.VerificationCodeUtil;
import com.fmap.user.entity.User;
import com.fmap.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final EmailVerificationService emailVerificationService;

    public UserController(UserServiceImpl userServiceImpl, EmailVerificationService emailVerificationService) {
        this.userServiceImpl = userServiceImpl;
        this.emailVerificationService = emailVerificationService;
    }

    /**
     * 회원 가입
     *
     * @param userReq
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseData> join(@Valid @RequestBody UserReq userReq) {

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userReq.setPassword(passwordEncoder.encode(userReq.getPassword()));

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseData responseData = new ResponseData();

        UserRes savedUser = userServiceImpl.join(userReq);

        if (savedUser == null) {
            responseData.setError();
        } else {
            httpStatus = HttpStatus.OK;
            responseData.setSuccess();
            responseData.setData(savedUser);
        }

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);
    }

    /**
     * 이메일 중복 검사
     *
     * @param email
     * @return
     */
    @PostMapping("/checkEmail")
    public ResponseEntity<ResponseData> checkEmail(@Valid @RequestParam @NotNull String email) {

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData responseData = new ResponseData();

        boolean isExistEmail = userServiceImpl.checkEmail(email);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isExistEmail", isExistEmail);

        responseData.setSuccess();
        responseData.setData(resultMap);

        if (isExistEmail) {
            log.debug("중복");
        }

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);

    }

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @PostMapping("/checkNickname")
    public ResponseEntity<ResponseData> checkNickname(@Valid @RequestParam @NotNull String nickname) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData responseData = new ResponseData();

        boolean isExistNickname = userServiceImpl.checkNickname(nickname);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isExistNickname", isExistNickname);
        responseData.setSuccess();
        responseData.setData(resultMap);
        if (isExistNickname) {
            log.debug("닉네임 중복");
        }

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);
    }

    /**
     * 아이디로 user 검색
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getUserById(@PathVariable Long id) {

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData responseData = new ResponseData();

        Optional<User> findUser = userServiceImpl.findById(id);

        if (findUser.isPresent()) {
            responseData.setSuccess();
            responseData.setData(findUser.get());
        } else {
            responseData.setError("400", "사용자가 존재하지 않습니다.");
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(responseData, new HttpHeaders(), httpStatus);

    }

    /**
     * 인증번호 메일 발송 (10분)
     * @param email
     * @return
     */
    @GetMapping("/sendMail")
    public ResponseEntity<ResponseData> sendMail(@Valid @RequestParam @NotNull String email) {

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData responseData = new ResponseData();

        // 인증번호 발급 , redis 저장
        String verificationCode = VerificationCodeUtil.generateVerificationCode(6);
        emailVerificationService.saveVerificationCode(email, verificationCode);

        log.debug("email : {}", email);
        log.debug("verificationCode : {} ", verificationCode);


        return new ResponseEntity<>(responseData,new HttpHeaders(),httpStatus);
    }



}
