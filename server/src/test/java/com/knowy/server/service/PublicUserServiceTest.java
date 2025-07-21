package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.ports.LanguageRepository;
import com.knowy.server.repository.ports.ProfileImageRepository;
import com.knowy.server.repository.ports.PublicUserRepository;
import com.knowy.server.service.exception.ImageNotFoundException;
import com.knowy.server.service.exception.InvalidUserNicknameException;
import com.knowy.server.service.exception.UnchangedImageException;
import com.knowy.server.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;


public class PublicUserServiceTest {

	// NICKNAME
	@Test
	void givenExistingNicknameExpectAlreadyExistException() {
		// Mock: Simulate repos
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: Simulate nickname already exist
		Mockito.when(publicUserRepo.findByNickname("ExistNickname")).thenReturn(Optional.of(new PublicUserEntity()));

		// Execute-Verify
		InvalidUserNicknameException ex = Assertions.assertThrows(InvalidUserNicknameException.class, () ->
			service.create("ExistNickname")
		);
		Assertions.assertEquals("Nickname already exists", ex.getMessage());
	}

	//Valid Nickname but default image doesn't exist
	@Test
	void givenNoDefaultImageExpectNotFoundException() {
		// Simular repos
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: nickname no existe
		Mockito.when(publicUserRepo.findByNickname("ValidNickname")).thenReturn(Optional.empty());

		// Mock: imagen con ID 1 no existe
		Mockito.when(imageRepo.findById(1)).thenReturn(Optional.empty());

		// Ejecutar y verificar excepción
		ImageNotFoundException ex = Assertions.assertThrows(ImageNotFoundException.class, () ->
			service.create("ValidNickname")
		);
		Assertions.assertEquals("Not found profile image", ex.getMessage());
	}


	@Test
	void givenSameNicknameExpectUnchangedException() {
		// Simular repos
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Simular usuario existente
		PublicUserEntity user = new PublicUserEntity();
		user.setNickname("SameNickname");
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));

		// Ejecutar y verificar excepción
		Exception ex = Assertions.assertThrows(com.knowy.server.service.exception.UnchangedNicknameException.class, () ->
			service.updateNickname("SameNickname", 1)
		);
		Assertions.assertEquals("Nickname must be different from the current one.", ex.getMessage());
	}


	@Test
	void givenExistingNicknameExpectAlreadyTakenException() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		PublicUserEntity user = new PublicUserEntity();
		user.setNickname("judit");
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(publicUserRepo.existsByNickname("nuevo")).thenReturn(true);

		Exception ex = Assertions.assertThrows(
			com.knowy.server.service.exception.NicknameAlreadyTakenException.class,
			() -> service.updateNickname("nuevo", 1)
		);
		Assertions.assertEquals("Nickname is already in use.", ex.getMessage());
	}


	@Test
	void givenBlankNicknameExpectNotAllowException() {
		// Mock: Simulate repos
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);

		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Execute Exception
		InvalidUserNicknameException ex = Assertions.assertThrows(
			InvalidUserNicknameException.class, () ->
				service.create(" ")
		);
		Assertions.assertEquals("Blank nicknames are not allowed", ex.getMessage());
	}


	@Test
		//Find Nickname Happy path
	void givenValidNicknameExpectFindUser() throws Exception {
		// Simulate repos
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: nickname doesn't exist
		Mockito.when(publicUserRepo.findByNickname("judit")).thenReturn(Optional.empty());

		// Mock: fakeImage
		ProfileImageEntity fakeImage = new ProfileImageEntity();
		fakeImage.setId(1);
		Mockito.when(imageRepo.findById(1)).thenReturn(Optional.of(fakeImage));

		// Execute-Verify
		PublicUserEntity result = service.create("judit");
		Assertions.assertEquals("judit", result.getNickname());
		Assertions.assertEquals(1, result.getProfileImage().getId());

		Mockito.verify(publicUserRepo, Mockito.times(1)).findByNickname("judit");
		Mockito.verify(imageRepo).findById(1);
	}


	@Test
		// Save Correct User Happy path
	void givenUserWhenSaveExpectRepoUserReturned() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: User to save
		PublicUserEntity userToSave = new PublicUserEntity();
		userToSave.setNickname("SaveMe");

		// Mock: repo returns same user
		Mockito.when(publicUserRepo.save(userToSave)).thenReturn(userToSave);

		//Execute save
		PublicUserEntity result = service.save(userToSave);

		// Verify save method is called only once
		Mockito.verify(publicUserRepo, Mockito.times(1)).save(userToSave);

		// Check if return the same Nickname "SaveMe"
		Assertions.assertEquals("SaveMe", result.getNickname());
	}


	@Test
		// Update Nickname Happy path
	void givenValidNewNicknameExpectUpdateRepo() throws Exception {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: Exist UserID=6 with Nickname "old"
		PublicUserEntity existingUser = new PublicUserEntity();
		existingUser.setNickname("old");
		Mockito.when(publicUserRepo.findUserById(7)).thenReturn(Optional.of(existingUser));

		// Mock: "new" Nickname doesn't exist (false)
		Mockito.when(publicUserRepo.existsByNickname("new")).thenReturn(false);

		// Execute updateNickname
		service.updateNickname("new", 7);

		// Verify
		Mockito.verify(publicUserRepo, Mockito.times(1)).updateNickname("new", 7);
	}


	@Test
		// Obtain User from Repo using UserID
	void givenUserIdExpectUserFromRepo() {
		// Mock repos y servicio
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: UserID
		PublicUserEntity user = new PublicUserEntity();
		Mockito.when(publicUserRepo.findUserById(16)).thenReturn(Optional.of(user));

		// Execute findPublicUserById()
		Optional<PublicUserEntity> result = service.findPublicUserById(16);

		// Verify
		Mockito.verify(publicUserRepo, Mockito.times(1)).findUserById(16);

		// Check a result
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(user, result.get());
	}


// PROFILE IMAGE

	@Test
		//UserID doesn't exist so can't update ProfileImage
	void givenInvalidUserIdExpectUserNotFoundExceptionProfileImage() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		Mockito.when(publicUserRepo.findUserById(16)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.updateProfileImage(2, 16)
		);
		Assertions.assertEquals("User not found with id: 16", ex.getMessage());
	}


	@Test
		//UserID Exist but not ProfileImageID
	void givenInvalidImageIdExpectImageNotFoundException() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		//New UserID with ProfileImageID
		PublicUserEntity user = new PublicUserEntity();
		ProfileImageEntity currentImage = new ProfileImageEntity();
		currentImage.setId(1);
		user.setProfileImage(currentImage);

		//Mock: UserID Exists but ProfileImageID doesn't
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(imageRepo.findById(16)).thenReturn(Optional.empty());

		ImageNotFoundException ex = Assertions.assertThrows(ImageNotFoundException.class, () ->
			service.updateProfileImage(16, 1)
		);
		Assertions.assertEquals("Profile image with this id not found", ex.getMessage());
	}


	@Test
	void givenSameImageIdExpectUnchangedImageException() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		//New User with same ProfileImageID (that Mock later)
		ProfileImageEntity img = new ProfileImageEntity();
		img.setId(5);
		PublicUserEntity user = new PublicUserEntity();
		user.setProfileImage(img);

		//Mock: UserID and ProfileImageID Exists but same current ProfileImageID
		Mockito.when(publicUserRepo.findUserById(1)).thenReturn(Optional.of(user));
		Mockito.when(imageRepo.findById(5)).thenReturn(Optional.of(img));

		UnchangedImageException ex = Assertions.assertThrows(UnchangedImageException.class, () ->
			service.updateProfileImage(5, 1)
		);
		Assertions.assertEquals("Image must be different from the current one.", ex.getMessage());
	}


	@Test
		//ProfileImage Happy path
	void givenNewImageExpectUpdateProfileImage() throws Exception {
		// Mock: Repos y servicio
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		//New UserID(=5) with oldImageID
		ProfileImageEntity oldImage = new ProfileImageEntity();
		oldImage.setId(1);
		PublicUserEntity user = new PublicUserEntity();
		user.setProfileImage(oldImage);
		//Mock: UserID=5 with oldImageID=1
		Mockito.when(publicUserRepo.findUserById(5)).thenReturn(Optional.of(user));

		// Mock: newImageID=2 to return later
		ProfileImageEntity newImage = new ProfileImageEntity();
		newImage.setId(2);
		Mockito.when(imageRepo.findById(2)).thenReturn(Optional.of(newImage));

		// Execute updateProfileImage
		service.updateProfileImage(2, 5);

		// Check is the User save the newImageID
		Mockito.verify(publicUserRepo).save(user);
		Assertions.assertEquals(2, user.getProfileImage().getId());
	}


//	LANGUAGES


	@Test
		//UserID doesn't exist so can't update Language
	void givenInvalidUserIdExpectUserNotFoundExceptionLanguages() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: usuarID 232 doesn't exist
		Mockito.when(publicUserRepo.findUserById(232)).thenReturn(Optional.empty());

		// Verify UserNotFountException
		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.updateLanguages(232, new String[]{"English", "Spanish"})
		);
		Assertions.assertEquals("User not found with id: 232", ex.getMessage());
	}


	@Test
	void givenNullLanguageArrayExpectSelectLenguageException() {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Check if languages array is null
		NullPointerException ex = Assertions.assertThrows(NullPointerException.class, () ->
			service.updateLanguages(1, null)
		);
		Assertions.assertEquals("A not null languages array is required, if no languages are selected use an empty array instead of null", ex.getMessage());
	}


	@Test
		//Languages Happy path
	void givenLanguagesExpectUpdateUserLanguages() throws Exception {
		PublicUserRepository publicUserRepo = Mockito.mock(PublicUserRepository.class);
		ProfileImageRepository imageRepo = Mockito.mock(ProfileImageRepository.class);
		LanguageRepository languageRepo = Mockito.mock(LanguageRepository.class);
		PublicUserService service = new PublicUserService(publicUserRepo, languageRepo, imageRepo);

		// Mock: UserID
		PublicUserEntity user = new PublicUserEntity();
		Mockito.when(publicUserRepo.findUserById(42)).thenReturn(Optional.of(user));

		// Mock: create repo Set.of() wich will be returned when arrayLanguages={"Java", "Python", "C++"}
		LanguageEntity java = new LanguageEntity();
		java.setName("Java");
		LanguageEntity python = new LanguageEntity();
		python.setName("Python");
		LanguageEntity cpp = new LanguageEntity();
		cpp.setName("C++");
		Set<LanguageEntity> newLanguages = Set.of(java, python, cpp);
		Mockito.when(languageRepo.findByNameInIgnoreCase(new String[]{"Java", "Python", "C++"}))
			.thenReturn(newLanguages);

		// Execute updateLanguages
		service.updateLanguages(42, new String[]{"Java", "Python", "C++"});

		// Verify the new languages
		Assertions.assertTrue(user.getLanguages().contains(java));
		Assertions.assertTrue(user.getLanguages().contains(python));
		Assertions.assertTrue(user.getLanguages().contains(cpp));
		Assertions.assertEquals(3, user.getLanguages().size());

		// Check save method
		Mockito.verify(publicUserRepo).save(user);
	}


}
