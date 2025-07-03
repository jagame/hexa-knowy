package com.knowy.server.application.service.usecase.recovery;

import com.knowy.server.application.domain.Email;

import java.net.URI;

public record SendPasswordRecoveryMessageCommand(Email userEmail, URI passwordRecoveryUri) {
}
