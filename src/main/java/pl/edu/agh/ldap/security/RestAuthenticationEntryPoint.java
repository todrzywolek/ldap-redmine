package pl.edu.agh.ldap.security;


import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        if (response.getStatus() == HttpServletResponse.SC_METHOD_NOT_ALLOWED) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                    "Method not allowed");
        } else {

            String errorMessage = "{\"message\":\"error\"}";
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(errorMessage);
        }
    }
}
