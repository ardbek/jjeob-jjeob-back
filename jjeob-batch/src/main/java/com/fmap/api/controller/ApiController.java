package com.fmap.api.controller;

import com.fmap.batch.processor.RestaurantItemProcessor;
import com.fmap.dto.RestaurantReq;
import com.fmap.dto.RestaurantRes;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.repository.RestaurantRepository;
import com.fmap.utils.LocalDataApiUtils;
import com.fmap.utils.LocationUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    private LocalDataApiUtils localDataApiUtils;

    @Autowired
    private RestaurantItemProcessor restaurantItemProcessor;

    @Autowired
    private RestaurantRepository restaurantRepository;


    @RequestMapping(value = "/getApiCall.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSampleApi(HttpServletRequest req, HttpServletResponse res, RestaurantReq apiReq) throws Exception {
        List<RestaurantRes> updatedDataList = localDataApiUtils.fetchAndUpdateData();

        List<Restaurant> restaurants = new ArrayList<>();

        for (RestaurantRes restaurantRes : updatedDataList) {
            Restaurant restaurant = restaurantItemProcessor.process(restaurantRes);
            restaurants.add(restaurant);
        }

        for (Restaurant item : restaurants) {
            Restaurant existingRestaurant = restaurantRepository.findByManagementNum(item.getManagementNum())
                    .orElse(null);

            if (existingRestaurant != null) {
                updateExistingRestaurant(existingRestaurant, item);
            } else {
                restaurantRepository.save(item);
            }
        }



        // 웹에 출력 테스트
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(new Gson().toJson(updatedDataList));
    }

    private void updateExistingRestaurant(Restaurant existingRestaurant, Restaurant newItem) {

        String latitude = newItem.getEpsg5174X() != null ? newItem.getEpsg5174X().trim() : "";
        String longitude = newItem.getEpsg5174y() != null ? newItem.getEpsg5174y().trim() : "";

        ProjCoordinate wgs84Coord = null;
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
            wgs84Coord = LocationUtils.CoordinateConversion(latitude, longitude);
        }

        String finalLatitude = (wgs84Coord != null) ? String.valueOf(wgs84Coord.x) : "";
        String finalLongitude = (wgs84Coord != null) ? String.valueOf(wgs84Coord.y) : "";

        existingRestaurant.builder().apiUpdateDt(newItem.getApiUpdateDt())
                .bussSttus(newItem.getBussSttus())
                .closureDt(newItem.getClosureDt())
//                .bussType
                .epsg5174X(newItem.getEpsg5174X())
                .epsg5174y(newItem.getEpsg5174y())
                .newAddr(newItem.getNewAddr())
                .oldAddr(newItem.getOldAddr())
                .latitude(finalLatitude)
                .longitude(finalLongitude)
                .newPostCode(newItem.getNewPostCode())
                .oldPostCode(newItem.getOldPostCode())
                .rstntNm(newItem.getRstntNm())
                .opnSvcNm(newItem.getOpnSvcNm())
                .managementNum(newItem.getManagementNum())
                .siteTel(newItem.getSiteTel())
                .build();

        restaurantRepository.save(existingRestaurant);
    }

}