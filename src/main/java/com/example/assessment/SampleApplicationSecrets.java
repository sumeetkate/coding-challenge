package com.example.assessment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("gorest")
public class SampleApplicationSecrets {

    private String bearertoken;

    public String getBearertoken() {
        return this.bearertoken;
    }

    public void setBearertoken(String bearertoken) {
        this.bearertoken = bearertoken;
    }
}
