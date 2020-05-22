package pl.edu.agh.ldap.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class AuthUtils {
    public static boolean checkIfAdmin() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority a : authorities) {
            if ("ROLE_MANAGERS".equals(a.getAuthority())) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }
}
