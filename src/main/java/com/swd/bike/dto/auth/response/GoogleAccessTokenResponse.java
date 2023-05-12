package com.swd.bike.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAccessTokenResponse extends BaseRequestData {
    @JsonProperty("access_token")
    protected String accessToken;
    @JsonProperty("id_token")
    protected String idToken;
    @JsonProperty("expires_in")
    protected long expiresIn;
    @JsonProperty("token_type")
    protected String tokenType;

    protected String scope;
    @JsonProperty("refresh_token")
    protected String refreshToken;
    protected String payload;
}
