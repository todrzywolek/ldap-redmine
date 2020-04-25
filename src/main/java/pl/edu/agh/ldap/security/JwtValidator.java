package pl.edu.agh.ldap.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class JwtValidator {
    private static final String TOKEN_PREFIX = "Bearer ";

    public RedminePrincipal authenticate(String token) {
        return getPrincipal(token);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return "";
    }

    private RedminePrincipal getPrincipal(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtAuthenticationConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        RedminePrincipal principal = new RedminePrincipal();
        principal.setName(claims.getSubject());
        List roles = claims.get("roles", List.class);
        principal.setRoles(roles);
        return principal;
    }
}
