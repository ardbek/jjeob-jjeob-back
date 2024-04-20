package com.fmap.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReq {
    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotEmpty(message = "사용자 ID는 필수입니다.")
    private String userId;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;


}
