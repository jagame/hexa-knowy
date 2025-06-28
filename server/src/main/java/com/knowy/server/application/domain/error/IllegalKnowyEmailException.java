package com.knowy.server.application.domain.error;

public class IllegalKnowyEmailException extends IllegalKnowyDataException {
    public IllegalKnowyEmailException(String message) {
        super(message);
    }
}
