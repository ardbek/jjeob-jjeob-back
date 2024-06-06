package com.fmap.email.service.impl;

import com.fmap.email.service.EmailVerificationService;
import com.fmap.jjeobcommon.util.EmailUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailUtil emailUtil;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long VERIFICATION_CODE_EXPIRATION_MINUTE = 10;

    public EmailVerificationServiceImpl(EmailUtil emailUtil, RedisTemplate<String, Object> redisTemplate) {
        this.emailUtil = emailUtil;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 인증 번호 발급
     * @param email
     * @param verificationCode
     */
    public void saveVerificationCode(String email, String verificationCode) {
        redisTemplate.opsForValue().set(email, verificationCode, VERIFICATION_CODE_EXPIRATION_MINUTE, TimeUnit.MINUTES);
    }

    /**
     * 인증 번호 조회
     * @param email
     * @return
     */
    public String getVerificationCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    /**
     * 인증 번호 삭제
     * @param email
     */
    public void deleteVerificationCode(String email) {
        redisTemplate.delete(email);
    }
}
