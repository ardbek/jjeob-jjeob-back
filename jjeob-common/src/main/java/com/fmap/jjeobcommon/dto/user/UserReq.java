package com.fmap.jjeobcommon.dto.user;

import com.fmap.jjeobcommon.dto.validate.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserReq {

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Size(min = 2 , message = "닉네임은 2자리 이상이어야 합니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @ValidPassword(message = "8~16자, 최소 하나의 영문자, 최소 하나의 숫자, 최소 하나의 특수문자 포함")
    private String password;

}
