package com.example.jjeobjjeob.restaurant.service;

import com.example.jjeobjjeob.restaurant.entity.Restaurant;
import com.example.jjeobjjeob.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * 음식점 등록
     * @param restaurant
     * @return
     */
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * 음식점 전체 조회
     * @return
     */
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    /**
     * 음식점 이름 검색
     * @param rstntNm
     * @return
     */
    public List<Restaurant> findRestaurantByName(String rstntNm) {
        return restaurantRepository.findByRstntNm(rstntNm);
    }


    /**
     * id로 음식점 검색
     *
     * @param id
     * @return
     */
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }
}
