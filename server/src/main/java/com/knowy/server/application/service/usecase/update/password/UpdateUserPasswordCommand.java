package com.knowy.server.application.service.usecase.update.password;

public record UpdateUserPasswordCommand(String currentPassword, String newPassword, String token) {

}
