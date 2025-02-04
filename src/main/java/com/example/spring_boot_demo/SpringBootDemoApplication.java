package com.example.spring_boot_demo;

import com.example.spring_boot_demo.run.Location;
import com.example.spring_boot_demo.run.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringBootDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            Run r = new Run(2, "Morning run", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5, Location.INDOOR);
            log.info("Run: {}", r);
        };
    }
}
