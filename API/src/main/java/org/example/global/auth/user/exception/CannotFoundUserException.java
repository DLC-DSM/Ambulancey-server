package org.example.global.auth.user.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class CannotFoundUserException extends CustomException {
    public CannotFoundUserException cannotFoundUserException = new CannotFoundUserException();
    public CannotFoundUserException() {
        super(ErrorList.USER_NO_EXIST);
    }
}
