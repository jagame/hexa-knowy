package com.knowy.server.application.service.usecase.update.email;

public record UpdateUserEmailCommand(String currentEmail, String newEmail, String password) {
}
