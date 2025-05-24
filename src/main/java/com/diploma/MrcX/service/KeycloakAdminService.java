package com.diploma.MrcX.service;

public interface KeycloakAdminService {

    public String getAccessTokenFromKeycloak();


    public String getAllUsers();

    public String getUserById(String userId);

}

