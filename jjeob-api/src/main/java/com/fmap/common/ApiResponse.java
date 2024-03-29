package com.fmap.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fmap.common.ResponseType.*;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    @Builder
    public ApiResponse(ResponseType responseType, T data) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public static ApiResponse success(){
        return ApiResponse.builder()
                .responseType(SUCCESS)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS, data);
    }

    public static ApiResponse failure() {
        return ApiResponse.builder()
                .responseType(FAILURE)
                .build();
    }

    public static ApiResponse error() {
        return ApiResponse.builder()
                .responseType(ERROR)
                .build();
    }

}
