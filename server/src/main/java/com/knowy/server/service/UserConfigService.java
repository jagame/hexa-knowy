package com.knowy.server.service;

import com.knowy.server.controller.model.UserConfigDTO;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class UserConfigService {
	// Method to obtain the current user (simulated)
	public UserConfigDTO getCurrentUser() {
		UserConfigDTO user = new UserConfigDTO(1L, "Usuario123", "usuario@ejemplo.com", "password");
		user.setFavoriteLanguages(Arrays.asList("JavaScript", "Python", "TypeScript"));
		return user;
	}

	// Method for updating the user profile
	public UserConfigDTO updateProfile(UserConfigDTO user) {
		// Updating the user in the database
		return user;
	}

	// Method for updating the password
	public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
		// Verifying and updating the password
		return true;
	}

	// Method to delete the account
	public boolean deleteAccount(Long userId, String password) {
		// Verifying the password and deleting the account.
		return true;
	}
	//Method to logout
	public boolean logout(Long userId) {
		//Logout logic
		return true;
	}
}
