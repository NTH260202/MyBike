package com.swd.bike.dto.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd.bike.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class GoogleIdToken {
    private String iss;
    private String azp;
    private String sub;
    private String aud;
    private String hd;
    private String email;
    private String name;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    @JsonProperty("at_hash")
    private String atHash;
    private String picture;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    private String locale;
    private String nonce;
    private Long iat;
    private Long exp;

    public static GoogleIdToken get(String googleIdToken) {
        if (googleIdToken == null) {
            return null;
        }
        String[] parts = googleIdToken.split("\\.");
        String payload = CommonUtils.decode(parts[1]);
        try {
            return new ObjectMapper().readValue(payload, GoogleIdToken.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
