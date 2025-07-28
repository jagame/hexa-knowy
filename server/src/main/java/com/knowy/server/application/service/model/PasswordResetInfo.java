package com.knowy.server.application.service.model;

public record PasswordResetInfo(int userId, String email) {
}
