package org.example.global.auth.user.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CannotFoundUserException extends CustomException {
    public static final CannotFoundUserException cannotFoundUserException = new CannotFoundUserException();
    public CannotFoundUserException() {
        super(ErrorList.USER_NO_EXIST);
    }
}
