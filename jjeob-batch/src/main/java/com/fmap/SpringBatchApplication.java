package com.fmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
@Slf4j
public class SpringBatchApplication implements CommandLineRunner {

    private JobLauncher jobLauncher;
    private Job updateRestaurantJob;

    public SpringBatchApplication(JobLauncher jobLauncher, Job updateRestaurantJob) {
        this.jobLauncher = jobLauncher;
        this.updateRestaurantJob = updateRestaurantJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
        log.debug("=== SpringBatchApplication run ===");
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(updateRestaurantJob, jobParameters);
    }
}
