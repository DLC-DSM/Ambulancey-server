package org.example.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorList errorList;
}
