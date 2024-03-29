package com.fmap.common;

public enum ResponseType {
    SUCCESS(200),
    FAILURE(400),
    ERROR(500);

    private int code;

    ResponseType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.name();
    }
}
