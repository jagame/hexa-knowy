package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.repository.PublicUserRepository;
import com.knowy.server.service.exception.NicknameAlreadyTakenException;
import com.knowy.server.service.exception.UnchangedEmailException;
import com.knowy.server.service.exception.UnchangedNicknameException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.util.PasswordChecker;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;
	private final PasswordChecker passwordChecker;

	/**
	 * The constructor
	 *
	 * @param privateUserRepository the privateUserRepository
	 * @param publicUserRepository  the publicUserRepository
	 * @param passwordChecker       the passwordChecker
	 */
	public UserService(PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository, PasswordChecker passwordChecker) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
		this.passwordChecker = passwordChecker;
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
	public void updateNickname(String newNickname, Integer id) throws UserNotFoundException, UnchangedNicknameException, NicknameAlreadyTakenException {
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
		return (user.getNickname().equals(newNickname));
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
	 * @throws UserNotFoundException      if no user exists with the given ID
	 * @throws UnchangedEmailException    if the new email is the same as the current one
	 * @throws WrongPasswordException     if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException
	{
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
}
