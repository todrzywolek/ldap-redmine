package pl.edu.agh.ldap.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtCreator {
    public String createJwt(Authentication authentication) {
        LdapUserDetails principal = (LdapUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Instant now = Instant.now();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuer("LdapRedmine2")
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(JwtAuthenticationConfig.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, JwtAuthenticationConfig.getSecret().getBytes())
                .compact();
    }
}
