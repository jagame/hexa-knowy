package com.knowy.server.application.service.usecase.update.password;

public record UpdateUserPasswordCommand(String newPassword, String token) {

}
