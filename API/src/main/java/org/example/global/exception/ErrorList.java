package org.example.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorList {
    USER_NO_EXIST("NO_EXIST_USER",HttpStatusCode.valueOf(404));

    private final String error;
    private final HttpStatusCode statusCode;

}
