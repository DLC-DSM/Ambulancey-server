package org.example.global.auth.user.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class AlreadyRegisteredException extends CustomException {
    public static final CustomException EXCEPTION = new AlreadyRegisteredException();
    private AlreadyRegisteredException() {
        super (ErrorList.ALREADY_REGISTERED);
    }

}
