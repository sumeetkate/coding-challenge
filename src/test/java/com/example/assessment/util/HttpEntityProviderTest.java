package com.example.assessment.util;

import com.example.assessment.SampleApplicationSecrets;
import com.example.assessment.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
class HttpEntityProviderTest {

    private User user;
    private String TEST_USER_NAME = "Jhon Doe";
    private Integer TEST_USER_ID = 1;
    private String BEARER_TOKEN = "beakerToken";

    @Mock
    SampleApplicationSecrets sampleApplicationSecrets;
    @InjectMocks
    HttpEntityProvider httpEntityProvider;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(TEST_USER_ID);
        user.setName(TEST_USER_NAME);
        when(sampleApplicationSecrets.getBearertoken())
                .thenReturn(BEARER_TOKEN);
    }

    @Test
    public void test_getHttpEntityForBearerAuth() {
        HttpEntity<User> actualResponse = httpEntityProvider.getHttpEntityForBearerAuth(user);
        List<User> userList = Arrays.asList(actualResponse.getBody());
        HttpHeaders actualHeaders = actualResponse.getHeaders();

        Assertions.assertEquals(actualHeaders.getContentType(), MediaType.APPLICATION_JSON);
        Assertions.assertEquals(actualHeaders.getAccept(), Collections.singletonList(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(actualHeaders.get("Authorization").get(0), "Bearer " + BEARER_TOKEN);

        Assertions.assertEquals(userList.get(0).getId(), TEST_USER_ID);
        Assertions.assertEquals(userList.get(0).getName(), TEST_USER_NAME);
    }

}