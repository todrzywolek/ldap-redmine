package pl.edu.agh.ldap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ldap.security.UserAuthenticationService;

@RestController
public class AuthenticationController {

    private final UserAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authenticationService.login(authHeader);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
}
