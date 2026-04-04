package com.example.Notes_Management_Api.exceptionHandler;

import com.example.Notes_Management_Api.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CommonResponse> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(404)
                .body(CommonResponse.builder()
                        .timestamp(Instant.now().toString())
                        .error("Not Found")
                        .message(e.getMessage())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(
                CommonResponse.builder()
                        .timestamp(Instant.now().toString())
                        .error("Validation Error")
                        .message(errorMsg)
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(500).body(
                CommonResponse.builder()
                        .timestamp(Instant.now().toString())
                        .error("Internal Server Error")
                        .message(e.getMessage())
                        .build()
        );
    }
}