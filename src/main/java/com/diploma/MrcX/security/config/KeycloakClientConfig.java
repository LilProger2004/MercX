package com.diploma.MrcX.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakClientConfig {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}

