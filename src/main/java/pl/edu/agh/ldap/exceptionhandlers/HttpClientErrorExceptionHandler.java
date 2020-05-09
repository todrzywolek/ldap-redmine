package pl.edu.agh.ldap.exceptionhandlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpClientErrorExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Log LOGGER = LogFactory.getLog(HttpClientErrorExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    protected ResponseEntity<?> handleHttpClientForbidenException(HttpClientErrorException.Forbidden ex, WebRequest request) {
        String errorMessage = "{\"message\": \"User is not a manager\"}";
        LOGGER.error(ex.getMessage());
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
}
