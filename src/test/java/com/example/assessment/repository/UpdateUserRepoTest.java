package com.example.assessment.repository;

import com.example.assessment.model.User;
import com.example.assessment.util.HttpEntityProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static com.example.assessment.repository.UpdateUserRepo.API_END_POINT;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
class UpdateUserRepoTest {

    @Mock
    HttpEntityProvider httpEntityProvider;

    @Mock
    RestTemplate restTemplate;

    @Mock
    HttpEntity httpEntity;

    @Mock
    User user;

    @InjectMocks
    UpdateUserRepo updateUserRepo;

    private String NEW_USER_NAME = "JHON DOE";

    @BeforeEach
    void setUp() {
        when(httpEntityProvider.getHttpEntityForBearerAuth(user))
                .thenReturn(httpEntity);
    }

    @Test
    public void test_UpdateUser_success() {
        when(restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.PUT, httpEntity, User.class))
                .thenReturn(successResponse());
        HttpStatus actualResponse = updateUserRepo.updateUserName(user, NEW_USER_NAME);
        Assertions.assertEquals(actualResponse, HttpStatus.OK);
    }

    @Test
    public void test_UpdateUser_failed() {
        when(restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.PUT, httpEntity, User.class))
                .thenReturn(failResponse());
        HttpStatus actualResponse = updateUserRepo.updateUserName(user, NEW_USER_NAME);
        Assertions.assertEquals(actualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<User> successResponse() {
        ResponseEntity<User> userResponseEntity = new ResponseEntity(
                "Test body",
                new HttpHeaders(),
                HttpStatus.OK
        );
        return userResponseEntity;
    }

    private ResponseEntity<User> failResponse() {
        ResponseEntity<User> userResponseEntity = new ResponseEntity(
                "Test body",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return userResponseEntity;
    }
}

