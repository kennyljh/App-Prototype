package com.careerconnect.app.Global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uses @RestControllerAdvice to define global exception-handing logic for all
 * controllers in Spring Boot application.
 * This class defines handling logic specifically for MethodArgumentNotValidException
 * type exceptions.
 * Can be used by declaring the @Valid annotation for checking, and will return
 * a Map of "Field to Error Message" responses for variables using annotations of
 * the jakarta.validation.constraints.* package, e.g. @NotNull(message = "message").
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex){

        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
