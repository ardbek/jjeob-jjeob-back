package com.fmap.jjeobcommon.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRes {
    private String email;
    private String nickName;
}
