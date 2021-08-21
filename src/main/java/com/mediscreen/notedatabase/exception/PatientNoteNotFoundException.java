package com.mediscreen.notedatabase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatientNoteNotFoundException extends RuntimeException {

    public PatientNoteNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public PatientNoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
