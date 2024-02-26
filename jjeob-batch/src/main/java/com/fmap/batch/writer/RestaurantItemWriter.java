package com.fmap.batch.writer;

import com.fmap.restaurant.entity.Restaurant;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class RestaurantItemWriter implements ItemWriter<Restaurant> {

    @Override
    public void write(Chunk<? extends Restaurant> chunk) throws Exception {

    }
}
