package com.diploma.M.rcX.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    public HashMap index() {
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new HashMap() {{
            put("hello", user.getAttribute("name"));
            put("your email is", user.getAttribute("email"));
        }};
        /*у меня есть проблема я пишу веб приложение Spring boot и использую сервер авторизации keycloak и приложение и сервер запускаются в контейнере docker , контейнер приложения - "app:
    build: .
    ports:
      - "8081:8081"
    networks:
      - app
    environment:
      SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI: http://localhost:8080/realms/mercx-realm
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/mercX
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - db", контейнер keycloak - "keycloak: # <- Этот блок должен быть внутри services:
    image: quay.io/keycloak/keycloak:26.1.3
    container_name: keycloak
    restart: always
    depends_on:
      keycloak-db:
        condition: service_healthy
    ports:
      - "8085:8085"
    environment:
      KC_HOSTNAME: keycloak
      KC_KEYCLOAK_FRONTEND_URL: http://localhost:8085
      KC_DB: postgres
      KC_DB_URL: jdbc:postgresql://keycloak-db:5432/keycloak
      KC_DB_USERNAME: keycloak
      KC_DB_PASSWORD: keycloak
      KC_HTTP_ENABLED: "true"
      KC_BOOTSTRAP_ADMIN_USERNAME: admin
      KC_BOOTSTRAP_ADMIN_PASSWORD: admin
    command: [ "start-dev" , "--http-port", "8085" ]
    networks:
      - app" , application.yml - "spring:
  application:
    name: mercX
  security:
    oauth2:
      client:
        registration:
          mercx-realm:
            client-id: merx-client-id
            client-secret: hWkzOv4YVvsExocPapplq2L04hpfxyCZ
            provider: mercx-realm
            scope: openid
            authorization-grant-type: authorization_code
        provider:
          mercx-realm:
            authorization-uri: http://localhost:8085/realms/mercx-realm/protocol/openid-connect/auth
            issuer-uri: http://keycloak:8085/realms/mercx-realm

server:
  port: 8081" , и у меня возникает проблема после авторизации пользователя в приложении (авторизация происходит с помощью keycloak по адресу - "http://localhost:8085/realms/mercx-realm/protocol/openid-connect/auth") меня редиректит по адресу "http://keycloak:8085/realms/mercx-realm/login-actions/authenticate" но браузер не знает такого хоста поэтому возникает ошибка, при этом когда KC_HOSTNAME: keycloak я не могу обратиться из браузера напрямую , но поэтому адресу "http://localhost:8085/realms/mercx-realm/.well-known/openid-configuration" я могу обратиться и получить коректный ответ , а когда KC_HOSTNAME: localhost я могу обратиться к нему из браузера , но при подстановки параметров в issuer-uri:http://localhost:8085/realms/mercx-realm , выдает ошибку "The Issuer "http://localhost:8085/realms/mercx-realm" provided in the configuration metadata did not match the requested issuer "http://keycloak:8085/realms/mercx-realm" , что делать ?*/
    }

    @GetMapping(path = "/unauthenticated")
    public String unauthenticatedRequests() {
        return "this is unauthenticated endpoint";
    }

    @GetMapping(path = "/cats")
    public List<String> getCats() {
        return List.of("cat1", "cat2");
    }
}
