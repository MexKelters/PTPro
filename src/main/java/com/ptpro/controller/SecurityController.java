package com.ptpro.controller;


import com.ptpro.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    //Nog uitwerken na gesprek docent
    //Moet ik met het keycloak uuid mijn model vullen ?
    @GetMapping("/hello")
    public String hallo(JwtAuthenticationToken jwtAuthenticationToken) {
        String email = jwtAuthenticationToken.getToken().getClaimAsString("email");
        String voorNaam = jwtAuthenticationToken.getToken().getClaimAsString("given_name");
        String achterNaam = jwtAuthenticationToken.getToken().getClaimAsString("family_name");
        Collection<GrantedAuthority> authorities = jwtAuthenticationToken.getAuthorities();
        userService.getOrCreateUser(email,voorNaam,achterNaam,authorities);
        return email + " " + voorNaam + " " + achterNaam + " " + authorities;
    }

    @GetMapping("/helloUser")
    public String halloUser(JwtAuthenticationToken jwtAuthenticationToken) {
        String email = jwtAuthenticationToken.getToken().getClaimAsString("email");
        String voorNaam = jwtAuthenticationToken.getToken().getClaimAsString("given_name");
        String achterNaam = jwtAuthenticationToken.getToken().getClaimAsString("family_name");
        Collection<GrantedAuthority> authorities = jwtAuthenticationToken.getAuthorities();

        return email + " " + voorNaam + " " + achterNaam + " " + authorities;
    }


}
