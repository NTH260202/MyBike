package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.TokenVerifier;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Using to get current log in user
 * Author: lnson
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContextService {

    private final IAccountService accountService;
    @Value("${keycloak.resource}")
    private String keycloakClientId;

    private AccessToken getLoggedInAccessToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    !(authentication instanceof KeycloakAuthenticationToken)) {
                return null;
            }
            KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) token.getPrincipal();

            AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            return accessToken;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Optional<String> getLoggedInUsername() {
        AccessToken loggedInToken = getLoggedInAccessToken();
        return loggedInToken != null
                ? Optional.of(loggedInToken.getPreferredUsername())
                : Optional.empty();
    }

    /**
     * Get subject ID (Keycloak UserID)
     *
     * @return
     */
    public Optional<String> getLoginUserId() {
        AccessToken loggedInToken = getLoggedInAccessToken();

        Optional<String> subjectId = loggedInToken != null
                ? Optional.of(loggedInToken.getSubject())
                : Optional.empty();

        return !subjectId.isEmpty()
                ? accountService.getIdBySubjectIdOpt(subjectId.get())
                : Optional.empty();
    }

    public Set<String> getLoggedInUserRoles() {
        try {
            AccessToken loggedInToken = getLoggedInAccessToken();
            return loggedInToken != null
                    ? loggedInToken.getResourceAccess(keycloakClientId).getRoles()
                    : new HashSet<>();
        } catch (Exception ex) {
            log.error("Get Keycloak user roles failed, {}", ex.getMessage());
        }
        return new HashSet<>();
    }

    public Account getLoggedInUser() {
        try {
            AccessToken loggedInToken = getLoggedInAccessToken();
            return getLoggedInUser(loggedInToken);
        } catch (Exception ex) {
            log.error("Get current login user failed, {}", ex.getMessage());
            throw new InternalException(ResponseCode.UNAUTHORIZED_REQUEST);
        }
    }

    public Account getLoggedInUser(String jwt) {
        try {
            AccessToken token = TokenVerifier.create(jwt, AccessToken.class).getToken();
            return getLoggedInUser(token);
        } catch (VerificationException e) {
            return null;
        }
    }

    public Account getLoggedInUser(AccessToken token) {
        try {
            if (token == null) {
                log.info("Unauthorized request");
                return null;
            }
            String subjectId = token.getSubject();

            Account account = accountService.getBySubjectId(subjectId);
            if (account == null) {
                throw new InternalException(ResponseCode.UNAUTHORIZED_REQUEST);
            }

            return account;
        } catch (Exception ex) {
            log.error("Get current login user failed, {}", ex.getMessage());
            throw new InternalException(ResponseCode.UNAUTHORIZED_REQUEST);
        }
    }
}
