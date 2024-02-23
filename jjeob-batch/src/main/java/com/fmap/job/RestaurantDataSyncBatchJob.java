package com.fmap.job;

import com.fmap.api.dto.ApiResponse;
import com.fmap.api.service.ApiService;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.utils.LocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestaurantDataSyncBatchJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final ApiService apiService;

    // Job 생성
    @Bean
    public Job testJob(JobRepository jobRepository) {
        return new JobBuilder("testJob", jobRepository)
                .start(testFirstJob(jobRepository))
                .build();
    }

    // step 생성
    @Bean
    public Step testFirstJob(JobRepository jobRepository) {
        return new StepBuilder("testFirstStep", jobRepository)
                .<String, String>chunk(1000, transactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .build();
    }

    /**
     * TODO API로 데이터 호출
     * @return
     */
    @Bean
    public ItemReader<ApiResponse> itemReader() {
        return new ItemReader<ApiResponse>() {
            private int nextItemIndex = 0;
            private List<ApiResponse> responseData;

            @Override
            public ApiResponse read() throws Exception {
                if (responseData == null) {
                    responseData = apiService.fetchAndUpdateData();
                }

                if (nextItemIndex < responseData.size()) {
                    return responseData.get(nextItemIndex++);
                } else {
                    return null;
                }
            }
        };
    }

    /**
     * TODO 데이터 처리 로직 구현
     * @return
     */
    @Bean
    public ItemProcessor<ApiResponse, Restaurant> processor() {
        return new ItemProcessor<ApiResponse, Restaurant>() {
            @Override
            public Restaurant process(ApiResponse apiResponse) throws Exception {

                String latitude = apiResponse.getX();
                String longitude = apiResponse.getY();

                ProjCoordinate wgs84Coord = LocationUtils.CoordinateConversion(latitude, longitude);

                // Restaurant 객체 생성 및 데이터 설정
                Restaurant restaurant = Restaurant.builder()
                        .apiUpdateDt(apiResponse.getUpdateDt())
                        .bussSttus(apiResponse.getDtlStateNm())
                        .closureDt(apiResponse.getDcbYmd())
//                        .bussType()
                        .epsg5174X(apiResponse.getX())
                        .epsg5174y(apiResponse.getY())
                        .newAddr(apiResponse.getRdnWhlAddr())
                        .oldAddr(apiResponse.getSiteWhlAddr())
                        .latitude(String.valueOf(wgs84Coord.x))
                        .longitude(String.valueOf(wgs84Coord.y))
                        .newPostCode(apiResponse.getRdnPostNo())
                        .oldPostCode(apiResponse.getSitePostNo())
                        .rstntNm(apiResponse.getBplcNm())
                        .opnSvcNm(apiResponse.getOpnSvcNm())
                        .managementNum(apiResponse.getMgtNo())
                        .build();


                // DB에서 해당 Restaurant이 이미 존재하는지 조회 (옵션)
                // 예를 들어, name을 기준으로 조회한다고 가정
                // Restaurant existingRestaurant = restaurantRepository.findByName(restaurant.getName());
                // if (existingRestaurant != null) {
                //     // 기존 데이터가 있을 경우, 업데이트 로직 구현
                //     // 예: existingRestaurant.setRating(restaurant.getRating());
                //     // return existingRestaurant;
                // }

                // 새로운 Restaurant 데이터 또는 업데이트된 Restaurant 반환
                return restaurant;
            }
        };
    }

    /**
     * TODO DB에 insert, update
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<YourDataType> writer(DataSource dataSource) {
        return new ItemWriter<YourDataType>() {
            @Override
            public void write(List<? extends YourDataType> items) throws Exception {
                // JdbcTemplate, EntityManager 등을 사용하여 DB에 쓰기
            }
        };
    }


}
