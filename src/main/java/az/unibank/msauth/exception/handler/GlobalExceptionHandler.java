package az.unibank.msauth.exception.handler;

import az.unibank.msauth.exception.AccessDeniedException;
import az.unibank.msauth.exception.InvalidUserException;
import az.unibank.msauth.exception.UserNotFoundException;
import az.unibank.msauth.model.view.ErrorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorView> handle(UserNotFoundException exception) {
        log.error("UserNotFoundException: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorView(exception.getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorView> handle(InvalidUserException exception) {
        log.error("InvalidUserException: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorView(exception.getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorView> handle(AuthenticationException exception) {
        log.error("AuthenticationException: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorView("401" ,exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorView> handle(AccessDeniedException exception) {
        log.error("AccessDeniedException: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorView(exception.getCode(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorView> handle(Exception exception) {
        log.error("Unknown Exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorView("500", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
