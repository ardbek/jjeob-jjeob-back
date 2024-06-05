package com.fmap.email.service.impl;

import com.fmap.email.service.EmailVerificationService;
import com.fmap.jjeobcommon.util.EmailUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailUtil emailUtil;

    private final RedisTemplate<String, Object> redisTemplate;

    public EmailVerificationServiceImpl(EmailUtil emailUtil, RedisTemplate<String, Object> redisTemplate) {
        this.emailUtil = emailUtil;
        this.redisTemplate = redisTemplate;
    }
}
