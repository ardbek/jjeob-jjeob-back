package com.fmap.restaurant.controller;

import com.fmap.common.ApiResponse;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fmap.common.ApiResponse.failure;
import static com.fmap.common.ApiResponse.success;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final String RESULT_CODE = "RESULT_CODE";
    private final String RESULT_MSG = "RESULT_MSG";
    private final String EMPTY = "EMPTY";
    private final String NOT_FOUND = "NOT_FOUND";

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * 음식점 등록
     * @param restaurant
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> registRestaurant(HttpServletRequest request, @RequestBody Restaurant restaurant) {

        String register = request.getRemoteAddr();
        restaurant.setCreatedBy(register);

        Restaurant registedRstnt = restaurantService.saveRestaurant(restaurant);
        if (registedRstnt != null) {
            return new ResponseEntity(success(registedRstnt), HttpStatus.OK);
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
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Restaurant>> getRestaurantById(@PathVariable Long id) {

        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(id);

        return restaurantOptional
                .map(restaurant -> ResponseEntity.ok(ApiResponse.success(restaurant)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.success()));
    }

    /**
     * 이름으로 음식점 검색
     * @param rstntNm
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurantByName(@RequestParam String rstntNm) {

        List<Restaurant> restaurants = restaurantService.findRestaurantByName(rstntNm);

        if (restaurants.isEmpty()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(RESULT_MSG, EMPTY);
            return new ResponseEntity(success(resultMap), HttpStatus.OK);
        } else {
            return ResponseEntity.ok(restaurants);
        }

    }
}
