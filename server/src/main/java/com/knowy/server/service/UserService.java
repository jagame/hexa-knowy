package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.*;
import lombok.Getter;
import lombok.Setter;
import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Service
public class UserService {
	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;
	private final BannedWordsRepository bannedWordsRepo;
	private final LanguageRepository languageRepository;


	public UserService(PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository, BannedWordsRepository bannedWordsRepo, LanguageRepository jpaLanguageRepository) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
		this.bannedWordsRepo = bannedWordsRepo;
		this.languageRepository = jpaLanguageRepository;
	}


//	public boolean validatePrivateUsername(String newPrivateUsername){
//		return !(newPrivateUsername.equals(this.privateUsername));
//	}

	public Optional<PublicUserEntity> findPublicUserById(Integer id) {
		return publicUserRepository.findUserById(id);
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email){
		return privateUserRepository.findByEmail(email);
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

	public boolean updateNickname(String newNickname, Integer id){
		Optional<PublicUserEntity> publicUser = findPublicUserById(id);
		if(publicUser.isPresent() && !isCurrentNickname(newNickname, publicUser.get())){
			publicUserRepository.updateNickname(newNickname, id);
			return true;
		}
		return false;
	}




		//method to update favourite languages
//		public PublicUserEntity updateUserProfile(Integer userId, String newNickname, Set<LanguageEntity> languages) {
//			Optional <PublicUserEntity> optUser = jpaPublicUserRepository.findUserById(userId);
//			if (optUser.isEmpty()) {
//				return null;
//			}
//			PublicUserEntity publicUserEntity = optUser.get();
//			if (newNickname != null && !newNickname.equals(publicUserEntity.getNickname())) {
//				if (isTakenUsername(newNickname)) {
//					return null;
//				}
//				if (isInappropriateName(newNickname)) {
//					return null;
//				}
//				publicUserEntity.setNickname(newNickname);
//			}
//			if (languages != null && !languages.isEmpty()) {
//				Set<String> languagesNames = languages.stream().map(LanguageEntity::getName).collect(Collectors.toSet());
//				Set<LanguageEntity> newLanguages = new HashSet<>(jpaLanguageRepository.findByNameIn(languagesNames));
//				publicUserEntity.setLanguages(newLanguages);
//			}
//			return jpaPublicUserRepository.save(publicUserEntity);
//		}

	public PublicUserEntity updateUserProfile(Integer userId, String newNickname, String[] languages) {
		Optional <PublicUserEntity> optUser = publicUserRepository.findUserById(userId);
		if (optUser.isEmpty()) {
			return null;
		}
		PublicUserEntity publicUserEntity = optUser.get();
		if (newNickname != null && !newNickname.equals(publicUserEntity.getNickname())) {
			if (isTakenUsername(newNickname)) {
				return null;
			}
			if (isNicknameBanned(newNickname)) {
				return null;
			}
			publicUserEntity.setNickname(newNickname);
		}
//		if (languages != null && !languages.isEmpty()) {
			Set<LanguageEntity> newLanguages = languageRepository.findByNameInIgnoreCase(languages);
			publicUserEntity.setLanguages(newLanguages);
//		}
		return publicUserRepository.save(publicUserEntity);
	}

		//method to check if the new username contains any of the banned words
		public boolean isNicknameBanned(String nickname) {
			return bannedWordsRepo.isWordBanned(nickname);
		}

		//method to check if the username already exists
		public boolean isTakenUsername(String nickname) {
			return publicUserRepository.existsByNickname(nickname);
		}


	public boolean updateEmail(String email,  String newEmail, String currentPassword){
		Optional<PrivateUserEntity> privateUser = findPrivateUserByEmail(email);
		if(privateUser.isPresent() && !isCurrentEmail(newEmail, privateUser.get()) && isValidPassword(currentPassword, privateUser.get())){
			privateUserRepository.updateEmail(email, newEmail);
			return true;
		}
		return false;
	}

}
