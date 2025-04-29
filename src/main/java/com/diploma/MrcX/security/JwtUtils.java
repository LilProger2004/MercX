package com.diploma.MrcX.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtils {
    public String getUserIdFromJWT(Authentication authentication){
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            return jwt.getClaimAsString("sub");
        } else if (authentication instanceof OAuth2AuthenticationToken oauth2Auth) {
            return oauth2Auth.getPrincipal().getAttribute("sub");
        } else {
            return "";
        }
    }

}
