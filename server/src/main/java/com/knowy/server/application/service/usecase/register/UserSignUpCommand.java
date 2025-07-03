package com.knowy.server.application.service.usecase.register;

import com.knowy.server.application.domain.ProfileImage;

public record UserSignUpCommand(String nickname, ProfileImage profileImage, String email, String password) {
}
