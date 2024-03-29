package com.fmap.restaurant.controller;

import com.fmap.common.ApiResponse;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.fmap.common.ApiResponse.failure;
import static com.fmap.common.ApiResponse.success;

@RestController
@RequestMapping("/api/restaurant")
public class restaurantController {

    private final RestaurantService restaurantService;

    public restaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * 음식점 등록
     * @param restaurant
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> registRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant registedRstnt = restaurantService.saveRestaurant(restaurant);
        if (registedRstnt != null) {
            return new ResponseEntity(success(), HttpStatus.OK);
        } else {
            return new ResponseEntity(failure(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 모든 음식점 조회
     * @return
     */
    @GetMapping
    public List<Restaurant> getAllRestaurant() {
        return restaurantService.getAllRestaurant();
    }

    /**
     * id로 음식점 검색
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Restaurant>> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    /**
     * 이름으로 음식점 검색
     * @param rstntNm
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurantByName(@RequestParam String rstntNm) {
        List<Restaurant> restaurants = restaurantService.findRestaurantByName(rstntNm);
        return ResponseEntity.ok(restaurants);
    }
}
