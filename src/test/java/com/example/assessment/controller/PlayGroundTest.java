package com.example.assessment.controller;

import com.example.assessment.datasource.UserListManipulation;
import com.example.assessment.model.User;
import com.example.assessment.repository.DeleteUser;
import com.example.assessment.repository.GetUsersRepo;
import com.example.assessment.repository.UpdateUserRepo;
import com.example.assessment.util.HeadersExtractor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.assessment.controller.PlayGround.DEFAULT_PAGE_ID;
import static com.example.assessment.controller.PlayGround.FAILURE_RESPONSE;
import static com.example.assessment.controller.PlayGround.HEADER_KEY_FOR_NUMBER_OF_PAGES;
import static com.example.assessment.controller.PlayGround.NEW_NAME_FOR_LAST_USER;
import static com.example.assessment.controller.PlayGround.NON_EXISTENT_USER_ID;
import static com.example.assessment.controller.PlayGround.SUCCESS_RESPONSE;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
class PlayGroundTest {

    @Mock
    GetUsersRepo getUsersRepo;
    @Mock
    HeadersExtractor headersExtractor;
    @Mock
    UpdateUserRepo updateUserRepo;
    @Mock
    DeleteUser deleteUser;
    @Mock
    UserListManipulation userListManipulation;
    @Mock
    User user1, user2, mockLastUser;
    @Mock
    HttpHeaders headers;

    @InjectMocks
    private PlayGround playGround;

    @Test
    public void test_playWithApis_forNonEmptyUserResponse() throws RuntimeException {
        ResponseEntity<User[]> mockResponse = generateNonEmptyUserResponse();
        when(getUsersRepo.getUsersForPageNumber(DEFAULT_PAGE_ID))
                .thenReturn(mockResponse);
        when(userListManipulation.getLastUser(mockResponse.getBody()))
                .thenReturn(mockLastUser);

        ResponseEntity<?> actualResponse = playGround.playWithApis();

        Mockito.verify(headersExtractor).extractHeaderValue(HEADER_KEY_FOR_NUMBER_OF_PAGES, mockResponse.getHeaders());
        Mockito.verify(userListManipulation).sortUsersInAscendingOrderOfName(mockResponse.getBody());
        Mockito.verify(userListManipulation).getLastUser(mockResponse.getBody());
        Mockito.verify(updateUserRepo).updateUserName(mockLastUser, NEW_NAME_FOR_LAST_USER);
        Mockito.verify(deleteUser).deleteUser(mockLastUser);
        Mockito.verify(getUsersRepo).getUserWithGiveId(NON_EXISTENT_USER_ID);

        Assertions.assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(actualResponse.getBody(), SUCCESS_RESPONSE);
    }

    @Test
    public void test_playWithApis_forEmptyUserResponse() throws RuntimeException {
        ResponseEntity<User[]> mockResponse = generateEmptyUserResponse();
        when(getUsersRepo.getUsersForPageNumber(DEFAULT_PAGE_ID))
                .thenReturn(mockResponse);

        ResponseEntity<?> actualResponse = playGround.playWithApis();
        Mockito.verifyNoInteractions(headersExtractor);
        Mockito.verifyNoInteractions(userListManipulation);
        Mockito.verifyNoInteractions(userListManipulation);
        Mockito.verifyNoInteractions(updateUserRepo);
        Mockito.verifyNoInteractions(deleteUser);
        Mockito.verify(getUsersRepo).getUserWithGiveId(NON_EXISTENT_USER_ID);

        Assertions.assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(actualResponse.getBody(), SUCCESS_RESPONSE);
    }

    @Test
    public void test_playWithApis_forWhenExceptionOccurs() throws RuntimeException {
        when(getUsersRepo.getUsersForPageNumber(DEFAULT_PAGE_ID))
                .thenThrow(new RuntimeException(""));
        ResponseEntity<?> actualResponse = playGround.playWithApis();
        Assertions.assertEquals(actualResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertEquals(actualResponse.getBody(), FAILURE_RESPONSE);
    }

    private ResponseEntity<User[]> generateNonEmptyUserResponse() {
        User[] userArray = new User[2];
        userArray[0] = user1;
        userArray[1] = user2;
        ResponseEntity<User[]> responseEntity =
                ResponseEntity.status(HttpStatus.OK).headers(headers).body(userArray);
        return responseEntity;
    }

    private ResponseEntity<User[]> generateEmptyUserResponse() {
        ResponseEntity<User[]> responseEntity =
                ResponseEntity.status(HttpStatus.OK).headers(headers).body(null);
        return responseEntity;
    }
}