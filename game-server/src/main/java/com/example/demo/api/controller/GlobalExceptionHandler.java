package com.example.demo.api.controller;

import com.example.demo.api.errors.ApiError;
import com.example.demo.api.errors.ApiErrorPrinter;
import com.example.demo.api.errors.ApiFieldError;
import com.example.demo.api.errors.CommandParseException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiFieldError> errors =
                ex.getBindingResult().getFieldErrors().stream().map(this::toError).toList();

        ApiError body = new ApiError(400, "Validation failed", errors);
        log.debug(ApiErrorPrinter.print(body));
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(CommandParseException.class)
    public ResponseEntity<ApiError> handleCommandParse(CommandParseException ex) {
        ApiError body =
                new ApiError(400, ex.getMessage(), List.of(new ApiFieldError("command", ex.getInput())));
        log.debug(ApiErrorPrinter.print(body));
        return ResponseEntity.badRequest().body(body);
    }

    private ApiFieldError toError(FieldError error) {
        return new ApiFieldError(error.getField(), error.getDefaultMessage());
    }
}
