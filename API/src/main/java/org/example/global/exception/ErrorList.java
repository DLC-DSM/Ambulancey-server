package org.example.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorList {
    USER_NO_EXIST("NO_EXIST_USER",HttpStatusCode.valueOf(400)),
    HOSPITAL_NO_EXIST("NO_HOSPITAL_EXIST",HttpStatusCode.valueOf(400)),
    INVALID_ADDRESS("INVALID_ADDRESS",HttpStatusCode.valueOf(400)),
    REVIEW_NOT_FOUND("REVIEW_NOT_FOUND",HttpStatusCode.valueOf(400)),
    ALREADY_REGISTERED("ALREADY_REGISTERED",HttpStatusCode.valueOf(400)),
    NOT_ALLOWED_USERNAME_EXCEPTION("NOT_ALLOWED_USERNAME_EXCEPTION", HttpStatusCode.valueOf(400));

    private final String error;
    private final HttpStatusCode statusCode;

}
