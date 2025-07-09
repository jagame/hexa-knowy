package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.*;
import com.knowy.server.service.exception.*;
import com.knowy.server.util.PasswordChecker;
import com.knowy.server.util.exception.WrongPasswordException;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;
	private final BannedWordsRepository bannedWordsRepo;
	private final LanguageRepository languageRepository;
	private final ProfileImageRepository profileImageRepository;
	private final PasswordChecker passwordChecker;


	public UserService(
		PrivateUserRepository privateUserRepository,
		PublicUserRepository publicUserRepository,
		BannedWordsRepository bannedWordsRepo,
		LanguageRepository jpaLanguageRepository,
		ProfileImageRepository profileImageRepository,
		PasswordChecker passwordChecker
	) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
		this.bannedWordsRepo = bannedWordsRepo;
		this.languageRepository = jpaLanguageRepository;
		this.profileImageRepository = profileImageRepository;
		this.passwordChecker = passwordChecker;
	}

	public PublicUserEntity findPublicUserById(Integer id) throws UserNotFoundException {
		return publicUserRepository.findUserById(id).
			orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email) {
		return privateUserRepository.findByEmail(email);
	}

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
		if (!isCurrentNickname(newNickname, publicUser.get())) {
			throw new UnchangedNicknameException("Nickname must be different from the current one.");
		}
		if (publicUserRepository.existsByNickname(newNickname)) {
			throw new NicknameAlreadyTakenException("Nickname is already in use.");
		}

		publicUserRepository.updateNickname(newNickname, id);
	}

	private boolean isCurrentNickname(String newNickname, PublicUserEntity user) {
		return user.getNickname().equals(newNickname);
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

	/**
	 * Updates the email of a user after validating identity and email change.
	 *
	 * <p>This method performs the following checks:
	 * <ul>
	 *   <li>Ensures a user with the given {@code userId} exists.</li>
	 *   <li>Verifies the new email is different from the current one.</li>
	 *   <li>Validates the provided password matches the user's current password.</li>
	 * </ul>
	 * If all checks pass, the user's email is updated in the repository.
	 *
	 * @param email    the new email to set for the user
	 * @param userId   the ID of the user whose email will be updated
	 * @param password the current password to authenticate the email change
	 * @throws UserNotFoundException   if no user exists with the given ID
	 * @throws UnchangedEmailException if the new email is the same as the current one
	 * @throws WrongPasswordException  if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException {
		Optional<PrivateUserEntity> privateUser = privateUserRepository.findById(userId);
		if (privateUser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		if (email.equals(privateUser.get().getEmail())) {
			throw new UnchangedEmailException("Email must be different from the current one.");
		}
		passwordChecker.assertHasPassword(privateUser.get(), password);

		privateUserRepository.updateEmail(privateUser.get().getEmail(), email);
	}

	public String findNicknameById(Integer id) throws UserNotFoundException {
		return publicUserRepository.findNicknameById(id)
			.orElseThrow(() -> new UserNotFoundException("User with id " + id + " does not exist."));
	}

	public String getProfileImageUrlById(Integer userId) throws UserNotFoundException {
		return publicUserRepository.findProfileImageUrlById(userId)
			.orElseThrow(() -> new UserNotFoundException("No se encontró la imagen de perfil para el id actual"));
	}

	public boolean isCurrentNickname(String newNickname, String currentNickname) {
		return newNickname != null && newNickname.equals(currentNickname);
	}


}
