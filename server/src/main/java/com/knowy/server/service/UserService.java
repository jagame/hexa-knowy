package com.knowy.server.service;

import com.knowy.server.controller.dto.SessionUser;
import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.*;
import com.knowy.server.service.exception.*;
import com.knowy.server.util.exception.UserNotFoundException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.knowy.server.controller.AccessController.SESSION_LOGGED_USER;

@Getter
@Setter
@Service
public class UserService {
	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;
	private final BannedWordsRepository bannedWordsRepo;
	private final LanguageRepository languageRepository;
	private final ProfileImageRepository profileImageRepository;


	public UserService(PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository,
					   BannedWordsRepository bannedWordsRepo, LanguageRepository jpaLanguageRepository,
					   ProfileImageRepository profileImageRepository) {

		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
		this.bannedWordsRepo = bannedWordsRepo;
		this.languageRepository = jpaLanguageRepository;
		this.profileImageRepository = profileImageRepository;
	}


//	public boolean validatePrivateUsername(String newPrivateUsername){
//		return !(newPrivateUsername.equals(this.privateUsername));
//	}

	public PublicUserEntity findPublicUserById(Integer id) throws UserNotFoundException {
		return publicUserRepository.findUserById(id).
			orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email) {
		return privateUserRepository.findByEmail(email);
	}

	private boolean isCurrentNickname(String newNickname, PublicUserEntity user) {
		return (user.getNickname().equals(newNickname));
	}

	private boolean isCurrentEmail(String newEmail, PrivateUserEntity privateUser) {
		return (privateUser.getEmail().equals(newEmail));
	}

	private boolean isValidPassword(String currentPassword, PrivateUserEntity privateUser) {
		return privateUser.getPassword().equals(currentPassword);
	}

//	public boolean updateNickname(String newNickname, Integer id){
//		Optional<PublicUserEntity> publicUser = findPublicUserById(id);
//		if(publicUser.isPresent() && !isCurrentNickname(newNickname, publicUser.get())){
//			publicUserRepository.updateNickname(newNickname, id);
//			return true;
//		}
//		return false;
//	}

	/**
	 * Updates the nickname of a public user.
	 *
	 * <p>This method validates the provided nickname change by performing the following checks:
	 * <ul>
	 *   <li>Verifies that a user with the given {@code id} exists.</li>
	 *   <li>Ensures that the new nickname is different from the user's current nickname.</li>
	 *   <li>Checks that the new nickname is not already taken by another user.</li>
	 * </ul>
	 *
	 * <p>If all validations pass, the user's nickname is updated in the repository.
	 *
	 * @param newNickname the new nickname to assign to the user
	 * @param id          the unique identifier of the user whose nickname is being updated
	 * @throws UserNotFoundException         if no user is found with the given ID
	 * @throws UnchangedNicknameException    if the new nickname is the same as the current one
	 * @throws NicknameAlreadyTakenException if the new nickname is already in use by another user
	 */
	public void updateNickname(String newNickname, @Nonnull Integer id) throws UserNotFoundException, UnchangedNicknameException, NicknameAlreadyTakenException {
		Optional<PublicUserEntity> publicUser = publicUserRepository.findUserById(id);
		if (publicUser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		if (isCurrentNickname(newNickname, publicUser.get())) {
			throw new UnchangedNicknameException("Nickname must be different from the current one.");
		}
		if (publicUserRepository.existsByNickname(newNickname)) {
			throw new NicknameAlreadyTakenException("Nickname is already in use.");
		}

		publicUserRepository.updateNickname(newNickname, id);
	}

	public void updateProfileImage(Integer newProfileImageId, @Nonnull Integer userId) throws UnchangedImageException, ImageNotFoundException, UserNotFoundException {

		PublicUserEntity user = publicUserRepository.findUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		ProfileImageEntity img = profileImageRepository.findById(newProfileImageId)
			.orElseThrow(() -> new ImageNotFoundException("Profile image with this id not found"));

		if (user.getProfileImage().getId().equals(img.getId())) {
			throw new UnchangedImageException("Image must be different from the current one.");
		}



		user.setProfileImage(img);
		publicUserRepository.save(user);
	}

	public void updateLanguages(@Nonnull Integer userId, String[] languages) throws UserNotFoundException {
		PublicUserEntity user = publicUserRepository.findUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		Set<LanguageEntity> newLanguages = languageRepository.findByNameInIgnoreCase(languages);

		user.setLanguages(newLanguages);
		publicUserRepository.save(user);
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

	public PublicUserEntity updateUserProfile(Integer userId, String newNickname, Integer profilePicId, String[] languages) {
		Optional<PublicUserEntity> optUser = publicUserRepository.findUserById(userId);
		if (optUser.isEmpty()) {
			return null;
		}

		PublicUserEntity publicUserEntity = optUser.get();
		if (newNickname != null && !newNickname.equals(publicUserEntity.getNickname())) {
			if (isUsernameTaken(newNickname)) {
				return null;
			}
			if (isNicknameBanned(newNickname)) {
				return null;
			}
			publicUserEntity.setNickname(newNickname);
		}
		if (languages != null && languages.length > 0) {
			Set<LanguageEntity> newLanguages = languageRepository.findByNameInIgnoreCase(languages);
			publicUserEntity.setLanguages(newLanguages);
		}

		if (profilePicId != null && (profilePicId >= 1) && (profilePicId <= 3)) {
			publicUserEntity.setProfileImage(profileImageRepository.findById(profilePicId)
				.orElseThrow(() -> new IllegalArgumentException
					("Profile image with id " + profilePicId + " does not exist.")));
		}
		return publicUserRepository.save(publicUserEntity);
	}


	//method to check if the new username contains any of the banned words
	public boolean isNicknameBanned(String nickname) {
		return bannedWordsRepo.isWordBanned(nickname);
	}

	//method to check if the username already exists
	public boolean isUsernameTaken(String nickname) {
		return publicUserRepository.existsByNickname(nickname);
	}


	public boolean updateEmail(String email, String newEmail, String currentPassword) {
		Optional<PrivateUserEntity> privateUser = findPrivateUserByEmail(email);
		if (privateUser.isPresent() && !isCurrentEmail(newEmail, privateUser.get()) && isValidPassword(currentPassword, privateUser.get())) {
			privateUserRepository.updateEmail(email, newEmail);
			return true;
		}
		return false;
	}

	public String findNicknameById(Integer id) throws UserNotFoundException {
		return publicUserRepository.findNicknameById(id)
			.orElseThrow(() -> new UserNotFoundException("User with id " + id + " does not exist."));
	}

	public String getProfileImageUrlById(Integer userId) throws UserNotFoundException{
		return publicUserRepository.findProfileImageUrlById(userId)
			.orElseThrow(() -> new UserNotFoundException("No se encontró la imagen de perfil para el id actual"));
	}

	public boolean isCurrentNickname(String newNickname, String currentNickname) {
		return newNickname != null && newNickname.equals(currentNickname);
	}


}
