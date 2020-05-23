package pl.edu.agh.ldap.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.util.Base64Utils;


public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtCreator jwtCreator;

    public UserAuthenticationService(AuthenticationManager authenticationManager,
                                     JwtCreator jwtCreator) {
        this.authenticationManager = authenticationManager;
        this.jwtCreator = jwtCreator;
    }

    public LoginResponse login(String authHeader) {
        if (authHeader != null && !authHeader.isEmpty()) {
            String[] credentials = getCredentialsFromAuthenticationHeader(authHeader);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            String token = jwtCreator.createJwt(authenticationResult);
            return new LoginResponse(token, (LdapUserDetailsImpl) authenticationResult.getPrincipal());
        }
        throw new AuthenticationServiceException("No authentication header");
    }

    private String[] getCredentialsFromAuthenticationHeader(String authHeader) {
        String decodedHeader = getDecodedHeader(authHeader);
        return getUsernameAndPassword(decodedHeader);
    }

    private String getDecodedHeader(String authHeader) {
        if (!authHeader.startsWith("Basic ")) {
            throw new AuthenticationServiceException("Invalid authentication header");
        }

        byte[] base64Credentials = authHeader.substring(6).getBytes();
        byte[] decoded;

        try {
            decoded = Base64Utils.decode(base64Credentials);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationServiceException("Failed to decode basic authentication header");
        }
        return new String(decoded);
    }

    private String[] getUsernameAndPassword(String decodedHeader) {
        int delim = decodedHeader.indexOf(":");
        if (delim == -1) {
            throw new AuthenticationServiceException("Invalid basic authentication token");
        } else {
            return new String[]{decodedHeader.substring(0, delim), decodedHeader.substring(delim + 1)};
        }
    }
}
