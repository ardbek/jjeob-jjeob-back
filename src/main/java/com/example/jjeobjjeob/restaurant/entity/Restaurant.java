package com.example.jjeobjjeob.restaurant.entity;

import com.example.jjeobjjeob.common.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "RSTNT_SEQ_GENERATOR",
        sequenceName = "RSTNT_SEQ"
)

@Getter
@Setter
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RSTNT_SEQ_GENERATOR"
    )
    private Long id; // PK
    @Column
    private String opnSvcNm; // 개방 서비스 명 (ex:일반 음식점)
    @Column
    private String bussSttus; // 영업 상태 (폐업, 영업, 휴업)
    @Column
    private String closureDt; // 폐업 일자
    @Column
    private String oldPostCode; // 우편번호(구 주소)
    @Column
    private String oldAddr; // 소재지 전체 주소 (구 주소)
    @Column
    private String newPostCode; // 도로명 우편번호
    @Column
    private String newAddr; // 도로명 전체 주소
    @Column
    private String rstntNm; // 사업장 명 (음식점 명)
    @Column
    private LocalDateTime apiUpdateDt; // 데이터 갱신 일자 (api로 받아오는 값)
    @Column
    private String bussType; // 업태 구분 (한식, 중식, 기타)
    @Column
    private String epsg5174X; // EPSG:5174 - X 좌표
    @Column
    private String epsg5174y; // EPSG:5174 - Y 좌표
    @Column
    private String longitude; // WGS84 - X 좌표 (경도)
    @Column
    private String latitude; // WGS84 - Y 좌표(위도)

    // 공통
    // 최초 등록일
    // 최초 등록자 (ip 또는 id -> 하나만 받을지 둘 다 받을지)
    // 최종 수정일
    // 최종 수정자 (ip 또는 id)


}
