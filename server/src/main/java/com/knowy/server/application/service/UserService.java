package com.knowy.server.application.service;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.User;
import com.knowy.server.application.ports.CategoryRepository;
import com.knowy.server.application.ports.ProfileImageRepository;
import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.application.service.exception.*;
import com.knowy.server.application.service.model.NewUserCommand;
import com.knowy.server.application.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ProfileImageRepository profileImageRepository;
	private final CategoryRepository categoryRepository;

	/**
	 * The constructor
	 *
	 * @param userRepository         the publicUserRepository
	 * @param categoryRepository     the languageRepository
	 * @param profileImageRepository the profileImageRepository
	 */
	public UserService(
		UserRepository userRepository,
		CategoryRepository categoryRepository,
		ProfileImageRepository profileImageRepository
	) {
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
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
	 * @throws KnowyInvalidUserException   if the nickname is already in use
	 * @throws KnowyImageNotFoundException if the default profile image (ID 1) does not exist
	 */
	public NewUserCommand create(String nickname) throws KnowyInvalidUserException, KnowyImageNotFoundException {
		assertNotBlankNickname(nickname);

		if (findPublicUserByNickname(nickname).isPresent()) {
			throw new KnowyInvalidUserNicknameException("Nickname already exists");
		}

		return new NewUserCommand(
			nickname,
			profileImageRepository.findById(1)
				.orElseThrow(() -> new KnowyImageNotFoundException("Not found profile image")),
			new HashSet<>()
		);
	}

	/**
	 * Persists the given {@code PublicUserEntity} in the database.
	 *
	 * <p>If the entity already exists, it will be updated; otherwise, a new record will be created.</p>
	 *
	 * @param user the {@code PublicUserEntity} to persist
	 * @return the saved entity with any database-generated fields (like ID) populated
	 */
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Updates the nickname of a public user.
	 *
	 * <p>Ensures the new nickname is different from the current one and not already taken.
	 * Performs the update in the repository if validations pass.</p>
	 *
	 * @param newNickname the new nickname to assign
	 * @param id          the ID of the user whose nickname should be updated
	 * @throws KnowyUserNotFoundException         if the user with the given ID does not exist
	 * @throws KnowyUnchangedNicknameException    if the new nickname is the same as the current one
	 * @throws KnowyNicknameAlreadyTakenException if the new nickname is already in use by another user
	 */
	public void updateNickname(String newNickname, Integer id) throws KnowyUserNotFoundException,
		KnowyUnchangedNicknameException, KnowyNicknameAlreadyTakenException, KnowyInvalidUserNicknameException {
		assertNotBlankNickname(newNickname);

		User user = userRepository.findById(id)
			.orElseThrow(() -> new KnowyUserNotFoundException("User not found"));
		if (user.nickname().equals(newNickname)) {
			throw new KnowyUnchangedNicknameException("Nickname must be different from the current one.");
		}
		if (userRepository.existsByNickname(newNickname)) {
			throw new KnowyNicknameAlreadyTakenException("Nickname is already in use.");
		}

		userRepository.updateNickname(newNickname, id);
	}

	private void assertNotBlankNickname(String nickname) throws KnowyInvalidUserNicknameException {
		if (StringUtils.isBlank(nickname)) {
			throw new KnowyInvalidUserNicknameException("Blank nicknames are not allowed");
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
	 * @throws KnowyUserNotFoundException   if no user exists with the given ID
	 * @throws KnowyImageNotFoundException  if no profile image exists with the given ID
	 * @throws KnowyUnchangedImageException if the new image is the same as the current one
	 */
	public void updateProfileImage(Integer newProfileImageId, Integer userId) throws KnowyUnchangedImageException,
		KnowyImageNotFoundException, KnowyUserNotFoundException {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new KnowyUserNotFoundException("User not found with id: " + userId));

		ProfileImage img = profileImageRepository.findById(newProfileImageId)
			.orElseThrow(() -> new KnowyImageNotFoundException("Profile image with this id not found"));

		if (user.profileImage().id().equals(img.id())) {
			throw new KnowyUnchangedImageException("Image must be different from the current one.");
		}

		User newUser = new User(user.id(), user.nickname(), img, user.categories());
		userRepository.save(newUser);
	}

	/**
	 * Updates the set of categories associated with a public user.
	 *
	 * <p>Fetches language entities by their names (case-insensitive) and updates the user's categories.
	 * Throws an exception if the user is not found.</p>
	 *
	 * @param userId    the ID of the user whose categories should be updated
	 * @param languages an array of language names to assign to the user
	 * @throws KnowyUserNotFoundException if no user exists with the given ID
	 */
	public void updateLanguages(Integer userId, String[] languages) throws KnowyUserNotFoundException {
		Objects.requireNonNull(languages, "A not null categories array is required, if no categories are selected use an empty array instead of null");

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new KnowyUserNotFoundException("User not found with id: " + userId));
		Set<Category> newCategories = categoryRepository.findByNameInIgnoreCase(languages);

		User newUser = new User(user.id(), user.nickname(), user.profileImage(), newCategories);
		userRepository.save(newUser);
	}

	/**
	 * Finds a public user by their nickname.
	 *
	 * @param nickname the nickname of the public user
	 * @return an {@code Optional} containing the {@code PublicUserEntity} if found, or empty if not found
	 */
	public Optional<User> findPublicUserByNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}

	public Optional<User> findPublicUserById(int id) {
		return userRepository.findById(id);
	}
}
