package com.fmap.restaurant.repository;

import com.fmap.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByRstntNm(String rstntNm);

    Optional<Restaurant> findByManagementNum(String managementNum);

}
