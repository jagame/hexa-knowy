package com.knowy.server.application.domain.error;

public class InvalidKnowyEmailFormatException extends InvalidKnowyDataFormatException {
    public InvalidKnowyEmailFormatException(String message) {
        super(message);
    }
}
