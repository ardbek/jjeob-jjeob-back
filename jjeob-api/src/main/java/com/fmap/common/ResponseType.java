package com.fmap.common;

import lombok.Getter;

@Getter
public enum ResponseType {
    SUCCESS("200","SUCCESS"),
    FAIL("400", "잘못된 접근"),
    ERROR("500", "ERROR"),
    ERR_NOT_FOUND("404", "페이지 없음"),
//    ERR_INVAILD_TOKEN()

    ;

    private final String resultCode;
    private final String resultMessage;

    ResponseType(String code, String message) {
        this.resultCode = code;
        this.resultMessage = message;
    }

}
