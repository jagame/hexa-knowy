package com.knowy.server.services;

import com.knowy.server.entities.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserSrvc {

	//user for testing
	@Getter
	private User userTest = new User("userTest", "https://placehold.co/50x50", new ArrayList<>());

	//method to update username and profilepic
	public void updateProfile(String newUsername, String newProfilePicture) {
		userTest.setUsername(newUsername);
		if (newProfilePicture != null && !newProfilePicture.isEmpty()) {
			userTest.setProfilePicture(newProfilePicture);
		}
	}

	//method to update favourite lenguages
	public void updateFavLanguages(List<String> languages) {
		if (languages != null) {
			userTest.setFavouriteLanguages(languages);
		} else {
			userTest.setFavouriteLanguages(new ArrayList<>());
		}
	}

	//List of banned words created for testing
	private final List<String> bannedWords = Arrays.asList(
		"tonto",
		"gay",
		"estupido"
	);

	//method to check if the new username contains any of the banned words
	public boolean inappropriateName(String userName) {
		if (userName == null) {
			return false;
		}
		String lowerCaseUserName = userName.toLowerCase();
		return bannedWords.stream().map(String::toLowerCase).anyMatch(lowerCaseUserName::contains);
	}

	//List of already existing usernames
	private List<String> existingUsernames = Arrays.asList(
		"admin",
		"user",
		"root",
		"knowy"
	);

	//method to check if the username already exists
	public boolean takenUserName(String userName) {
		if (userName == null) {
			return false;
		}
		return existingUsernames.contains(userName.toLowerCase());
	}
}
