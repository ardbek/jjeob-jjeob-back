package com.fmap.email.service;

public interface EmailVerificationService {
    void saveVerificationCode(String email, String verificationCode);

    String getVerificationCode(String email);

    void deleteVerificationCode(String email);

}
