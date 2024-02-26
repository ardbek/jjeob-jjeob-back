package com.fmap.batch.writer;

import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantItemWriter implements ItemWriter<Restaurant> {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void write(Chunk<? extends Restaurant> chunk) throws Exception {
        for (Restaurant restaurant : chunk) {
            restaurantRepository.save(restaurant);
        }
    }
}
