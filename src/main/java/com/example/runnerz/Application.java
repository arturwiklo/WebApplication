package com.example.runnerz;

import com.example.runnerz.run.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

import static com.example.runnerz.run.Location.OUTDOOR;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            Run run = new Run(1, "First Run", LocalDateTime.now(),
                    LocalDateTime.now().plusHours(1), 4, OUTDOOR);
            log.info("Run: {}", run);
        };
    }
}
