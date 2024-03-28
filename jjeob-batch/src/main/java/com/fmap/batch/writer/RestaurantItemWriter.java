package com.fmap.batch.writer;

import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.repository.RestaurantRepository;
import com.fmap.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantItemWriter implements ItemWriter<Restaurant> {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void write(Chunk<? extends Restaurant> chunk) throws Exception {
        for (Restaurant item : chunk) {
            Restaurant existingRestaurant = restaurantRepository.findByManagementNum(item.getManagementNum())
                    .orElse(null);

            if (existingRestaurant != null) {
                updateExistingRestaurant(existingRestaurant, item);
            } else {
                restaurantRepository.save(item);
            }
        }
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
