package com.fmap.common;

import lombok.Builder;
import lombok.Getter;

/**
 * Api Resposne Header
 */
@Getter
@Builder
public class ApiHeader {
    /** 응답코드 */
    private int resultCode;
    /** 코드명 */
    private String codeName;
}
