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

public interface KeycloakAdminService {

    public String getAccessTokenFromKeycloak();


    public List<Map<String, Object>> getAllUsers();

    public String getUserById(String userId);

}

