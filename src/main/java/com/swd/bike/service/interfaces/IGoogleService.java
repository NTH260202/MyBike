package com.swd.bike.service.interfaces;

import com.swd.bike.dto.auth.response.GoogleAccessTokenResponse;

public interface IGoogleService {

    GoogleAccessTokenResponse getAccessToken(String code, String redirectUri);

    boolean isValidEmail(String domain);
}
