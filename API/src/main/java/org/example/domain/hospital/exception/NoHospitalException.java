package org.example.domain.hospital.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class NoHospitalException extends CustomException {
    public static final NoHospitalException hospitalException = new NoHospitalException();
    public NoHospitalException() {
        super(ErrorList.HOSPITAL_NO_EXIST);
    }
}
