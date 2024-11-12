package org.example.domain.review.exception;

import org.example.domain.hospital.exception.NoHospitalException;
import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class ReviewNotFoundException extends CustomException {
    public static final ReviewNotFoundException reviewNotFoundException = new ReviewNotFoundException();
    public ReviewNotFoundException() {
        super(ErrorList.REVIEW_NOT_FOUND);
    }
}

