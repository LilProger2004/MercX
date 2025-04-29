package com.diploma.MrcX.service;

import com.diploma.MrcX.security.config.KeycloakClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KeycloakClientConfig config;

//    @Value("${security.oauth2.client.provider.mercx-realm.token-uri}")
//    private String adminUrl;

    public String getAccessTokenFromKeycloak() {
        String tokenUrl = "http://keycloak:8085/realms/mercx-realm/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", config.getClientId());
        form.add("client_secret", config.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> body = response.getBody();
            return (String) body.get("access_token");
        } catch (HttpClientErrorException e) {
            System.out.println("❌ Ошибка: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;
        }
    }


    public List<Map<String, Object>> getAllUsers() {
        String token = getAccessTokenFromKeycloak();
        String url = config.getServerUrl() + "/admin/realms/" + config.getRealm() + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public String getUserById(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessTokenFromKeycloak());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange("http://keycloak:8085/admin/realms/mercx-realm/users/" + userId, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("❌ Ошибка при получении пользователя: " + e.getStatusCode());
            throw e;
        }
    }

}

