package com.example.assessment.util;

import com.example.assessment.SampleApplicationSecrets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * Method to get various types of HttpEntity used to make remote calls
 */
@Configuration
@Slf4j
public class HttpEntityProvider {

    final private SampleApplicationSecrets sampleApplicationSecrets;

    /**
     * Constructor
     *
     * @param sampleApplicationSecrets used to retrieve application secrets like bearer token
     */
    @Autowired
    public HttpEntityProvider(final SampleApplicationSecrets sampleApplicationSecrets) {
        this.sampleApplicationSecrets = sampleApplicationSecrets;
    }

    /**
     * Returns HttpEntity for given class
     *
     * @return HttpEntity for given class for bearer authentication
     */
    public <T> HttpEntity<T> getHttpEntityForBearerAuth(T v) {
        Assert.notNull(sampleApplicationSecrets.getBearertoken(), "BEARER_TOKEN cannot be empty requests will fail");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(sampleApplicationSecrets.getBearertoken());
        return new HttpEntity<>(v, headers);
    }
}
