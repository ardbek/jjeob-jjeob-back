package com.example.jjeobjjeob.restaurant.repository;

import com.example.jjeobjjeob.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByRstntNm(String rstntNm);

}
