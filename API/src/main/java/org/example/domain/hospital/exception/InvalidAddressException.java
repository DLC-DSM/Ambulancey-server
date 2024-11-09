package org.example.domain.hospital.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class InvalidAddressException extends CustomException {
    InvalidAddressException hospitalException = new InvalidAddressException();
    public InvalidAddressException() {
        super(ErrorList.Invalid_Address);
    }
}
