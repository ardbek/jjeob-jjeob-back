package com.fmap.jjeobcommon.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
public class VerificationCodeUtil {

    // 영문 대문자 + 숫자 6자리 인증번호 생성
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateVerificationCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        log.debug("======= VerificationCodeUtil 인증번호 :: {}",sb.toString());
        return sb.toString();
    }

}
