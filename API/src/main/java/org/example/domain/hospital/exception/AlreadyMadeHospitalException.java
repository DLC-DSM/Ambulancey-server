package org.example.domain.hospital.exception;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class AlreadyMadeHospitalException extends CustomException {
    public static final AlreadyMadeHospitalException hospitalException = new AlreadyMadeHospitalException();
    public AlreadyMadeHospitalException() {
        super(ErrorList.ALREADY_HOSPITAL);
    }
}
