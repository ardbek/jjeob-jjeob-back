package com.fmap.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestaurantBatchConfig {

    private final JobRepository jobRepository;
    private final Step updateRestaurantStep;

    /**
     * Job 설정
     * @return
     */
    @Bean
    public Job updateRestaurantJob() {
        return new JobBuilder("updateRestaurantJob", jobRepository)
                .start(updateRestaurantStep)
                .build();
    }

}
