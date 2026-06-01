package com.roger.mystery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RogerMysteryApplication {
    public static void main(String[] args) {
        SpringApplication.run(RogerMysteryApplication.class, args);
    }
}
