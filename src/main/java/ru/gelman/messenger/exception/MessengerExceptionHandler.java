package ru.gelman.messenger.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MessengerExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> alreadyExistsExceptionHandler(AlreadyExistsException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
