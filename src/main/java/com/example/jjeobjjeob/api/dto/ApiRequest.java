package com.example.jjeobjjeob.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRequest {
    private String state;  // 운영상태코드 01: 영업/정상 02: 휴업 03: 폐업 04: 취소/말소/만료/정지/중지
    private String pageSize; // 페이지당 출력 갯수 (def:10)
    private String localCode; // 개방자치단체 코드(신고지역)
    private String bgnYmd; // 인허가일자기준검색 시작일(YYYYMMDD)
    private String endYmd; // 인허가일자기준검색 종료일(YYYYMMDD)
    private String lastModTsBgn; // 데이터갱신일자기준 시작일
    private String lastModTsEnd; // 데이터갱신일자기준 종료일

}
