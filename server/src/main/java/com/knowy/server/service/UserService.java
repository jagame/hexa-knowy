package com.knowy.server.service;

import com.knowy.server.controller.dto.UserProfileDTO;
import com.knowy.server.entity.BannedWordsEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.BannedWordsRepository;
import com.knowy.server.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.JpaPrivateUserRepository;
import com.knowy.server.repository.JpaPublicUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class UserService {
	private final JpaPrivateUserRepository jpaPrivateUserRepository;
	private final JpaPublicUserRepository jpaPublicUserRepository;


	public UserService(JpaPrivateUserRepository jpaPrivateUserRepository, JpaPublicUserRepository jpaPublicUserRepository) {
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
	private final BannedWordsRepository bannedWordsRepo;
	private final UserRepository userRepo;

	public UserService(BannedWordsRepository bannedWordsRepo, UserRepository userRepo) {
		this.bannedWordsRepo = bannedWordsRepo;
		this.userRepo = userRepo;

	}

	public boolean validatePrivateUsername(String newPrivateUsername){
		return !(newPrivateUsername.equals(this.privateUsername));
	}

	public Optional<PublicUserEntity> findPublicUserById(Integer id) {
		return jpaPublicUserRepository.findUserById(id);
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email){
		return jpaPrivateUserRepository.findByEmail(email);
	}

	private boolean isCurrentNickname(String newNickname, PublicUserEntity user){
		return (user.getNickname().equals(newNickname));
	}

	private boolean isCurrentEmail(String newEmail, PrivateUserEntity privateUser){
		return (privateUser.getEmail().equals(newEmail));
	}

	private boolean isValidPassword(String currentPassword, PrivateUserEntity privateUser){
		return privateUser.getPassword().equals(currentPassword);
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

	public boolean updateNickname(String newNickname, Integer id){
		Optional<PublicUserEntity> publicUser = findPublicUserById(id);
		if(publicUser.isPresent() && !isCurrentNickname(newNickname, publicUser.get())){
			jpaPublicUserRepository.updateNickname(newNickname, id);
			return true;
		}
		return false;
	}
		//method to check if the new username contains any of the banned words
		public boolean isInappropriateName(String username) {
			String lowerCaseUsername = username.toLowerCase();
			return bannedWordsRepo.findAll().stream().map(bw -> bw.getWord().toLowerCase()).anyMatch(lowerCaseUsername::contains);
		}

	public boolean updateEmail(String email,  String newEmail, String currentPassword){
		Optional<PrivateUserEntity> privateUser = findPrivateUserByEmail(email);
		if(privateUser.isPresent() && !isCurrentEmail(newEmail, privateUser.get()) && isValidPassword(currentPassword, privateUser.get())){
			jpaPrivateUserRepository.updateEmail(email, newEmail);
			return true;
		}
		return false;
	}
}
		//method to check if the username already exists
		public boolean isTakenUsername(String username) {
			return userRepo.existsByUsername(username);
		}
	}



