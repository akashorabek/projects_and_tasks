package com.forum.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


// This is global exception handler for all restController in the project
@ControllerAdvice(annotations = RestController.class)
public class BindExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected ResponseEntity<?> handleBind(BindException ex) {
        List errors = ex.getFieldErrors()
                .stream()
                .map(fe -> fe.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.unprocessableEntity()
                .body(errors);
    }
}
