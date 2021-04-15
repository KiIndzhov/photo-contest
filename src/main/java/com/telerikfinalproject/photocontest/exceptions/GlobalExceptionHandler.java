package com.telerikfinalproject.photocontest.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice("com.telerikfinalproject.photocontest.controllers.RESTControllers")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(EntityNotFoundException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DuplicateEntityException.class)
    protected ResponseEntity<Object> handleDuplicate(DuplicateEntityException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = UnauthorisedException.class)
    protected ResponseEntity<Object> handleAuthorization(UnauthorisedException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = WrongPasswordException.class)
    protected ResponseEntity<Object> handleWrongPassword(WrongPasswordException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = DateTimeException.class)
    protected ResponseEntity<Object> handleDate(DateTimeException e, WebRequest request) {
        String bodyOfResponse = e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        });

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

}