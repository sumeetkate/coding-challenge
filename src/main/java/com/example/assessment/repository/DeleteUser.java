package com.example.assessment.repository;

import com.example.assessment.SampleApplicationSecrets;
import com.example.assessment.model.User;
import com.example.assessment.util.HttpEntityProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
/**
 * Makes call to delete user api
 */
public class DeleteUser {

    protected static final String API_END_POINT = "https://gorest.co.in/public/v2/users/";
    private final HttpEntityProvider httpEntityProvider;
    private RestTemplate restTemplate;

    /**
     * @param httpEntityProvider: helper to get http entity
     * @param restTemplate        : used to make remote calls
     */
    @Autowired
    public DeleteUser(final HttpEntityProvider httpEntityProvider,
                      final RestTemplate restTemplate
    ) {
        this.httpEntityProvider = httpEntityProvider;
        this.restTemplate = restTemplate;
    }

    /**
     * Deletes given user using userId
     *
     * @param user user that needs to be deleted
     * @return status code depicting if operation was successful
     */
    public HttpStatus deleteUser(User user) {
        
        ResponseEntity<User> response = restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.DELETE, httpEntityProvider.getHttpEntityForBearerAuth(user), User.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("delete user succeeded with status {}", response.getStatusCode());
        } else {
            log.info("delete user failed with status {}", response.getStatusCode());
        }
        return response.getStatusCode();
    }
}
