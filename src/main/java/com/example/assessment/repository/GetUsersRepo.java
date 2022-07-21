package com.example.assessment.repository;

import com.example.assessment.model.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Encapsulates methods to get Users
 */
@Configuration
public class GetUsersRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetUsersRepo.class);
    protected static final String API_END_POINT_WITH_PAGE = "https://gorest.co.in/public/v2/users?page=";
    protected static final String API_END_POINT_WITH_USER_ID = "https://gorest.co.in/public/v2/users?id=";
    protected static final String USER_PRESENT_MESSAGE = "user with Id {} found";
    protected static final String USER_NOT_PRESENT_MESSAGE = "user with Id {} not found";

    private RestTemplate restTemplate;

    /**
     * Constructor
     *
     * @param restTemplate used to make remote calls
     */
    @Autowired
    public void GetUsersRepo(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gets list of users given pageNumber
     *
     * @param pageNumber : pageNumber for which users need to be retrieved
     * @return array of user objects
     * @throws RuntimeException if something goes wrong while making api calls
     */
    public ResponseEntity<User[]> getUsersForPageNumber(@NonNull Integer pageNumber) throws RuntimeException {
        return restTemplate.getForEntity(API_END_POINT_WITH_PAGE + pageNumber, User[].class);
    }

    /**
     * Check if given userId is present
     *
     * @param userId userId that need to be checked
     * @throws RuntimeException if something goes wrong while making api calls
     */
    public void getUserWithGiveId(@NonNull Integer userId) throws RuntimeException {
        ResponseEntity<User[]> response = restTemplate.getForEntity(API_END_POINT_WITH_USER_ID + userId, User[].class);
        if (response != null && response.getBody().length != 0) {
            LOGGER.info(USER_PRESENT_MESSAGE, userId);
        } else {
            LOGGER.error(USER_NOT_PRESENT_MESSAGE, userId);
        }
    }
}
