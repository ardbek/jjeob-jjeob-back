package com.fmap.common;

import lombok.Data;

@Data
public class ResponseData {

    // 결과코드
    private String resultCode;

    // 결과 메세지
    private String resultMessage;

    // 데이터
    private Object data;

    public void setSuccess() {
        this.resultCode = ResultEnum.SUCCESS.getResultCode();
        this.resultMessage = ResultEnum.SUCCESS.getResultMessage();
    }

    public void setError() {
        this.resultCode = ResultEnum.ERROR.getResultCode();
        this.resultMessage = ResultEnum.ERROR.getResultMessage();
    }

    public void setError(String message) {
        this.resultCode = ResultEnum.ERROR.getResultCode();
        this.resultMessage = message;
    }

    public void setError(String code, String message) {
        this.resultCode = code;
        this.resultMessage = message;
    }

}
