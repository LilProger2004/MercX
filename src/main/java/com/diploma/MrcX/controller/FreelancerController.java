package com.diploma.MrcX.controller;

import com.diploma.MrcX.model.pojo.User;
import com.diploma.MrcX.security.JwtUtils;
import com.diploma.MrcX.service.KeycloakAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/freelancer")
public class FreelancerController {

    private final JwtUtils jwtUtils = new JwtUtils();

    public FreelancerController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    private final KeycloakAdminService keycloakAdminService;

    /*@GetMapping("me")
    public Map<String, Object> userInfo(Authentication authentication) {
        Map<String, Object> attributes = new HashMap<>();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            attributes.put("type", "jwt");
            attributes.put("username", jwt.getClaimAsString("preferred_username"));
            attributes.put("email", jwt.getClaimAsString("email"));
            attributes.put("roles", jwt.getClaimAsMap("realm_access").get("roles"));
        } else if (authentication instanceof OAuth2AuthenticationToken oauth2Auth) {
            attributes.put("type", "oauth2");
            attributes.put("username", oauth2Auth.getPrincipal().getAttribute("preferred_username"));
            attributes.put("email", oauth2Auth.getPrincipal().getAttribute("email"));
            attributes.put("roles", oauth2Auth.getPrincipal().getAttribute("realm_access") != null
                    ? ((Map<?, ?>) oauth2Auth.getPrincipal().getAttribute("realm_access")).get("roles")
                    : "no roles found");
        } else {
            attributes.put("error", "Неизвестный тип аутентификации");
        }

        return attributes;
    }*/

    @GetMapping("test")
    public String getUsers() {
        return "freelancers";
    }

    @GetMapping("me")
    public String getById(Authentication authentication, Model model) throws JsonProcessingException {
        User user = new ObjectMapper().readValue(keycloakAdminService.getUserById(jwtUtils.getUserIdFromJWT(authentication)),User.class);
        model.addAttribute("id",user.getId());
        return "freelancer_profile";
    }
}
