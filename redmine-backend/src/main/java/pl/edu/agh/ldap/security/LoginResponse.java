package pl.edu.agh.ldap.security;

import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

public class LoginResponse {
    private String token;
    private UserDetails userDetails;


    LoginResponse(String token, LdapUserDetailsImpl principal) {
        this.token = token;
        this.userDetails = new UserDetails(principal);
    }

    public String getToken() {
        return token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
