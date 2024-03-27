package com.fmap.restaurant.entity;

import com.fmap.common.Entity.BaseEntity;
import com.fmap.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@SequenceGenerator(
        name = "RSTNT_SEQ_GENERATOR",
        sequenceName = "RSTNT_SEQ"
)

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RSTNT_SEQ_GENERATOR"
    )
    @Column(name="RSTNT_NO")
    private Long restaurantNo; // PK
    @Column
    private String managementNum; // api pk
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
    private String apiUpdateDt; // 데이터 갱신 일자 (api로 받아오는 값)
//    @Column
//    private String bussType; // 업태 구분 (한식, 중식, 기타)
    @Column
    private String epsg5174X; // EPSG:5174 - X 좌표
    @Column
    private String epsg5174y; // EPSG:5174 - Y 좌표
    @Column
    private String longitude; // WGS84 - X 좌표 (경도)
    @Column
    private String latitude; // WGS84 - Y 좌표(위도)

    @OneToOne(mappedBy = "restaurant")
    private Post post;

    @Builder
    public Restaurant(Long restaurantNo, String managementNum, String opnSvcNm, String bussSttus, String closureDt, String oldPostCode, String oldAddr, String newPostCode, String newAddr, String rstntNm, String apiUpdateDt, /*String bussType,*/ String epsg5174X, String epsg5174y, String longitude, String latitude) {
        this.restaurantNo = restaurantNo;
        this.managementNum = managementNum;
        this.opnSvcNm = opnSvcNm;
        this.bussSttus = bussSttus;
        this.closureDt = closureDt;
        this.oldPostCode = oldPostCode;
        this.oldAddr = oldAddr;
        this.newPostCode = newPostCode;
        this.newAddr = newAddr;
        this.rstntNm = rstntNm;
        this.apiUpdateDt = apiUpdateDt;
        /*this.bussType = bussType;*/
        this.epsg5174X = epsg5174X;
        this.epsg5174y = epsg5174y;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
