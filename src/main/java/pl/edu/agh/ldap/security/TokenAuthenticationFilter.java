package pl.edu.agh.ldap.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final static Log LOGGER = LogFactory.getLog(TokenAuthenticationFilter.class);

    private final JwtValidator jwtValidator;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public TokenAuthenticationFilter(JwtValidator jwtValidator, AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtValidator = jwtValidator;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = jwtValidator.getTokenFromRequest(request);
        if (!token.isEmpty()) {
            authenticateWithToken(token, request, response);
        }
        chain.doFilter(request, response);
    }

    private void authenticateWithToken(String token, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            RedminePrincipal principal = jwtValidator.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(
                    new TokenBasedAuthentication(principal, token, principal.grantedAuthorities()));
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, e);
        }
    }
}