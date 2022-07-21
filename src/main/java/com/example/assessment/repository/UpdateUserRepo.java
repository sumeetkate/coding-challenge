package com.example.assessment.repository;

import com.example.assessment.model.User;
import com.example.assessment.util.HttpEntityProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Encapsulates method to update user info
 */
@Configuration
@Slf4j
public class UpdateUserRepo {

    protected static final String API_END_POINT = "https://gorest.co.in/public/v2/users/";
    private final HttpEntityProvider httpEntityProvider;
    private final RestTemplate restTemplate;

    /**
     * Constructor
     *
     * @param httpEntityProvider helper to get http entity
     * @param restTemplate       used to make remote calls
     */
    @Autowired
    public UpdateUserRepo(final HttpEntityProvider httpEntityProvider, final RestTemplate restTemplate) {
        this.httpEntityProvider = httpEntityProvider;
        this.restTemplate = restTemplate;
    }

    /**
     * Updates user's name corresponding to it Id
     *
     * @param user    user that will be updated
     * @param newName new name for passed user
     * @return status representing status of operation
     */
    public HttpStatus updateUserName(User user, String newName) {
        user.setName(newName);
        ResponseEntity<User> response = restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.PUT, httpEntityProvider.getHttpEntityForBearerAuth(user), User.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("update user succeeded with status {}", response.getStatusCode());
        } else {
            log.info("update user failed with status {}", response.getStatusCode());
        }

        return response.getStatusCode();
    }
}
