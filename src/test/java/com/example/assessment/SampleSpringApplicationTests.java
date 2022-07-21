package com.example.assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SampleSpringApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(restTemplate);
    }

}
