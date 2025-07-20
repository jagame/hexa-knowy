package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.ports.LanguageRepository;
import com.knowy.server.repository.ports.ProfileImageRepository;
import com.knowy.server.repository.ports.PublicUserRepository;
import com.knowy.server.service.exception.*;
import com.knowy.server.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicUserService {

	private final PublicUserRepository publicUserRepository;
	private final ProfileImageRepository profileImageRepository;
	private final LanguageRepository languageRepository;

	/**
	 * The constructor
	 *
	 * @param publicUserRepository   the publicUserRepository
	 * @param languageRepository     the languageRepository
	 * @param profileImageRepository the profileImageRepository
	 */
	public PublicUserService(
		PublicUserRepository publicUserRepository,
		LanguageRepository languageRepository,
		ProfileImageRepository profileImageRepository
	) {
		this.publicUserRepository = publicUserRepository;
		this.languageRepository = languageRepository;
		this.profileImageRepository = profileImageRepository;
	}

	/**
	 * Creates a new {@code PublicUserEntity} with the specified nickname.
	 *
	 * <p>Validates that the nickname is unique and assigns a default profile image (ID 1).
	 * Throws exceptions if the nickname is already taken or the default image cannot be found.</p>
	 *
	 * @param nickname the desired nickname for the new user
	 * @return a new {@code PublicUserEntity} instance with the default profile image set
	 * @throws InvalidUserException   if the nickname is already in use
	 * @throws ImageNotFoundException if the default profile image (ID 1) does not exist
	 */
	public PublicUserEntity create(String nickname) throws InvalidUserException, ImageNotFoundException {
		assertNotBlankNickname(nickname);

		if (findPublicUserByNickname(nickname).isPresent()) {
			throw new InvalidUserNicknameException("Nickname already exists");
		}

		PublicUserEntity publicUser = new PublicUserEntity();
		publicUser.setNickname(nickname);
		publicUser.setProfileImage(findProfileImageById(1)
			.orElseThrow(() -> new ImageNotFoundException("Not found profile image")));
		return publicUser;
	}

	/**
	 * Persists the given {@code PublicUserEntity} in the database.
	 *
	 * <p>If the entity already exists, it will be updated; otherwise, a new record will be created.</p>
	 *
	 * @param user the {@code PublicUserEntity} to persist
	 * @return the saved entity with any database-generated fields (like ID) populated
	 */
	public PublicUserEntity save(PublicUserEntity user) {
		return publicUserRepository.save(user);
	}

	/**
	 * Updates the nickname of a public user.
	 *
	 * <p>Ensures the new nickname is different from the current one and not already taken.
	 * Performs the update in the repository if validations pass.</p>
	 *
	 * @param newNickname the new nickname to assign
	 * @param id          the ID of the user whose nickname should be updated
	 * @throws UserNotFoundException         if the user with the given ID does not exist
	 * @throws UnchangedNicknameException    if the new nickname is the same as the current one
	 * @throws NicknameAlreadyTakenException if the new nickname is already in use by another user
	 */
	public void updateNickname(String newNickname, Integer id) throws UserNotFoundException,
		UnchangedNicknameException, NicknameAlreadyTakenException, InvalidUserNicknameException {
		assertNotBlankNickname(newNickname);

		PublicUserEntity publicUser = publicUserRepository.findUserById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
		if (publicUser.getNickname().equals(newNickname)) {
			throw new UnchangedNicknameException("Nickname must be different from the current one.");
		}
		if (publicUserRepository.existsByNickname(newNickname)) {
			throw new NicknameAlreadyTakenException("Nickname is already in use.");
		}

		publicUserRepository.updateNickname(newNickname, id);
	}

	private void assertNotBlankNickname(String nickname) throws InvalidUserNicknameException {
		if (StringUtils.isBlank(nickname)) {
			throw new InvalidUserNicknameException("Blank nicknames are not allowed");
		}
	}

	/**
	 * Updates the profile image of a public user.
	 *
	 * <p>Validates that the new profile image exists and is different from the current one.
	 * Throws exceptions if validations fail or if the user or image is not found.</p>
	 *
	 * @param newProfileImageId the ID of the new profile image to assign
	 * @param userId            the ID of the user whose profile image should be updated
	 * @throws UserNotFoundException   if no user exists with the given ID
	 * @throws ImageNotFoundException  if no profile image exists with the given ID
	 * @throws UnchangedImageException if the new image is the same as the current one
	 */
	public void updateProfileImage(Integer newProfileImageId, Integer userId) throws UnchangedImageException,
		ImageNotFoundException, UserNotFoundException {
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

	/**
	 * Updates the set of languages associated with a public user.
	 *
	 * <p>Fetches language entities by their names (case-insensitive) and updates the user's languages.
	 * Throws an exception if the user is not found.</p>
	 *
	 * @param userId    the ID of the user whose languages should be updated
	 * @param languages an array of language names to assign to the user
	 * @throws UserNotFoundException if no user exists with the given ID
	 */
	public void updateLanguages(Integer userId, String[] languages) throws UserNotFoundException {
		Objects.requireNonNull(languages, "A not null languages array is required, if no languages are selected use an empty array instead of null");

		PublicUserEntity user = publicUserRepository.findUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		Set<LanguageEntity> newLanguages = languageRepository.findByNameInIgnoreCase(languages);

		user.setLanguages(newLanguages);
		publicUserRepository.save(user);
	}

	/**
	 * Finds a profile image by its unique identifier.
	 *
	 * @param id the unique ID of the profile image
	 * @return an {@code Optional} containing the {@code ProfileImageEntity} if found, or empty if not found
	 */
	public Optional<ProfileImageEntity> findProfileImageById(Integer id) {
		return profileImageRepository.findById(id);
	}

	/**
	 * Finds a public user by their nickname.
	 *
	 * @param nickname the nickname of the public user
	 * @return an {@code Optional} containing the {@code PublicUserEntity} if found, or empty if not found
	 */
	public Optional<PublicUserEntity> findPublicUserByNickname(String nickname) {
		return publicUserRepository.findByNickname(nickname);
	}
}
