package br.com.fecaf.exception.handler;

import br.com.fecaf.exception.custom.DuplicateCpfException;
import br.com.fecaf.exception.custom.DuplicateEmailException;
import br.com.fecaf.exception.custom.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildBody(HttpStatus status, String error, Object message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return body;
    }


    @ExceptionHandler(DuplicateCpfException.class)
    public ResponseEntity<Object> handleDuplicateCpf(DuplicateCpfException ex) {
        Map<String, Object> body = buildBody(HttpStatus.CONFLICT, "Bad Request", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmail(DuplicateEmailException ex) {
        Map<String, Object> body = buildBody(HttpStatus.CONFLICT, "Bad Request", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, Object> body = buildBody(HttpStatus.UNAUTHORIZED, "Invalid Credentials", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

}
