package com.knowy.server.services;

import com.knowy.server.entities.UserEntity;
import com.knowy.server.repositories.BannedWordsRepository;
import com.knowy.server.repositories.ExistingUsernameRepository;
import com.knowy.server.repositories.UserRepositoryTest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	private final UserRepositoryTest userRepo;
	private final BannedWordsRepository bannedWordsRepo;
	private final ExistingUsernameRepository existingUsernameRepo;

	public UserService(UserRepositoryTest userRepo, BannedWordsRepository bannedWordsRepo, ExistingUsernameRepository existingUsernameRepo) {
		this.userRepo = userRepo;
		this.bannedWordsRepo = bannedWordsRepo;
		this.existingUsernameRepo = existingUsernameRepo;
	}

	public UserEntity getUserTest() {
		return userRepo.getTestUser();
	}

	//method to update username and profilePic
	public void updateProfile(String newUsername, String newProfilePicture) {
		UserEntity user = userRepo.getTestUser();
		user.setUsername(newUsername);
		if (newProfilePicture != null && !newProfilePicture.isEmpty()) {
			user.setProfilePicture(newProfilePicture);
		}
		userRepo.save(user);
	}

	//method to update favourite languages
	public void updateFavLanguages(List<String> languages) {
		UserEntity user = userRepo.getTestUser();
		user.setFavouriteLanguages(languages);
		if (languages != null) {
			user.setFavouriteLanguages(languages);
		} else {
			user.setFavouriteLanguages(new ArrayList<>());
		}
		userRepo.save(user);
	}

	//method to check if the new username contains any of the banned words
	public boolean inappropriateName(String userName) {
		if (userName == null) {
			return false;
		}
		String lowerCaseUserName = userName.toLowerCase();
		return bannedWordsRepo.getBannedWords().stream().map(String::toLowerCase).anyMatch(lowerCaseUserName::contains);
	}


	//method to check if the username already exists
	public boolean takenUserName(String userName) {
		if (userName == null) {
			return false;
		}
		return existingUsernameRepo.getExistingUsernames().stream().anyMatch(userName::equalsIgnoreCase);
	}
}
