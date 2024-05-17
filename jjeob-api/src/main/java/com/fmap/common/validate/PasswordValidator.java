package com.fmap.common.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 비밀번호 검증을 위한 클래스
 */

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    // 8~16자, 최소 하나의 영문자, 최소 하나의 숫자, 최소 하나의 특수문자 포함
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
    private Pattern pattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
