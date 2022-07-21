package com.example.assessment.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Slf4j
class HeadersExtractorTest {

    HttpHeaders headers;

    @InjectMocks
    HeadersExtractor headersExtractor;

    private static String KEY1 = "KEY1";
    private static String KEY2 = "KEY2";

    private static String KEY_VALUE1 = "KEY_VALUE1";
    private static String KEY_VALUE2 = "KEY_VALUE2";

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.set(KEY1, KEY_VALUE1);
        headers.set(KEY2, KEY_VALUE2);
    }

    @Test
    public void test_extractHeaderValue_whenKeyIsPresent() {
        Optional<List<String>> actualResponse = headersExtractor.extractHeaderValue(KEY1, headers);
        Assertions.assertEquals(actualResponse.get().get(0), KEY_VALUE1);
    }

    @Test
    public void test_extractHeaderValue_whenKeyIsNotPresent() {
        Optional<List<String>> actualResponse = headersExtractor.extractHeaderValue("RandomKey", headers);
        Assertions.assertEquals(actualResponse, Optional.empty());
    }

}