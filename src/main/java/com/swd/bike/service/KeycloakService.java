package com.swd.bike.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.dto.auth.response.TokenExchangeResponse;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IKeycloakService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KeycloakService implements IKeycloakService {

    @Value("${keycloak.auth-server-url}")
    String authUrl;
    @Value("${keycloak.realm}")
    String realm;
    @Value("${keycloak-client.username}")
    String username;
    @Value("${keycloak-client.password}")
    String password;
    @Value("${keycloak.credentials.secret}")
    private String secretKey;
    @Value("${keycloak.resource}")
    private String clientId;
    private Keycloak keycloakAdmin;

    @Bean
    private void initKeycloakService() {
        keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(username)
                .password(password)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                )
                .build();
    }

    public AccessTokenResponseCustom getUserJWT(String username, String password) {
        try {
            Keycloak keycloakUser = KeycloakBuilder.builder()
                    .serverUrl(authUrl)
                    .realm(realm)
                    .username(username)
                    .password(password)
                    .grantType(CredentialRepresentation.PASSWORD)
                    .clientId(clientId)
                    .clientSecret(secretKey)
                    .build();

            AccessTokenResponse accessTokenResponse = keycloakUser.tokenManager().getAccessToken();
            return new AccessTokenResponseCustom(accessTokenResponse);
        } catch (Exception ex) {
            log.error("Login failed. {}", ex.getMessage());
        }
        return null;
    }

    public AccessTokenResponseCustom exchangeToken(String subjectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", secretKey);
        map.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        map.add("requested_token_type", "urn:ietf:params:oauth:token-type:refresh_token");
        map.add("requested_subject", subjectId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String url = authUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        try {
            ResponseEntity<String> keycloakResponse = restTemplate.postForEntity(url, request, String.class);
            return new ObjectMapper().readValue(keycloakResponse.getBody(), AccessTokenResponseCustom.class);
        } catch (Exception e) {
            log.error("Keycloak error: {}", e.getMessage());
        }
        return null;
    }

    public boolean invalidateToken(String refreshToken) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", secretKey);
        requestParams.add("refresh_token", refreshToken);
        requestParams.add("revoke_tokens_on_logout ", "true");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParams, headers);

        String url = authUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

        RestTemplate restTemplate = new RestTemplate();
        try {
            log.info("logout response {}", restTemplate.postForEntity(url, request, Object.class));
        } catch (Exception e) {
            log.error("Keycloak error: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public String createUser(String username, String password, String email, String name) {
        return createUser(username, password, email, name, null);
    }


    /**
     * Create Keycloak user with roles
     */
    public String createUser(String username, String password, String email, String name, List<String> roles) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setUsername(username);
        if (email != null) {
            user.setEmail(email);
        }
        if (name != null) {
            user.setFirstName(name);
        }

        RealmResource realmResource = keycloakAdmin.realm(realm);
        UsersResource usersResource = realmResource.users();

        try (Response response = usersResource.create(user)) {
            log.info("Create keycloak user response: {}", response.getStatus());
            if (response.getStatus() == 201 || response.getStatus() == 200) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(false);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(password);

                UserResource userResource = usersResource.get(userId);
                userResource.resetPassword(credentialRepresentation);

                if (roles != null && !roles.isEmpty()) {
                    List<RoleRepresentation> roleRepresentations = roles.stream()
                            .map(this::getUserRoleRepresentation)
                            .collect(Collectors.toList());

                    userResource.roles().clientLevel(keycloakAdmin
                            .realm(realm)
                            .clients()
                            .findByClientId(clientId)
                            .get(0)
                            .getId()).add(roleRepresentations);
                }
                return userId;
            }
        } catch (Exception ex) {
            log.error("Keycloak error: {}", ex.getMessage());
        }

        return null;
    }

    public RoleRepresentation getUserRoleRepresentation(String roleName) {
        ClientRepresentation clientRep = keycloakAdmin
                .realm(realm)
                .clients()
                .findByClientId(clientId)
                .get(0);

        return keycloakAdmin
                .realm(realm)
                .clients()
                .get(clientRep.getId())
                .roles()
                .get(roleName)
                .toRepresentation();
    }

    public void deleteUser(String id) {
        try {
            keycloakAdmin.realm(realm).users().delete(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @SneakyThrows
    public AccessTokenResponseCustom refreshToken(String refreshToken) {
        // Create header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);
        map.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String url = authUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        try {
            ResponseEntity<String> keycloakResponse = restTemplate.postForEntity(url, request, String.class);
            if (keycloakResponse.getStatusCode() == HttpStatus.OK) {
                TokenExchangeResponse tokenExchangeResponse = new ObjectMapper().readValue(keycloakResponse.getBody(), TokenExchangeResponse.class);
                return new AccessTokenResponseCustom(tokenExchangeResponse);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new InternalException(ResponseCode.JSON_PROCESSING_ERROR);
        }

        return null;
    }

    public String getUserIdByEmail(String email) {
        try {
            UsersResource usersResource = keycloakAdmin.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(null, null, null, email, null, null);
            UserRepresentation userId = users
                    .stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            return userId != null ? userId.getId() : null;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public String getUserIdByUserName(String username) {
        try {
            UsersResource usersResource = keycloakAdmin.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(username, null, null, null, null, null);
            UserRepresentation userId = users
                    .stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst().orElse(null);
            return userId != null ? userId.getId() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setUserPassword(String userId, String newPassword) {
        try {
            UserResource userResource = keycloakAdmin.realm(realm).users().get(userId);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(newPassword);
            userResource.resetPassword(credentialRepresentation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String updateUser(String userId, String email, String name) {
        try {
            UsersResource userResource = keycloakAdmin.realm(realm).users();
            UserRepresentation user = new UserRepresentation();
            user.setFirstName(name);
            if (StringUtils.isNotEmpty(email)) {
                user.setEmail(email);
            }
            userResource.get(userId).update(user);
            return userId;
        } catch (Exception ex) {
            log.error("Keycloak error: {}", ex.getMessage());
            return null;
        }
    }

    public List<UserRepresentation> getAllUsers() {
        try {
            UsersResource usersResource = keycloakAdmin.realm(realm).users();
            return usersResource.search(null, null, null, null, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public AccessTokenResponseCustom exchangeGoogleToken(String googleAccessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        map.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        map.add("client_id", clientId);
        map.add("client_secret", secretKey);
        map.add("subject_token", googleAccessToken);
        map.add("subject_issuer", "google");
        map.add("scope", "offline_access");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String url = authUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        try {
            ResponseEntity<String> keycloakResponse = restTemplate.postForEntity(url, request, String.class);
            if (keycloakResponse.getStatusCode() == HttpStatus.OK) {
                TokenExchangeResponse tokenExchangeResponse = new ObjectMapper().readValue(keycloakResponse.getBody(), TokenExchangeResponse.class);
                return new AccessTokenResponseCustom(tokenExchangeResponse);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new InternalException(ResponseCode.JSON_PROCESSING_ERROR);
        }
        return null;
    }

    public boolean addUserRole(String userId, String roleName) {
        try {
            ClientRepresentation clientRep = keycloakAdmin
                    .realm(realm)
                    .clients()
                    .findByClientId(clientId)
                    .get(0);
            RolesResource rolesResource = keycloakAdmin.realm(realm).clients().get(clientRep.getId()).roles();
            if (!getAllRoles().contains(roleName)) {
                RoleRepresentation roleRep = new RoleRepresentation();
                roleRep.setName(roleName);
                rolesResource.create(roleRep);
            }

            List<RoleRepresentation> roleToAdd = new LinkedList<>();
            roleToAdd.add(rolesResource
                    .get(roleName)
                    .toRepresentation()
            );

            UserResource userResource = keycloakAdmin.realm(realm).users().get(userId);
            userResource.roles().clientLevel(clientRep.getId()).add(roleToAdd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllRoles() {
        ClientRepresentation clientRep = keycloakAdmin
                .realm(realm)
                .clients()
                .findByClientId(clientId)
                .get(0);
        List<String> availableRoles = keycloakAdmin
                .realm(realm)
                .clients()
                .get(clientRep.getId())
                .roles()
                .list()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        return availableRoles;
    }

}