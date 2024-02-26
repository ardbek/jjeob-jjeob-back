package com.fmap.batch.step;

import com.fmap.batch.processor.RestaurantItemProcessor;
import com.fmap.batch.reader.RestaurantItemReader;
import com.fmap.batch.writer.RestaurantItemWriter;
import com.fmap.dto.RestaurantRes;
import com.fmap.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class UpdateRestaurantStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RestaurantItemReader restaurantItemReader;
    private final RestaurantItemProcessor restaurantItemProcessor;
    private RestaurantItemWriter restaurantItemWriter;

    /**
     * Step 설정 : Reader, Processor, Writer 연결
     * @return
     */
    @Bean
    public Step updateRestaurantStep() {
        return new StepBuilder("updateRestaurantStep", jobRepository)
                .<RestaurantRes, Restaurant>chunk(10, transactionManager) // 한 번에 처리할 트랜잭션 단위 10
                .reader(restaurantItemReader)
                .processor(restaurantItemProcessor)
                .writer(restaurantItemWriter)
                .build();
    }
}
