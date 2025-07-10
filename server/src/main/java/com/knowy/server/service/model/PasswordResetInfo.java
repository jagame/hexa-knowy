package com.knowy.server.service.model;

public record PasswordResetInfo(int userId, String email) {
}
