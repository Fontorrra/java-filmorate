package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.yandex.practicum.filmorate.exceptions.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(AllExceptions.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new java.util.Date());
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getDescription(false));
        return new ResponseEntity<>(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new java.util.Date());
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorAttributes.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        errorAttributes.put("path", request.getDescription(false));
        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(Exception ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new java.util.Date());
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getDescription(false));
        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundExceptions.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(Exception ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new java.util.Date());
        errorAttributes.put("status", HttpStatus.NOT_FOUND.value());
        errorAttributes.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getDescription(false));
        return new ResponseEntity<>(errorAttributes, HttpStatus.NOT_FOUND);
    }
}