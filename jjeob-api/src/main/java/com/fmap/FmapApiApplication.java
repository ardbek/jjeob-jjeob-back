package com.fmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fmap")
public class FmapApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FmapApiApplication.class, args);
    }
}