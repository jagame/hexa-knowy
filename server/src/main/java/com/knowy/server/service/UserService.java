package com.knowy.server.service;

import com.knowy.server.entity.BannedWordsEntity;
import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.*;
import lombok.Getter;
import lombok.Setter;
import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.repository.query.Param;
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
	private final JpaBannedWordsRepository jpaBannedWordsRepo;
	private final JpaLanguageRepository jpaLanguageRepository;


	public UserService(JpaPrivateUserRepository jpaPrivateUserRepository, JpaPublicUserRepository jpaPublicUserRepository, JpaBannedWordsRepository jpaBannedWordsRepo, JpaLanguageRepository jpaLanguageRepository) {
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
		this.jpaBannedWordsRepo = jpaBannedWordsRepo;
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
			jpaPublicUserRepository.updateNickname(newNickname, id);
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

	public PublicUserEntity updateUserProfile(Integer userId, String newNickname, Set<String> languages) {
		Optional <PublicUserEntity> optUser = jpaPublicUserRepository.findUserById(userId);
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
			Set<LanguageEntity> newLanguages = jpaLanguageRepository.findByNameInIgnoreCase(languages);
			publicUserEntity.setLanguages(newLanguages);
//		}
		return jpaPublicUserRepository.save(publicUserEntity);
	}

		//method to check if the new username contains any of the banned words
		public boolean isNicknameBanned(String nickname) {
			return jpaBannedWordsRepo.isWordBanned(nickname);
		}

		//method to check if the username already exists
		public boolean isTakenUsername(String nickname) {
			return jpaPublicUserRepository.existsByNickname(nickname);
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
