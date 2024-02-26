package com.fmap.batch.processor;

import com.fmap.dto.RestaurantRes;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.utils.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestaurantItemProcessor implements ItemProcessor<RestaurantRes, Restaurant> {

    @Override
    public Restaurant process(RestaurantRes item) throws Exception {

        // 좌표 값 변환 ============================================//
        String latitude = item.getX() != null ? item.getX().trim() : "";
        String longitude = item.getY() != null ? item.getY().trim() : "";

        ProjCoordinate wgs84Coord = null;
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
            wgs84Coord = LocationUtils.CoordinateConversion(latitude, longitude);
        }

        String finalLatitude = (wgs84Coord != null) ? String.valueOf(wgs84Coord.x) : "";
        String finalLongitude = (wgs84Coord != null) ? String.valueOf(wgs84Coord.y) : "";

        // Restaurant 객체 생성, 값 설정
        Restaurant restaurant = Restaurant.builder()
                .apiUpdateDt(item.getUpdateDt())
                .bussSttus(item.getDtlStateNm())
                .closureDt(item.getDcbYmd())
//                .bussType
                .epsg5174X(item.getX())
                .epsg5174y(item.getY())
                .newAddr(item.getRdnWhlAddr())
                .oldAddr(item.getSiteWhlAddr())
                .latitude(finalLatitude)
                .longitude(finalLongitude)
                .newPostCode(item.getRdnPostNo())
                .oldPostCode(item.getSitePostNo())
                .rstntNm(item.getBplcNm())
                .opnSvcNm(item.getOpnSvcNm())
                .managementNum(item.getMgtNo())
                .build();

        return restaurant;
    }
}
