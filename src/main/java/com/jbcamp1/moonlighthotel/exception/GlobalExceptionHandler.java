package com.jbcamp1.moonlighthotel.exception;

import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelAuthentication;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        ArrayList<FieldError> fieldErrors = new ArrayList<>(ex.getFieldErrors());
        String key = "";

        for (FieldError fieldError : fieldErrors) {
            if (!fieldError.getField().equalsIgnoreCase(key)) {
                key = fieldError.getField();
                messages = ex.getFieldErrors(key).stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet());
                errors.put(key, messages);
            }
        }
        ErrorModelValidation error = ErrorModelValidation.builder()
                .message("Some of the fields are not correctly filled")
                .errors(errors)
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    private ResponseEntity<ErrorModelValidation> handleDuplicateRecord(DuplicateRecordException ex) {

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(ex.getMessage());
        errors.put(ex.getDuplicateField(), messages);

        ErrorModelValidation error = ErrorModelValidation.builder()
                .message("Duplicate record found")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    private ResponseEntity<ErrorModelDuplicate> handleNotFound(RecordNotFoundException ex) {

        ErrorModelDuplicate duplicateError = ErrorModelDuplicate.builder()
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(duplicateError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationFailException.class)
    private ResponseEntity<ErrorModelAuthentication> handleNotFound(AuthenticationFailException ex) {

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(ex.getMessage());
        errors.put(ex.getField(), messages);

        ErrorModelAuthentication error = ErrorModelAuthentication.builder()
                .message("Invalid Authentication")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {

        String message = String.format("Required request parameter '%s' is missing.", ex.getParameterName());

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(message);
        errors.put(ex.getParameterName(), messages);

        ErrorModelValidation error = ErrorModelValidation.builder()
                .message("Invalid Request")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    private ResponseEntity<ErrorModelValidation> handleInvalidRequest(InvalidRequestException ex) {

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(ex.getMessage());
        errors.put(ex.getRequestField(), messages);

        ErrorModelValidation error = ErrorModelValidation.builder()
                .message("Invalid Request")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}