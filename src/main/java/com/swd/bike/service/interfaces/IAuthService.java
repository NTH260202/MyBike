package com.swd.bike.service.interfaces;

import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;

public interface IAuthService {

    AccessTokenResponseCustom loginByUsernameAndPassword(String username, String password);


    AccessTokenResponseCustom loginByGoogle(String code, String redirectUri);

    void logout(String refreshToken);

    AccessTokenResponseCustom refreshToken(String refreshToken);
}
