package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class MainServiceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionNotFound(ResourceNotFoundException e) {
        String reason = "The required object was not found.";
        log.error(e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), reason, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ResourceConflictException.class, SQLConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse exceptionConflict(RuntimeException e) {
        String reason = "Integrity constraint has been violated.";
        log.error(e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), reason, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({InvalidResourceException.class, ConditionsNotMetException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionBadRequest(RuntimeException e) {
        String reason = "Incorrectly made request.";
        log.error(e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), reason, e.getMessage(), LocalDateTime.now());
    }
}
