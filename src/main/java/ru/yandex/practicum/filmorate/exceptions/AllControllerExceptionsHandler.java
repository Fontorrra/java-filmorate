package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AllControllerExceptionsHandler {

    @ExceptionHandler(AllControllerExceptions.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new java.util.Date());
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getDescription(false));
        return new ResponseEntity<>(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
