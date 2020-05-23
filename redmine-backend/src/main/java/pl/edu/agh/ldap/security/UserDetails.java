package pl.edu.agh.ldap.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails {

    private final String username;
    private final List<String> authorities;

    UserDetails(LdapUserDetailsImpl principal) {
        this.username = principal.getUsername();
        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        this.authorities = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
