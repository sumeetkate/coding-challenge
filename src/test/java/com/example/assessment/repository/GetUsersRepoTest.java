package com.example.assessment.repository;

import com.example.assessment.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.example.assessment.repository.GetUsersRepo.API_END_POINT_WITH_PAGE;
import static com.example.assessment.repository.GetUsersRepo.API_END_POINT_WITH_USER_ID;
import static com.example.assessment.repository.GetUsersRepo.USER_NOT_PRESENT_MESSAGE;
import static com.example.assessment.repository.GetUsersRepo.USER_PRESENT_MESSAGE;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersRepoTest {

    private Integer TEST_PAGE_NUMBER = 3;
    private Integer TEST_USER_ID = 120;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private User user1, user2;

    @Mock
    Logger logger;

    @InjectMocks
    private GetUsersRepo getUsersRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void test_getUsersForPageNumber() {
        getUsersRepo.getUsersForPageNumber(TEST_PAGE_NUMBER);
        Mockito.verify(restTemplate).getForEntity(API_END_POINT_WITH_PAGE + TEST_PAGE_NUMBER, User[].class);
    }

    @Test
    public void test_getUserWithGiveId_NonEmptyResponse() {
        ResponseEntity<User[]> mockResponse = generateNonEmptyUserResponse();
        when(restTemplate.getForEntity(API_END_POINT_WITH_USER_ID + TEST_USER_ID, User[].class))
                .thenReturn(mockResponse);
        getUsersRepo.getUserWithGiveId(TEST_USER_ID);
        Mockito.verify(restTemplate).getForEntity(API_END_POINT_WITH_USER_ID + TEST_USER_ID, User[].class);
        //Mockito.verify(logger).info(USER_PRESENT_MESSAGE, TEST_USER_ID);
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    public void test_getUserWithGiveId_EmptyResponse() {
        ResponseEntity<User[]> mockResponse = generateEmptyUserResponse();
        when(restTemplate.getForEntity(API_END_POINT_WITH_USER_ID + TEST_USER_ID, User[].class))
                .thenReturn(mockResponse);
        getUsersRepo.getUserWithGiveId(TEST_USER_ID);
        Mockito.verify(restTemplate).getForEntity(API_END_POINT_WITH_USER_ID + TEST_USER_ID, User[].class);
        //Mockito.verify(logger).info(USER_NOT_PRESENT_MESSAGE, TEST_USER_ID);
        Mockito.verifyNoMoreInteractions(logger);
    }

    private ResponseEntity<User[]> generateNonEmptyUserResponse() {
        User[] userArray = new User[2];
        userArray[0] = user1;
        userArray[1] = user2;
        ResponseEntity<User[]> responseEntity =
                ResponseEntity.status(HttpStatus.OK).headers(new HttpHeaders()).body(userArray);
        return responseEntity;
    }

    private ResponseEntity<User[]> generateEmptyUserResponse() {
        User[] userArray = new User[0];
        ResponseEntity<User[]> responseEntity =
                ResponseEntity.status(HttpStatus.OK).headers(new HttpHeaders()).body(userArray);
        return responseEntity;
    }
}