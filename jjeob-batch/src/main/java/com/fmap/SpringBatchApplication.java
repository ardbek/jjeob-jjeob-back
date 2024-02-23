package com.fmap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

    private JobLauncher jobLauncher;
    private Job testJob;

    public SpringBatchApplication(JobLauncher jobLauncher, Job testJob) {
        this.jobLauncher = jobLauncher;
        this.testJob = testJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() throws Exception {
        return args -> {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 잡 파라미터 설정
                    .toJobParameters();
            jobLauncher.run(testJob, jobParameters); // 배치 잡 실행
        };
    }

}
