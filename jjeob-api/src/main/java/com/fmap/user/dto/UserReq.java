package com.fmap.user.dto;

import com.fmap.common.validate.ValidPassword;
import com.fmap.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Min(2)
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @ValidPassword
    private String password;

    /**
     * UserReq -> UserEntity
     * @return
     */
    public User toEntity() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }


}
