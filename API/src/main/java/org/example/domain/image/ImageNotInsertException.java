package org.example.domain.image;

import org.example.global.exception.CustomException;
import org.example.global.exception.ErrorList;

public class ImageNotInsertException extends CustomException {
    private static final CustomException exception = new ImageNotInsertException();
    public ImageNotInsertException() {
        super(ErrorList.NOT_INSERT_IMAGE);
    }
}
