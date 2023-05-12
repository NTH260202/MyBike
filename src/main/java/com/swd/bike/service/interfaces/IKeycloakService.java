package com.swd.bike.service.interfaces;

import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import lombok.SneakyThrows;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {

    AccessTokenResponseCustom getUserJWT(String username, String password);

    AccessTokenResponseCustom exchangeToken(String subjectId);

    boolean invalidateToken(String refreshToken);

    String createUser(String username, String password, String email, String name);

    /**
     * Create Keycloak user with roles
     */
    String createUser(String username, String password, String email, String name, List<String> roles);

    RoleRepresentation getUserRoleRepresentation(String roleName);

    void deleteUser(String id);

    @SneakyThrows
    AccessTokenResponseCustom refreshToken(String refreshToken);

    String getUserIdByEmail(String email);

    String getUserIdByUserName(String username);

    boolean setUserPassword(String userId, String newPassword);

    String updateUser(String userId, String email, String name);

    List<UserRepresentation> getAllUsers();

    AccessTokenResponseCustom exchangeGoogleToken(String googleAccessToken);

    boolean addUserRole(String userId, String roleName);

    List<String> getAllRoles();
}
