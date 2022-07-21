package com.example.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Entry point for application
 */
@SpringBootApplication
@EnableConfigurationProperties(SampleApplicationSecrets.class)
public class SampleSpringApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String getBearerToken() {
        return System.getenv("bearerToken");
    }

    public static void main(String[] args) {

        System.out.println("main started");
        for (String arg : args) {
            System.out.println(arg);
        }

        SpringApplication.run(SampleSpringApplication.class, args);
    }
}