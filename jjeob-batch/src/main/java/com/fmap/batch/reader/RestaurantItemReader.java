package com.fmap.batch.reader;

import com.fmap.dto.RestaurantRes;
import com.fmap.utils.LocalDataApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class RestaurantItemReader implements ItemReader<RestaurantRes> {

    private final LocalDataApiUtils localDataApiUtils;
    private Iterator<RestaurantRes> restaurantResIterator;

    public RestaurantItemReader(LocalDataApiUtils utils) {
        this.localDataApiUtils = utils;
        getDataFromApi();
    }

    private void getDataFromApi() {
        List<RestaurantRes> restaurants = localDataApiUtils.fetchAndUpdateData();
        this.restaurantResIterator = restaurants.iterator();
        log.info("restaurants : {}", restaurants.get(1));
    }

    @Override
    public RestaurantRes read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (restaurantResIterator != null && restaurantResIterator.hasNext()) {
            return restaurantResIterator.next();
        }
        return null;
    }
}