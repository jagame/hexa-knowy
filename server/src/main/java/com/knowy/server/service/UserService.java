package com.knowy.server.service;

import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.entity.BannedWordsEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.BannedWordsRepository;
import com.knowy.server.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class UserService {
	private String username = "usuario123";
	private String privateUsername = "usuario@Privado123";
	private String email = "usuario123@gmail.com";
	private String password = "12345aA@";

	private final BannedWordsRepository bannedWordsRepo;
	private final UserRepository userRepo;

	public UserService(BannedWordsRepository bannedWordsRepo, UserRepository userRepo) {
		this.bannedWordsRepo = bannedWordsRepo;
		this.userRepo = userRepo;

	}

	public boolean validatePrivateUsername(String newPrivateUsername){
		return !(newPrivateUsername.equals(this.privateUsername));
	}

	public boolean validateCurrentPassword(String pass){
		return this.password.equals(pass);
	}

	public boolean validateEqualEmail(String email){
		return !(this.email.equals(email));
	}



		//method to update username and profilePic
		public PublicUserEntity updateProfile(String currentUsername, String newUsername, String newProfilePic) {
			Optional<PublicUserEntity> optUserEntity = userRepo.findByUsername(currentUsername);
			if (optUserEntity.isEmpty()) {
				return null;
			}
			PublicUserEntity publicUserEntity = optUserEntity.get();
			publicUserEntity.setNickname(newUsername);
			if (newProfilePic != null && !newProfilePic.isEmpty()) {
				publicUserEntity.setProfilePicture(newProfilePic);
			}
			return userRepo.save(publicUserEntity);
			}


		//method to update favourite languages
		public PublicUserEntity updateFavLanguages(String username, List<String> languages) {
			Optional <PublicUserEntity> optUser = userRepo.findByUsername(username);
			if (optUser.isEmpty()) {
				return null;
			}
			PublicUserEntity publicUserEntity = optUser.get();
			publicUserEntity.setFavouriteLanguages(languages);
			return userRepo.save(publicUserEntity);
		}

		//method to check if the new username contains any of the banned words
		public boolean isInappropriateName(String username) {
			String lowerCaseUsername = username.toLowerCase();
			return bannedWordsRepo.findAll().stream().map(bw -> bw.getWord().toLowerCase()).anyMatch(lowerCaseUsername::contains);
		}

		//method to check if the username already exists
		public boolean isTakenUsername(String username) {
			return userRepo.existsByUsername(username);
		}
	}



