package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.BannedWordsRepository;
import com.knowy.server.repository.JpaLanguageRepository;
import lombok.Getter;
import lombok.Setter;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.JpaPrivateUserRepository;
import com.knowy.server.repository.JpaPublicUserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class UserService {
	private final JpaPrivateUserRepository jpaPrivateUserRepository;
	private final JpaPublicUserRepository jpaPublicUserRepository;
	private final BannedWordsRepository bannedWordsRepo;
	private final JpaLanguageRepository jpaLanguageRepository;


	public UserService(JpaPrivateUserRepository jpaPrivateUserRepository, JpaPublicUserRepository jpaPublicUserRepository, BannedWordsRepository bannedWordsRepo, JpaLanguageRepository jpaLanguageRepository) {
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
		this.bannedWordsRepo = bannedWordsRepo;
		this.jpaLanguageRepository = jpaLanguageRepository;
	}


//	public boolean validatePrivateUsername(String newPrivateUsername){
//		return !(newPrivateUsername.equals(this.privateUsername));
//	}

	public Optional<PublicUserEntity> findPublicUserById(Integer id) {
		return jpaPublicUserRepository.findUserById(id);
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email){
		return jpaPrivateUserRepository.findByEmail(email);
	}

		//method to update username and profilePic
		public PublicUserEntity updateProfile(String currentUsername, String newUsername) {
			Optional<PublicUserEntity> optUserEntity = jpaPublicUserRepository.findByUsername(currentUsername);
			if (optUserEntity.isEmpty()) {
				return null;
			}
			PublicUserEntity publicUserEntity = optUserEntity.get();
			publicUserEntity.setNickname(newUsername);
			return jpaPublicUserRepository.save(publicUserEntity);
			}


		//method to update favourite languages
		public PublicUserEntity updateUserLanguages(String username, Set<LanguageEntity> languages) {
			Optional <PublicUserEntity> optUser = jpaPublicUserRepository.findByUsername(username);
			if (optUser.isEmpty()) {
				return null;
			}
			PublicUserEntity publicUserEntity = optUser.get();
			publicUserEntity.setLanguages(languages);
			return jpaPublicUserRepository.save(publicUserEntity);
		}

		//method to check if the new username contains any of the banned words
		public boolean isInappropriateName(String username) {
			String lowerCaseUsername = username.toLowerCase();
			return bannedWordsRepo.findAll().stream().map(bw -> bw.getWord().toLowerCase()).anyMatch(lowerCaseUsername::contains);
		}

		//method to check if the username already exists
		public boolean isTakenUsername(String username) {
			return jpaPublicUserRepository.existsByUsername(username);
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
