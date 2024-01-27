package com.example.jjeobjjeob.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Restaurant {

    // PK
    @Id @Column
    private String id;

    // 식당 이름
    @Column
    private String rst_name;

    // (구)주소
    @Column
    private String location;

    // 도로명 주소
    @Column
    private String location2;

    // 위도
    @Column
    private String latitude;

    // 경도
    @Column
    private String longitude;

    // 업태
    @Column
    private String type;



}
