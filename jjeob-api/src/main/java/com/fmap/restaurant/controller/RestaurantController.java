package com.fmap.restaurant.controller;

import com.fmap.common.ResponseData;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * 음식점 등록
     * @param restaurant
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseData> registRestaurant(HttpServletRequest request, @RequestBody Restaurant restaurant) {

        ResponseData responseData = new ResponseData();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        String register = request.getRemoteAddr();
        restaurant.setCreatedBy(register);

        Restaurant registedRstnt = restaurantService.saveRestaurant(restaurant);
        if (registedRstnt != null) {
            httpStatus = HttpStatus.OK;
            responseData.setSuccess();
        } else {
            responseData.setError();
        }
        return new ResponseEntity(responseData, new HttpHeaders(), httpStatus);

    }

    /**
     * 모든 음식점 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseData> getAllRestaurant() {

        ResponseData responseData = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;

        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        responseData.setData(restaurants);

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);
    }

    /**
     * id로 음식점 검색
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getRestaurantById(@PathVariable Long id) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseData responseData = new ResponseData();

        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);

        if (restaurant.isPresent()) {
            httpStatus = HttpStatus.OK;
            responseData.setData(restaurant.get());
        }

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);

        /*return restaurantOptional
                .map(restaurant -> ResponseEntity.ok(ApiResponse.success(restaurant)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success()));*/
    }

    /**
     * 이름으로 음식점 검색
     * @param rstntNm
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseData> searchRestaurantByName(@RequestParam String rstntNm) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseData responseData = new ResponseData();

        List<Restaurant> restaurants = restaurantService.findRestaurantByName(rstntNm);

        if (restaurants.isEmpty()) {
            responseData.setError();
            responseData.setResultMessage("찾으시는 데이터가 없습니다.");
        } else {
            httpStatus = HttpStatus.OK;
            responseData.setData(restaurants);
        }

        return new ResponseEntity<>(responseData, new HttpHeaders(), httpStatus);
    }
}
