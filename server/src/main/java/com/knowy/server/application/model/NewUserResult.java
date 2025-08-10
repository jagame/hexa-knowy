package com.knowy.server.application.model;

import com.knowy.server.domain.Category;
import com.knowy.server.domain.ProfileImage;

import java.util.Set;

public record NewUserResult(String nickname, ProfileImage profileImage, Set<Category> categories) {
}
