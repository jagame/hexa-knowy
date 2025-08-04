package com.knowy.server.application.service;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.User;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.ports.CategoryRepository;
import com.knowy.server.application.ports.ProfileImageRepository;
import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.application.service.exception.*;
import com.knowy.server.application.service.model.NewUserCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProfileImageRepository profileImageRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private UserService userService;

	// method create
	@Test
	void given_validNickname_when_creatingUser_then_returnNewUserCommand() {

		ProfileImage expectedProfileImage = new ProfileImage(1, "https://knowy/image.png");

		Mockito.when(profileImageRepository.findById(1))
			.thenReturn(Optional.of(expectedProfileImage));

		NewUserCommand result = assertDoesNotThrow(() -> userService.create("ValidNickname"));
		assertEquals(
			new NewUserCommand("ValidNickname", expectedProfileImage, new HashSet<>()),
			result
		);
	}

	@Test
	void given_existingNickname_when_creatingUser_then_throwKnowyException() {
		Mockito.when(userRepository.findByNickname("ExistNickname"))
			.thenReturn(Optional.of(new User(
				1,
				"ExistNickname",
				new ProfileImage(1, "https://knowy/image.png"),
				new HashSet<>()
			)));

		assertThrows(KnowyInvalidUserNicknameException.class, () -> userService.create("ExistNickname"));
	}

	@Test
	void given_blankNickname_when_creatingUser_then_throwKnowyInvalidUserNicknameException() {
		List<String> invalidNicknames = Arrays.asList(null, "", "   ");

		for (String invalidNickname : invalidNicknames) {
			assertThrows(KnowyInvalidUserNicknameException.class, () -> userService.create(invalidNickname));
		}
	}

	@Test
	void given_noDefaultImage_when_creatingUser_then_throwNotFoundException() {
		Mockito.when(profileImageRepository.findById(1))
			.thenReturn(Optional.empty());

		assertThrows(KnowyImageNotFoundException.class, () -> userService.create("ValidNickname"));
	}

	// method save
	@Test
	void given_user_when_saved_then_repositoryIsCalled_and_savedUserIsReturned() {
		User userToSave = new User(
			1,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.save(userToSave))
			.thenReturn(userToSave);

		User result = userService.save(userToSave);
		Mockito.verify(userRepository).save(userToSave);
		assertEquals(userToSave, result);
	}

	// method updateNickname
	@Test
	void given_validNewNickname_when_updateNickname_then_updateNicknameIsCalled() {
		User oldUser = new User(
			1,
			"OldNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(oldUser));
		Mockito.when(userRepository.existsByNickname("NewNickname")).thenReturn(false);

		assertDoesNotThrow(() -> userService.updateNickname("NewNickname", 1));
		Mockito.verify(userRepository).findById(1);
		Mockito.verify(userRepository).existsByNickname("NewNickname");
		Mockito.verify(userRepository, Mockito.times(1))
			.updateNickname("NewNickname", 1);
	}

	@Test
	void given_sameNickname_when_updateNickname_then_throwKnowyUnchangedNicknameException() {
		User otherUser = new User(
			1,
			"SameNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1))
			.thenReturn(Optional.of(otherUser));

		assertThrows(
			KnowyUnchangedNicknameException.class, () -> userService.updateNickname("SameNickname", 1)
		);
	}

	@Test
	void given_existingNickname_when_updateUser_then_throwKnowyException() {
		User user = new User(
			1,
			"ExistNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1))
			.thenReturn(Optional.of(user));
		Mockito.when(userRepository.existsByNickname("NewNicknameThatExistYet"))
			.thenReturn(true);

		assertThrows(
			KnowyNicknameAlreadyTakenException.class,
			() -> userService.updateNickname("NewNicknameThatExistYet", 1)
		);
	}

	@Test
	void given_blankNickname_when_updateUser_then_throwKnowyInvalidUserNicknameException() {
		List<String> invalidNicknames = Arrays.asList(null, "", "   ");

		for (String invalidNickname : invalidNicknames) {
			assertThrows(
				KnowyInvalidUserNicknameException.class,
				() -> userService.updateNickname(invalidNickname, 1)
			);
		}
	}

	// method updateProfileImage
	@Test
	void given_newImage_when_updateProfileImage_then_saveNewUserWithNewImage() throws Exception {
		User user = new User(
			1,
			"ExistNickname",
			new ProfileImage(6, "https://knowy/test-image.png"),
			new HashSet<>()
		);

		ProfileImage newProfileImage = new ProfileImage(4, "https://knowy/test-other-image.png");
		User newUser = new User(
			1,
			"ExistNickname",
			newProfileImage,
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1))
			.thenReturn(Optional.of(user));
		Mockito.when(profileImageRepository.findById(4))
			.thenReturn(Optional.of(newProfileImage));

		userService.updateProfileImage(4, 1);

		Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.eq(newUser));
	}

	@Test
	void given_invalidUserId_when_updateProfileImage_then_throwKnowyUserNotFoundException() {
		Mockito.when(userRepository.findById(Mockito.anyInt()))
			.thenReturn(Optional.empty());

		assertThrows(KnowyUserNotFoundException.class, () -> userService.updateProfileImage(2, 16));
	}

	@Test
	void given_invalidImageId_when_updateProfileImage_then_throwKnowyImageNotFoundException() {
		User user = new User(
			1,
			"ExistNickname",
			new ProfileImage(6, "https://knowy/test-image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1))
			.thenReturn(Optional.of(user));
		Mockito.when(profileImageRepository.findById(6))
			.thenReturn(Optional.empty());

		assertThrows(KnowyImageNotFoundException.class, () -> userService.updateProfileImage(6, 1));
	}

	@Test
	void given_sameImageId_when_updateProfileImage_then_throwKnowyUnchangedImageException() {
		User user = new User(
			1,
			"ExistNickname",
			new ProfileImage(6, "https://knowy/test-image.png"),
			new HashSet<>()
		);

		Mockito.when(userRepository.findById(1))
			.thenReturn(Optional.of(user));
		Mockito.when(profileImageRepository.findById(6))
			.thenReturn(Optional.of(user.profileImage()));

		assertThrows(KnowyUnchangedImageException.class, () -> userService.updateProfileImage(6, 1));
	}

	// method updateCategories
	@Test
	void given_newCategories_when_updateCategories_saveNewUserWithNewCategories() {
		User user = new User(
			42,
			"ExistNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		Set<Category> newCategories = Set.of(new Category(34, "Java"), new Category(12, "English"));
		User newUser = new User(
			42,
			"ExistNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			newCategories
		);

		Mockito.when(userRepository.findById(42))
			.thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findByNameInIgnoreCase(new String[]{"Java", "English"}))
			.thenReturn(newCategories);

		assertDoesNotThrow(() -> userService.updateCategories(42, new String[]{"Java", "English"}));
		Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.eq(newUser));
	}

	@Test
	void given_nullNewCategories_when_updateCategories_then_throwNullPointerException() {
		assertThrows(NullPointerException.class, () ->
			userService.updateCategories(1, null)
		);
	}

	@Test
	void given_invalidUserId_when_updateCategories_then_throwKnowyUserNotFoundException() {
		Mockito.when(userRepository.findById(232))
			.thenReturn(Optional.empty());

		assertThrows(
			KnowyUserNotFoundException.class,
			() -> userService.updateCategories(232, new String[]{"English", "Java"})
		);
	}

	@Test
	void given_nonPersistedCategories_when_updateCategories_then_throwKnowyInconsistentDataException() {
		User user = new User(
			10,
			"ExistNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>()
		);

		String[] newCategoriesArray = new String[]{"Java", "English", "CategoryThatDoesNotExistInDB"};
		Set<Category> newCategoriesSet = Set.of(new Category(34, "Java"), new Category(12, "English"));

		Mockito.when(userRepository.findById(42))
			.thenReturn(Optional.of(user));
		Mockito.when(categoryRepository.findByNameInIgnoreCase(newCategoriesArray))
			.thenReturn(newCategoriesSet);

		assertThrows(
			KnowyInconsistentDataException.class,
			() -> userService.updateCategories(42, newCategoriesArray)
		);
	}
}
