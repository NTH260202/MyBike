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
public class TokenExchangeResponse extends BaseRequestData {
    @JsonProperty("access_token")
    protected String accessToken;

    @JsonProperty("refresh_token")
    protected String refreshToken;

    @JsonProperty("expires_in")
    protected long expiresIn;

    @JsonProperty("refresh_expires_in")
    protected long refreshExpiresIn;

    @JsonProperty("session_state")
    protected String sessionState;

    protected String scope;

    @JsonProperty("token_type")
    protected String tokenType;

    @JsonProperty("not-before-policy")
    protected int notBeforePolicy;
}
