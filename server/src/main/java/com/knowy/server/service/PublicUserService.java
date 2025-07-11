package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.LanguageRepository;
import com.knowy.server.repository.ProfileImageRepository;
import com.knowy.server.repository.PublicUserRepository;
import com.knowy.server.service.exception.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PublicUserService {

	private final PublicUserRepository publicUserRepository;
	private final ProfileImageRepository profileImageRepository;
	private final LanguageRepository languageRepository;

	// TODO - JavaDoc
	public PublicUserService(PublicUserRepository publicUserRepository, LanguageRepository languageRepository, ProfileImageRepository profileImageRepository) {
		this.publicUserRepository = publicUserRepository;
		this.languageRepository = languageRepository;
		this.profileImageRepository = profileImageRepository;
	}

	// TODO - Añadir JavaDoc
	public PublicUserEntity create(String nickname) throws InvalidUserException, ImageNotFoundException {
		if (findPublicUserByNickname(nickname).isPresent()) {
			throw new InvalidUserNicknameException("Nickname already exists");
		}

		PublicUserEntity publicUser = new PublicUserEntity();
		publicUser.setNickname(nickname);
		publicUser.setProfileImage(findProfileImageById(1)
			.orElseThrow(() -> new ImageNotFoundException("Not found profile image")));
		return publicUser;
	}

	// TODO - JavaDoc
	public PublicUserEntity save(PublicUserEntity user) {
		return publicUserRepository.save(user);
	}

	// TODO - JavaDoc
	public void updateNickname(String newNickname, Integer id) throws UserNotFoundException,
		UnchangedNicknameException, NicknameAlreadyTakenException {
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

	// TODO - JavaDoc
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

	// TODO - JavaDoc
	public void updateLanguages(Integer userId, String[] languages) throws UserNotFoundException {
		PublicUserEntity user = publicUserRepository.findUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		Set<LanguageEntity> newLanguages = languageRepository.findByNameInIgnoreCase(languages);

		user.setLanguages(newLanguages);
		publicUserRepository.save(user);
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
	public Optional<PublicUserEntity> findPublicUserByNickname(String nickname) {
		return publicUserRepository.findByNickname(nickname);
	}
}
