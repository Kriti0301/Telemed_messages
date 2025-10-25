package com.praxes.telemed_messages.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
// change this ↓
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice  // was @ControllerAdvice
public class GlobalExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "ValidationError");
        List<Map<String, String>> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("field", fe.getField());
                    m.put("message", fe.getDefaultMessage());
                    return m;
                })
                .toList();
        body.put("details", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "BadRequest");
        body.put("message", "Malformed request body or invalid value.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
