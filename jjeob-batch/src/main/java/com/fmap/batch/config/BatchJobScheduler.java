package com.fmap.batch.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BatchJobScheduler {

    private JobLauncher jobLauncher;
    private Job updateRestaurantJob;

    @Scheduled(cron = "0 0 23 * * MON-SAT") //월-토 23시에 실행
    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(updateRestaurantJob, jobParameters);
        } catch (Exception e) {
            log.error("BatchJobScheduler :: ", e);
        }
    }

}
