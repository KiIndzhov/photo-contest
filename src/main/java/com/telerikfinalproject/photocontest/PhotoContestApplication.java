package com.telerikfinalproject.photocontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhotoContestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoContestApplication.class, args);
    }

}
