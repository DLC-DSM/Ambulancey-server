package org.example.global.auth.user.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class NotAllowedUsernameException extends CustomException {
    public NotAllowedUsernameException cannotFoundUserException = new NotAllowedUsernameException();
    public NotAllowedUsernameException() {
        super(ErrorList.NOT_ALLOWED_USERNAME_EXCEPTION);
    }
}
