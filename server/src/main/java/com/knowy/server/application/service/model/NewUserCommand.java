package com.knowy.server.application.service.model;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.domain.ProfileImage;

import java.util.Set;

public record NewUserCommand(String nickname, ProfileImage profileImage, Set<Category> categories) {
}
