
package com.swd.bike.service;


import com.swd.bike.dto.auth.dtos.GoogleIdToken;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.auth.response.GoogleAccessTokenResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.AccountStatus;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.Role;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import com.swd.bike.service.interfaces.IAuthService;
import com.swd.bike.service.interfaces.IGoogleService;
import com.swd.bike.service.interfaces.IKeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {
    private final IKeycloakService keycloakService;
    private final IGoogleService googleService;
    private final IAccountService accountService;


    public AccessTokenResponseCustom loginByUsernameAndPassword(String username, String password) {
        AccessTokenResponseCustom accessTokenResponseCustom = keycloakService.getUserJWT(
                username,
                password
        );

        AccessToken token;

        try {
            token = TokenVerifier.create(accessTokenResponseCustom.getToken(), AccessToken.class).getToken();
        } catch (Exception e) {
            log.error("error when login with username and password: {}", e.getLocalizedMessage());
            throw new InternalException(ResponseCode.INVALID_USERNAME_OR_PASSWORD);
        }

        String email = token.getEmail();
        String userName = token.getPreferredUsername();
        String id = token.getSubject();

        log.debug("token parsed: mail = {}, phone = {}, id = {}", email, userName, id);
        return accessTokenResponseCustom;
    }

    public AccessTokenResponseCustom loginByGoogle(String code, String redirectUri) {
        GoogleAccessTokenResponse googleAccessToken = googleService.getAccessToken(code, redirectUri);
        GoogleIdToken googleInfo = GoogleIdToken.get(googleAccessToken.getIdToken());

//        if (!googleService.isValidEmail(googleInfo.getHd())) {
//            throw new InternalException(ResponseCode.AUTHENTICATION_FAILED_OUTSIDE_EMAIL);
//        }

        if (googleAccessToken == null) {
            throw new InternalException(ResponseCode.GOOGLE_AUTH_ERROR);
        }

        AccessTokenResponseCustom accessTokenResponseCustom = keycloakService.exchangeGoogleToken(googleAccessToken.getAccessToken());

        try {
            AccessToken token = TokenVerifier.create(accessTokenResponseCustom.getToken(), AccessToken.class).getToken();
            Account account = accountService.getBySubjectId(token.getSubject());

            //Create if there is no account
            if (account == null) {
                account = new Account()
                        .setRole(Role.USER)
                        .setSubjectId(token.getSubject())
                        .setName(googleInfo.getGivenName())
                        .setAvatar(googleInfo.getPicture())
                        .setEmail(googleInfo.getEmail())
                        .setIsUpdated(false)
                        .setStatus(AccountStatus.ACTIVE);
                keycloakService.addUserRole(account.getSubjectId(), Role.USER.name());
                accountService.save(account);
            } else {
                if (!Objects.equals(account.getStatus(), AccountStatus.ACTIVE)) {
                    throw new InternalException(ResponseCode.ACCOUNT_INACTIVE);
                }
            }
        } catch (Exception e) {
            throw new InternalException(ResponseCode.AUTHENTICATION_FAILED);
        }
        return refreshToken(accessTokenResponseCustom.getRefreshToken());
    }

    public void logout(String refreshToken) {
        if (!keycloakService.invalidateToken(refreshToken)) {
            throw new InternalException(ResponseCode.REFRESH_TOKEN_INVALID);
        }
    }

    public AccessTokenResponseCustom refreshToken(String refreshToken) {
        return keycloakService.refreshToken(refreshToken);
    }
}