package org.example.domain.hospital.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class NoHospitalException extends CustomException {
    NoHospitalException hospitalException = new NoHospitalException();
    public NoHospitalException() {
        super(ErrorList.USER_NO_EXIST);
    }
}
