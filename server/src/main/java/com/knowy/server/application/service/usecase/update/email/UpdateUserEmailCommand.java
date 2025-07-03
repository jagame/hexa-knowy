package com.knowy.server.application.service.usecase.update.email;

import com.knowy.server.application.domain.Email;

public record UpdateUserEmailCommand(Email currentEmail, String newEmail, String plainPassword) {
}
