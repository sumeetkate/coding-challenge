package com.example.assessment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Encapsulates method to read HttpHeaders data
 */
@Configuration
@Slf4j
public class HeadersExtractor {

    /**
     * Returns optional representing the value of header corresponding to key passed
     *
     * @param headerKey key that needs to be looked up in HttpHeaders
     * @param headers   HttpHeaders
     * @return Optional if key has corresponding entry in headers map
     */
    public Optional<List<String>> extractHeaderValue(final String headerKey, HttpHeaders headers) {

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (key.equals(headerKey)) {
                log.info("headerKey {} has value {}", headerKey, value);
                return Optional.ofNullable(value);
            }
        }

        log.warn("No header value found for given key {} returning empty list", headerKey);
        return Optional.ofNullable(null);
    }
}
