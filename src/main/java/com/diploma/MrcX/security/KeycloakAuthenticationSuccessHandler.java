package com.diploma.MrcX.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class KeycloakAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Получаем доступ к токену Keycloak
        /*KeycloakAuthenticationToken keycloakToken = (KeycloakAuthenticationToken) authentication;
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) keycloakToken.getDetails();*/

        // Редирект на нужную страницу
        response.sendRedirect("/secure/dashboard"); // Меняйте URL по необходимости
    }

    }
