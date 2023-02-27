package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice()
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdAlreadyExistsException.class)
    public ResponseEntity<Object> handleIdAlreadyExistsException(IdAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(HttpStatus.CONFLICT, request),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return handleExceptionInternal(ex, createErrorResponse(HttpStatus.BAD_REQUEST, request),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ErrorResponse createErrorResponse(HttpStatus status, WebRequest request) {
        return new ErrorResponse.ErrorResponseBuilder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = createErrorResponse(status, request);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
