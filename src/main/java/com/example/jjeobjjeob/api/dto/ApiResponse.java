package com.example.jjeobjjeob.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse {
    public String rowNum; // 번호
    public String opnSfTeamCode; // 개방자치단체 코드
    public String mgtNo; // 관리번호
    public String opnSvcId;  // 개방서비스ID
    public String updateGbn; // 데이터갱신구분
    public String updateDt; // 데이터갱신일자
    public String opnSvcNm; // 개방서비스명
    public String bplcNm; // 사업장명
    public String sitePostNo; // 지번우편번호
    public String siteWhlAddr; // 지번주소
    public String rdnPostNo; // 도로명우편번호
    public String rdnWhlAddr; // 도로명주소
    public String siteArea; // 소재지 면적
    public String apvPermYmd; // 인허가일자
    public String apvCancelYmd; // 인허가취소일자
    public String dcbYmd; // 폐업일자
    public String clgStdt; // 휴업시작일자
    public String clgEnddt; // 휴업종료일자
    public String ropnYmd; // 재개업일자
    public String trdStateGbn; // 영업상태코드
    public String trdStateNm; // 영업상태명
    public String dtlStateGbn; // 상세영업상태코드
    public String dtlStateNm; // 상세영업상태명
    public String x; // 좌표정보(X)
    public String y; // 좌표정보(Y)
    public String lastModTs; // 최종수정일자
//    public String updateNm; // 업태 구분명
    public String siteTel; // 전화번호

}
