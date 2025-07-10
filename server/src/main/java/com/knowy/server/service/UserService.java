package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.LanguageRepository;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.repository.ProfileImageRepository;
import com.knowy.server.repository.PublicUserRepository;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.service.model.PasswordResetInfo;
import com.knowy.server.util.PasswordChecker;
import com.knowy.server.util.exception.JwtKnowyException;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;
	private final LanguageRepository languageRepository;
	private final ProfileImageRepository profileImageRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordChecker passwordChecker;

	public UserService(
		PrivateUserRepository privateUserRepository,
		PublicUserRepository publicUserRepository,
		LanguageRepository languageRepository,
		ProfileImageRepository profileImageRepository, PasswordEncoder passwordEncoder
	) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
		this.languageRepository = languageRepository;
		this.profileImageRepository = profileImageRepository;
		this.passwordChecker = new PasswordChecker(passwordEncoder);
		this.passwordEncoder = passwordEncoder;
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
		PublicUserEntity publicUser = publicUserRepository.findUserById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
		if (isCurrentNickname(newNickname, publicUser)) {
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

	// TODO - Añadir JavaDoc
	public PublicUserEntity createUser(String nickname, String email, String password) throws InvalidUserException, ImageNotFoundException {
		if (findPublicUserByNickname(nickname).isPresent()) {
			throw new InvalidUserNicknameException("Nickname alredy exists");
		}

		if (findPrivateUserByEmail(email).isPresent()) {
			throw new InvalidUserEmailException("Email alredy exists");
		}

		if (!passwordChecker.isRightPasswordFormat(password)) {
			throw new InvalidUserPasswordFormatException("Invalid password format");
		}

		PrivateUserEntity privateUser = new PrivateUserEntity();
		privateUser.setEmail(email);
		privateUser.setPassword(passwordEncoder.encode(password));

		PublicUserEntity publicUser = new PublicUserEntity();
		publicUser.setNickname(nickname);
		publicUser.setProfileImage(findProfileImageById(1)
			.orElseThrow(() -> new ImageNotFoundException("Not found profile image")));

		privateUser.setPublicUserEntity(publicUser);
		publicUser.setPrivateUserEntity(privateUser);

		publicUser = save(publicUser);

		return publicUser;
	}

	// TODO - JavaDoc
	public PublicUserEntity save(PublicUserEntity user) {
		return publicUserRepository.save(user);
	}

	// TODO - JavaDoc
	public PrivateUserEntity save(PrivateUserEntity user) {
		return privateUserRepository.save(user);
	}

	// TODO - JavaDoc
	public void updateEmailByEmail(String email, String newEmail) {
		privateUserRepository.updateEmail(email, newEmail);
	}

	// TODO - JavaDoc
	public Optional<PrivateUserEntity> findPrivateUserById(Integer id) {
		return privateUserRepository.findById(id);
	}

	// TODO - JavaDoc
	public Optional<PublicUserEntity> findPublicUserById(Integer id) {
		return publicUserRepository.findUserById(id);
	}

	// TODO - JavaDoc
	public Optional<ProfileImageEntity> findProfileImageById(Integer id) {
		return profileImageRepository.findById(id);
	}

	// TODO - JavaDoc
	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email) {
		return privateUserRepository.findByEmail(email);
	}

	// TODO - JavaDoc
	public PrivateUserEntity getPrivateUserByEmail(String email) throws UserNotFoundException {
		return findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	// TODO - JavaDoc
	public Optional<PublicUserEntity> findPublicUserByNickname(String nickname) {
		return publicUserRepository.findByNickname(nickname);
	}

	// TODO - JavaDoc
	public MailMessage createRecoveryPasswordEmail(String email, String token, String recoveryBaseUrl) throws UserNotFoundException {
		findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

		String subject = "Tu enlace para recuperar la cuenta de Knowy está aquí";
		String body = tokenBody(token, recoveryBaseUrl);

		return new MailMessage(email, subject, body);
	}

	private String tokenBody(String token, String appUrl) {
		String url = "%s?token=%s".formatted(appUrl, token);
		return """
			¡Hola, talentoso %%$@€#&%%$%%! 👋
			
			Sabemos que tu camino como $%%$@%%&€#@&$ es importante, por eso te ayudamos a recuperar tu acceso. \s
			Haz clic en el siguiente enlace para restablecer tu contraseña:
			
			%s
			
			Este enlace es válido solo por un tiempo limitado.
			Si no fuiste tú quien pidió este cambio, no te preocupes, simplemente ignora este correo.
			
			¡Sigue aprendiendo y conquistando tus metas con Knowy! 💪
			
			---
			© 2025 KNOWY, Inc
			""".formatted(url);
	}

	// TODO - JavaDoc
	public PasswordResetInfo createPasswordResetInfo(String email) throws JwtKnowyException {
		PrivateUserEntity user = findPrivateUserByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new PasswordResetInfo(user.getId(), user.getEmail());
	}

}
