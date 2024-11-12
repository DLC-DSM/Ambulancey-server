package org.example.global.exception;

import org.example.global.ResponseObject;
import org.example.global.auth.user.exception.CannotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseObject> customExceptionHandling(CustomException e) {
        final ErrorList errorCode = e.getErrorList();

        return new ResponseEntity<>(
                ResponseObject.builder()
                        .statusCode(errorCode.getStatusCode().value()+"")
                        .message(errorCode.getError())
                        .build(),
                HttpStatus.valueOf(errorCode.getStatusCode().value())
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandling(BindException e) {
        Map<String, String> errorList = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errorList.put(error.getField(),
                    error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotFoundUserException.class)
    public ResponseEntity<String> handleCannotFoundUserException(CannotFoundUserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
