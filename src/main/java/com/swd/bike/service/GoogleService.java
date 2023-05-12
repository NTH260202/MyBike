
package com.swd.bike.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd.bike.dto.auth.response.GoogleAccessTokenResponse;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IGoogleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class GoogleService implements IGoogleService {

    @Value("${google.auth-server-url}")
    private String authServerUrl;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;
    @Value("${google.valid-domain}")
    private String validDomain;

    public GoogleAccessTokenResponse getAccessToken(String code, String redirectUri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("redirect_uri", redirectUri);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String url = authServerUrl;
        try {
            ResponseEntity<String> googleResponse = restTemplate.postForEntity(url, request, String.class);
            if (googleResponse.getStatusCode() == HttpStatus.OK) {
                GoogleAccessTokenResponse accessTokenResponse = new ObjectMapper().readValue(googleResponse.getBody(), GoogleAccessTokenResponse.class);
                return accessTokenResponse;
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new InternalException(ResponseCode.JSON_PROCESSING_ERROR);
        }
        return null;
    }

    public boolean isValidEmail(String domain) {
        return Objects.equals(domain, validDomain);
    }
}