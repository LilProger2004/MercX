package com.diploma.MrcX.service;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakGroupService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak getKeycloakInstance() {
        String adminPassword = "admin";
        String adminUsername = "admin";
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    // Добавление пользователя в группу
    public void addUserToGroup(String userId, String groupId) {
        try (Keycloak keycloak = getKeycloakInstance()) {
            keycloak.realm(realm).users().get(userId).joinGroup(groupId);
        }
    }

    // Получение ID группы по имени
    public String getGroupIdByName(String groupName) {
        try (Keycloak keycloak = getKeycloakInstance()) {
            // Используем поиск по имени, если групп много
            List<GroupRepresentation> groups = keycloak.realm(realm)
                    .groups()
                    .groups(groupName, 0, 1); // Поиск по имени, лимит 1 результат
            return groups.stream()
                    .findFirst()
                    .map(GroupRepresentation::getId)
                    .orElseThrow(() -> new RuntimeException("Group not found: " + groupName));
        }
    }

    /*public String getGroupByUserId(String userId){
        try (Keycloak keycloak = getKeycloakInstance()) {
            // Получаем объект пользователя
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            // Получаем все группы пользователя



            // Проверяем наличие группы с указанным именем
            return userResource.g;
    }*/

    // Получение ID пользователя по username/email
    public String getUserIdByUsername(String username) {
        try (Keycloak keycloak = getKeycloakInstance()) {
            List<UserRepresentation> users = keycloak.realm(realm).users().search(username);
            return users.stream()
                    .findFirst()
                    .map(UserRepresentation::getId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
        }
    }

    public boolean isUserInGroup(String userId, String groupName) {
        try (Keycloak keycloak = getKeycloakInstance()) {
            // Получаем объект пользователя
            UserResource userResource = keycloak.realm(realm).users().get(userId);

            List<GroupRepresentation> userGroups;
            // Получаем все группы пользователя
            try {
                userGroups = userResource.groups();
            }catch (RuntimeException exception){
                return false;
            }


            // Проверяем наличие группы с указанным именем
            return userGroups.stream()
                    .anyMatch(group -> group.getName().equals(groupName));
        }
    }
}
