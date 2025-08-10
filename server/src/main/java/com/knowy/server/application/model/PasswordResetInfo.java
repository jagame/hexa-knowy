package com.knowy.server.application.model;

public record PasswordResetInfo(int userId, String email) {
}
