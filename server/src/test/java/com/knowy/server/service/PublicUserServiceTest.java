package com.knowy.server.service;

import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.application.service.UserService;
import com.knowy.server.application.service.exception.*;
import com.knowy.server.infrastructure.adapters.repository.entity.CategoryEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.ProfileImageEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import com.knowy.server.application.ports.CategoryRepository;
import com.knowy.server.application.ports.ProfileImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;


class PublicUserServiceTest {

	// NICKNAME
	@Test
	void givenExistingNicknameExpectAlreadyExistException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findByNickname("ExistNickname")).thenReturn(Optional.of(new PublicUserEntity()));

		InvalidUserNicknameException ex = Assertions.assertThrows(InvalidUserNicknameException.class, () ->
			service.create("ExistNickname")
		);
		Assertions.assertEquals("Nickname already exists", ex.getMessage());
	}

	@Test
	void givenNoDefaultImageExpectNotFoundException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findByNickname("ValidNickname")).thenReturn(Optional.empty());

		Mockito.when(imageRepo.findById(1)).thenReturn(Optional.empty());

		ImageNotFoundException ex = Assertions.assertThrows(ImageNotFoundException.class, () ->
			service.create("ValidNickname")
		);
		Assertions.assertEquals("Not found profile image", ex.getMessage());
	}

	@Test
	void givenSameNicknameExpectUnchangedException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		user.setNickname("SameNickname");
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));

		Exception ex = Assertions.assertThrows(UnchangedNicknameException.class, () ->
			service.updateNickname("SameNickname", 1)
		);
		Assertions.assertEquals("Nickname must be different from the current one.", ex.getMessage());
	}

	@Test
	void givenExistingNicknameExpectAlreadyTakenException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		user.setNickname("judit");
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(publicUserRepo.existsByNickname("nuevo")).thenReturn(true);

		Exception ex = Assertions.assertThrows(
			NicknameAlreadyTakenException.class,
			() -> service.updateNickname("nuevo", 1)
		);
		Assertions.assertEquals("Nickname is already in use.", ex.getMessage());
	}

	@Test
	void givenBlankNicknameExpectNotAllowException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);

		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		InvalidUserNicknameException ex = Assertions.assertThrows(
			InvalidUserNicknameException.class, () ->
				service.create(" ")
		);
		Assertions.assertEquals("Blank nicknames are not allowed", ex.getMessage());
	}

	@Test
	void givenValidNicknameExpectFindUser() throws Exception {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findByNickname("judit")).thenReturn(Optional.empty());

		ProfileImageEntity fakeImage = new ProfileImageEntity();
		fakeImage.setId(1);
		Mockito.when(imageRepo.findById(1)).thenReturn(Optional.of(fakeImage));

		PublicUserEntity result = service.create("judit");
		Assertions.assertEquals("judit", result.getNickname());
		Assertions.assertEquals(1, result.getProfileImage().getId());

		Mockito.verify(publicUserRepo, Mockito.times(1)).findByNickname("judit");
		Mockito.verify(imageRepo).findById(1);
	}


	@Test
	void givenUserWhenSaveExpectRepoUserReturned() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity userToSave = new PublicUserEntity();
		userToSave.setNickname("SaveMe");

		Mockito.when(publicUserRepo.save(userToSave)).thenReturn(userToSave);

		PublicUserEntity result = service.save(userToSave);

		Mockito.verify(publicUserRepo, Mockito.times(1)).save(userToSave);

		Assertions.assertEquals("SaveMe", result.getNickname());
	}


	@Test
	void givenValidNewNicknameExpectUpdateRepo() throws Exception {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity existingUser = new PublicUserEntity();
		existingUser.setNickname("old");
		Mockito.when(publicUserRepo.findUserById(7)).thenReturn(Optional.of(existingUser));

		Mockito.when(publicUserRepo.existsByNickname("new")).thenReturn(false);

		service.updateNickname("new", 7);

		Mockito.verify(publicUserRepo, Mockito.times(1)).updateNickname("new", 7);
	}


	@Test
	void givenUserIdExpectUserFromRepo() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		Mockito.when(publicUserRepo.findUserById(16)).thenReturn(Optional.of(user));

		Optional<PublicUserEntity> result = service.findPublicUserById(16);

		Mockito.verify(publicUserRepo, Mockito.times(1)).findUserById(16);

		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(user, result.get());
	}


	// PROFILE IMAGE
	@Test
	//UserID doesn't exist so can't update ProfileImage
	void givenInvalidUserIdExpectUserNotFoundExceptionProfileImage() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findUserById(16)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.updateProfileImage(2, 16)
		);
		Assertions.assertEquals("User not found with id: 16", ex.getMessage());
	}

	@Test
	void givenInvalidImageIdExpectImageNotFoundException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		ProfileImageEntity currentImage = new ProfileImageEntity();
		currentImage.setId(1);
		user.setProfileImage(currentImage);

		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(imageRepo.findById(16)).thenReturn(Optional.empty());

		ImageNotFoundException ex = Assertions.assertThrows(ImageNotFoundException.class, () ->
			service.updateProfileImage(16, 1)
		);
		Assertions.assertEquals("Profile image with this id not found", ex.getMessage());
	}

	@Test
	void givenSameImageIdExpectUnchangedImageException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		ProfileImageEntity img = new ProfileImageEntity();
		img.setId(5);
		PublicUserEntity user = new PublicUserEntity();
		user.setProfileImage(img);

		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(imageRepo.findById(5)).thenReturn(Optional.of(img));

		UnchangedImageException ex = Assertions.assertThrows(UnchangedImageException.class, () ->
			service.updateProfileImage(5, 1)
		);
		Assertions.assertEquals("Image must be different from the current one.", ex.getMessage());
	}


	@Test
		//Happy path
	void givenNewImageExpectUpdateProfileImage() throws Exception {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		ProfileImageEntity oldImage = new ProfileImageEntity();
		oldImage.setId(1);
		PublicUserEntity user = new PublicUserEntity();
		user.setProfileImage(oldImage);
		Mockito.when(publicUserRepo.findUserById(5)).thenReturn(Optional.of(user));

		ProfileImageEntity newImage = new ProfileImageEntity();
		newImage.setId(2);
		Mockito.when(imageRepo.findById(2)).thenReturn(Optional.of(newImage));

		service.updateProfileImage(2, 5);

		Mockito.verify(publicUserRepo).save(user);
		Assertions.assertEquals(2, user.getProfileImage().getId());
	}


	//	LANGUAGES
	@Test
	void givenInvalidUserIdExpectUserNotFoundExceptionLanguages() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findUserById(232)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.updateLanguages(232, new String[]{"English", "Spanish"})
		);
		Assertions.assertEquals("User not found with id: 232", ex.getMessage());
	}


	@Test
	void givenNullLanguageArrayExpectSelectLenguageException() {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		NullPointerException ex = Assertions.assertThrows(NullPointerException.class, () ->
			service.updateLanguages(1, null)
		);
		Assertions.assertEquals("A not null languages array is required, if no languages are selected use an empty array instead of null", ex.getMessage());
	}


	//Happy path
	@Test
	void givenLanguagesExpectUpdateUserLanguages() throws Exception {
		UserRepository publicUserRepo = Mockito.mock(UserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		CategoryRepository languageRepo = Mockito.mock(CategoryRepository.class);
		UserService service = new UserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		Mockito.when(publicUserRepo.findUserById(42)).thenReturn(Optional.of(user));

		CategoryEntity java = new CategoryEntity();
		java.setName("Java");
		CategoryEntity python = new CategoryEntity();
		python.setName("Python");
		CategoryEntity cpp = new CategoryEntity();
		cpp.setName("C++");
		Set<CategoryEntity> newLanguages = Set.of(java, python, cpp);
		Mockito.when(languageRepo.findByNameInIgnoreCase(new String[]{"Java", "Python", "C++"})).thenReturn(newLanguages);

		service.updateLanguages(42, new String[]{"Java", "Python", "C++"});

		Assertions.assertTrue(user.getLanguages().contains(java));
		Assertions.assertTrue(user.getLanguages().contains(python));
		Assertions.assertTrue(user.getLanguages().contains(cpp));
		Assertions.assertEquals(3, user.getLanguages().size());

		Mockito.verify(publicUserRepo).save(user);
	}


}
