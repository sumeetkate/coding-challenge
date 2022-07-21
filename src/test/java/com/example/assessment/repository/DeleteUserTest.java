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

import static com.example.assessment.repository.DeleteUser.API_END_POINT;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
class DeleteUserTest {

    @Mock
    HttpEntityProvider httpEntityProvider;

    @Mock
    RestTemplate restTemplate;

    @Mock
    HttpEntity httpEntity;

    User user;

    @InjectMocks
    DeleteUser deleteUser;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(123);
        when(httpEntityProvider.getHttpEntityForBearerAuth(user))
                .thenReturn(httpEntity);
    }

    @Test
    public void test_DeleteUser_success() {
        when(restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.DELETE, httpEntity, User.class))
                .thenReturn(successResponse());
        HttpStatus actualResponse = deleteUser.deleteUser(user);
        Assertions.assertEquals(actualResponse, HttpStatus.NO_CONTENT);
    }

    @Test
    public void test_DeleteUser_failed() {
        when(restTemplate.exchange(API_END_POINT + user.getId(), HttpMethod.DELETE, httpEntity, User.class))
                .thenReturn(failResponse());
        HttpStatus actualResponse = deleteUser.deleteUser(user);
        Assertions.assertEquals(actualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<User> successResponse() {
        ResponseEntity<User> userResponseEntity = new ResponseEntity(
                "Test body",
                new HttpHeaders(),
                HttpStatus.NO_CONTENT
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