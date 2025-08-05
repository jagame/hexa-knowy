package com.knowy.server.application.service;


import com.knowy.server.application.domain.Category;
import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.User;
import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.exception.KnowyPasswordFormatException;
import com.knowy.server.application.exception.KnowyTokenException;
import com.knowy.server.application.exception.KnowyWrongPasswordException;
import com.knowy.server.application.ports.KnowyPasswordChecker;
import com.knowy.server.application.ports.KnowyPasswordEncoder;
import com.knowy.server.application.ports.KnowyTokenTools;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.application.service.exception.KnowyInvalidUserEmailException;
import com.knowy.server.application.service.exception.KnowyInvalidUserPasswordFormatException;
import com.knowy.server.application.service.exception.KnowyUnchangedEmailException;
import com.knowy.server.application.service.exception.KnowyUserNotFoundException;
import com.knowy.server.application.service.model.NewUserResult;
import com.knowy.server.application.service.model.PasswordResetInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserPrivateServiceTest {

	@Mock
	private UserPrivateRepository userPrivateRepository;

	@Mock
	private KnowyPasswordEncoder passwordEncoder;

	@Mock
	private KnowyPasswordChecker passwordChecker;

	@Mock
	private KnowyTokenTools tokenTools;

	@Spy
	@InjectMocks
	private UserPrivateService userPrivateService;

	// method create
	@Test
	void given_validEmailAndPassword_when_createNewPrivateUser_then_returnNewPrivateUser() throws Exception {
		NewUserResult newUserResult = new NewUserResult(
			"TestNickname", new ProfileImage(1, "https://knowy/image.png"), new HashSet<>()
		);
		UserPrivate userPrivateResult = new UserPrivate(
			1,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<Category>(),
			"test@email.com",
			"ValidPass123@",
			true
		);

		Mockito.when(userPrivateRepository.findByEmail(userPrivateResult.email()))
			.thenReturn(Optional.empty());
		Mockito.when(passwordChecker.isRightPasswordFormat(userPrivateResult.password()))
			.thenReturn(true);
		Mockito.when(passwordEncoder.encode(userPrivateResult.password()))
			.thenReturn("ENCODED_PASS");
		Mockito.when(userPrivateRepository.save(any(UserPrivate.class)))
			.thenReturn(userPrivateResult);

		UserPrivate newUserPrivate = userPrivateService.create("test@email.com", "ValidPass123@", newUserResult);
		assertEquals(newUserPrivate, userPrivateResult);
	}

	@Test
	void given_existingEmail_when_createNewPrivateUser_then_throwKnowyInvalidUserEmailException() {
		NewUserResult newUserResult = new NewUserResult(
			"TestNickname", new ProfileImage(1, "https://knowy/image.png"), new HashSet<>()
		);

		User user = new User(
			1, newUserResult.nickname(), newUserResult.profileImage(), newUserResult.categories()
		);
		UserPrivate userPrivate = new UserPrivate(user, "exists@gmail.com", "ValidPass123");

		Mockito.when(userPrivateRepository.findByEmail("exists@gmail.com"))
			.thenReturn(Optional.of(userPrivate));

		assertThrows(
			KnowyInvalidUserEmailException.class,
			() -> userPrivateService.create("exists@gmail.com", "ValidPass123", newUserResult)
		);
	}

	@Test
	void given_invalidPassword_when_createNewPrivateUser_then_throwKnowyInvalidUserPasswordFormatException() {
		NewUserResult newUserResult = new NewUserResult(
			"TestNickname", new ProfileImage(1, "https://knowy/image.png"), new HashSet<>()
		);

		Mockito.when(userPrivateRepository.findByEmail("test@email.com"))
			.thenReturn(Optional.empty());

		assertThrows(
			KnowyInvalidUserPasswordFormatException.class, () ->
				userPrivateService.create("test@email.com", "invalidPassword", newUserResult)
		);
	}


	// method updateEmail
	// Happy Path
	@Test
	void givenValidEmailAndCorrectPasswordExpectEmailUpdated() throws Exception {
		UserPrivate userPrivate = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<Category>(),
			"old@email.com",
			"ENCODED_PASS",
			true
		);

		UserPrivate newUserPrivate = new UserPrivate(
			userPrivate.cropToUser(),
			"new@mail.com",
			userPrivate.password()
		);

		Mockito.when(userPrivateRepository.findById(16))
			.thenReturn(Optional.of(userPrivate));
		Mockito.when(userPrivateRepository.findByEmail("new@mail.com"))
			.thenReturn(Optional.empty());

		userPrivateService.updateEmail("new@mail.com", 16, "RAW_PASS");
		Mockito.verify(userPrivateRepository, Mockito.times(1)).save(newUserPrivate);
	}


	@Test
	void given_sameEmail_when_updateEmail_then_KnowyUnchangedEmailException() {
		UserPrivate userPrivateResult = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"same@email.com",
			"ENCODED_PASS",
			true
		);

		Mockito.when(userPrivateRepository.findById(16))
			.thenReturn(Optional.of(userPrivateResult));

		assertThrows(KnowyUnchangedEmailException.class, () -> {
			userPrivateService.updateEmail("same@email.com", 16, "RAW_PASS");
		});
	}

	@Test
	void given_emailAlreadyExists_when_updateEmail_then_KnowyInvalidUserEmailException() {
		UserPrivate userPrivateResult = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"old@email.com",
			"ENCODED_PASS",
			true
		);

		UserPrivate otherUserPrivate = new UserPrivate(
			24,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"other@email.com",
			"ENCODED_PASS",
			true
		);

		Mockito.when(userPrivateRepository.findById(16))
			.thenReturn(Optional.of(userPrivateResult));
		Mockito.when(userPrivateRepository.findByEmail("new@email.com"))
			.thenReturn(Optional.of(otherUserPrivate));

		assertThrows(
			KnowyInvalidUserEmailException.class,
			() -> userPrivateService.updateEmail("new@email.com", 16, "RAW_PASS")
		);
	}

	@Test
	void given_wrongPassword_when_updateEmail_then_throwKnowyWrongPasswordException() throws KnowyWrongPasswordException {
		UserPrivate userPrivate = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"old@email.com",
			"ValidPass123@",
			true
		);

		Mockito.when(userPrivateRepository.findById(16))
			.thenReturn(Optional.of(userPrivate));
		Mockito.when(userPrivateRepository.findByEmail("new@email.com"))
			.thenReturn(Optional.empty());
		Mockito.doThrow(new KnowyWrongPasswordException("Invalid password"))
			.when(passwordChecker)
			.assertHasPassword(userPrivate, "RAW_PASS");

		assertThrows(
			KnowyWrongPasswordException.class,
			() -> userPrivateService.updateEmail("new@email.com", 16, "RAW_PASS")
		);
	}

	// method resetPassword
	@Test
	void given_validTokenAndMatchingPasswords_when_resetPassword_then_passwordResetSuccess() throws Exception {
		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(11, "user@mail.com");
		UserPrivate userPrivate = new UserPrivate(
			11,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"test@email.com",
			"ValidOldPass123@",
			true
		);

		UserPrivate newUserPrivate = new UserPrivate(
			11,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"test@email.com",
			"ENCODED_NEW_PASSWORD",
			true
		);

		Mockito.when(tokenTools.decodeUnverified("valid-token", PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);
		Mockito.when(userPrivateRepository.findById(userPrivate.id()))
			.thenReturn(Optional.of(userPrivate));
		Mockito.when(tokenTools.decode(userPrivate.password(), "valid-token", PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);
		Mockito.when(passwordEncoder.encode("ValidNewPass123@"))
			.thenReturn("ENCODED_NEW_PASSWORD");

		userPrivateService.resetPassword("valid-token", "ValidNewPass123@", "ValidNewPass123@");

		Mockito.verify(userPrivateRepository).save(newUserPrivate);
	}

	@Test
	void given_invalidPasswordFormat_when_resetPassword_then_KnowyPasswordFormatException() throws KnowyPasswordFormatException {
		Mockito.doThrow(new KnowyPasswordFormatException("Invalid format"))
			.when(passwordChecker).assertPasswordFormatIsRight("invalidPassword");

		assertThrows(
			KnowyPasswordFormatException.class,
			() -> userPrivateService.resetPassword(
				"some-token",
				"invalidPassword",
				"invalidPassword")
		);
		Mockito.verify(passwordChecker, Mockito.times(1)).assertPasswordFormatIsRight("invalidPassword");
	}

	@Test
	void given_mismatchedPasswords_when_resetPassword_then_KnowyTokenException() {
		assertThrows(
			KnowyWrongPasswordException.class,
			() -> userPrivateService.resetPassword(
				"some-token",
				"VALID.pass1234",
				"VALID.diffPass1234")
		);
	}

	@Test
	void given_tokenWithNotExistUser_when_resetPassword_then_KnowyUserNotFoundException() throws Exception {
		String token = "invalid-token";
		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(16, "x@x.com");

		Mockito.when(tokenTools.decodeUnverified(token, PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);
		Mockito.when(userPrivateRepository.findById(passwordResetInfo.userId()))
			.thenReturn(Optional.empty());

		assertThrows(
			KnowyUserNotFoundException.class,
			() -> userPrivateService.resetPassword(token, "VALID.pass123.", "VALID.pass123.")
		);
	}

	@Test
	void given_invalidToken_when_resetPassword_then_KnowyTokenException() throws Exception {
		String token = "invalid-token";
		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(16, "x@x.com");
		UserPrivate userPrivate = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"test@email.com",
			"ValidPass123@",
			true
		);

		Mockito.when(tokenTools.decodeUnverified(token, PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);
		Mockito.when(userPrivateRepository.findById(16))
			.thenReturn(Optional.of(userPrivate));
		Mockito.doThrow(new KnowyTokenException("Invalid or expired token"))
			.when(tokenTools)
			.decode(userPrivate.password(), token, PasswordResetInfo.class);

		assertThrows(
			KnowyTokenException.class,
			() -> userPrivateService.resetPassword(token, "VALID.pass123.", "VALID.pass123.")
		);
	}


	// method isValidToken
	@Test
	void given_nullToken_when_validateToke_then_throwNullPointerException() {
		assertThrows(NullPointerException.class, () -> userPrivateService.isValidToken(null));
	}

	@Test
	void givenValidTokenExpectTrue() throws KnowyTokenException {
		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(11, "user@mail.com");
		UserPrivate userPrivate = new UserPrivate(
			11,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"test@email.com",
			"ValidOldPass123@",
			true
		);

		Mockito.when(tokenTools.decodeUnverified("valid-token", PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);
		Mockito.when(userPrivateRepository.findById(userPrivate.id()))
			.thenReturn(Optional.of(userPrivate));
		Mockito.when(tokenTools.decode(userPrivate.password(), "valid-token", PasswordResetInfo.class))
			.thenReturn(passwordResetInfo);

		assertTrue(userPrivateService.isValidToken("valid-token"));
	}

	@Test
	void given_invalidToken_when_validateToken_then_returnsFalse() throws KnowyTokenException, KnowyUserNotFoundException {
		Mockito.doThrow(new KnowyTokenException("Invalid Token"))
			.when(userPrivateService)
			.verifyPasswordToken("invalid-token");

		boolean result = userPrivateService.isValidToken("invalid-token");
		assertFalse(result);
	}

	@Test
	void given_invalidTokenByUserNotFound_when_validateToken_then_returnsFalse()
		throws KnowyTokenException, KnowyUserNotFoundException {
		Mockito.doThrow(new KnowyUserNotFoundException("Invalid Token"))
			.when(userPrivateService)
			.verifyPasswordToken("invalid-token");

		boolean result = userPrivateService.isValidToken("invalid-token");
		assertFalse(result);
	}

	/*
	//GER PRIVATE USER BY EMAIL
	@Test
	void givenExistingEmailExpectReturnUser() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "user@mail.com";
		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail(email);

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));

		PrivateUserEntity result = service.getPrivateUserByEmail(email);

		Assertions.assertEquals(email, result.getEmail());
	}

	@Test
	void givenNonExistingEmailExpectUserNotFoundException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "missing@mail.com";
		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.getPrivateUserByEmail(email);
		});
		Assertions.assertEquals("User not found", ex.getMessage());
	}
	// method getPrivateUserById - getByEmail
		@Test
	void given_validUserId_when_getPrivateUser_then_returnPrivateUser() {
		UserPrivate userPrivate = new UserPrivate(
			16,
			"TestNickname",
			new ProfileImage(1, "https://knowy/image.png"),
			new HashSet<>(),
			"old@email.com",
			"ValidPass123@",
			true
		);

		Mockito.when(userPrivateRepository.findById(userPrivate.id()))
			.thenReturn(Optional.of(userPrivate));

		UserPrivate gotUserPrivate = assertDoesNotThrow(() -> userPrivateService.getPrivateUserById(16));
		assertEquals(userPrivate, gotUserPrivate);
	}

	@Test
	void given_invalidUserId_when_getPrivateUser_then_returnPrivateUser() {
		Mockito.when(userPrivateRepository.findById(Mockito.anyInt()))
			.thenReturn(Optional.empty());

		assertThrows(
			KnowyUserNotFoundException.class,
			() -> userPrivateService.getPrivateUserById(Mockito.anyInt())
		);
	}




	// CREATE RECOVERY PASSWORD EMAIL
	@Test
	void givenNonExistentEmailExpectUserNotFoundException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String email = "missing@mail.com";
		String recoveryBaseUrl = "https://app.url/recover";

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.createRecoveryPasswordEmail(email, recoveryBaseUrl);
		});
		Assertions.assertEquals("The user with email missing@mail.com was not found", ex.getMessage());
	}

	@Test
	void givenJwtToolsEncodeThrowsExceptionExpectJwtKnowyException() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String email = "user@mail.com";
		String recoveryBaseUrl = "https://app.url/recover";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(124);
		user.setEmail(email);
		user.setPassword("encoded-password");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq(user.getPassword()), Mockito.anyLong())).thenThrow(new KnowyTokenException("Token encoding failed"));

		KnowyTokenException ex = Assertions.assertThrows(KnowyTokenException.class, () -> {
			service.createRecoveryPasswordEmail(email, recoveryBaseUrl);
		});
		Assertions.assertEquals("Token encoding failed", ex.getMessage());
	}

	@Test
	void givenValidEmailExpectReturnsMailMessage() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String email = "user@mail.com";
		String recoveryBaseUrl = "https://app.url/recover";
		String expectedToken = "mocked-token";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(123);
		user.setEmail(email);
		user.setPassword("encoded-password");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq(user.getPassword()), Mockito.anyLong()))
			.thenReturn(expectedToken);

		MailMessage mailMessage = service.createRecoveryPasswordEmail(email, recoveryBaseUrl);

		Assertions.assertEquals(email, mailMessage.to());
		Assertions.assertEquals("Tu enlace para recuperar la cuenta de Knowy está aquí", mailMessage.subject());
		Assertions.assertTrue(mailMessage.body().contains(expectedToken));
		Assertions.assertTrue(mailMessage.body().contains(recoveryBaseUrl));
	}


	// TOKEN BODY
	@Test
	void givenUnknownEmailExpectUserNotFoundException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String unknownEmail = "unknown@example.com";

		Mockito.when(repo.findByEmail(unknownEmail)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.createRecoveryPasswordEmail(unknownEmail, "https://knowy.app/recovery");
		});

		Assertions.assertEquals(
			"The user with email unknown@example.com was not found", ex.getMessage());
	}

	@Test
	void givenValidEmailExpectJwtKnowyException() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String email = "user@example.com";
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(10);
		user.setEmail(email);
		user.setPassword("encoded-pass");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq(user.getPassword()), Mockito.anyLong()))
			.thenThrow(new KnowyTokenException("Token encode failed"));

		KnowyTokenException ex = Assertions.assertThrows(KnowyTokenException.class, () -> {
			service.createRecoveryPasswordEmail(email, "https://knowy.app/recovery");
		});

		Assertions.assertEquals("Token encode failed", ex.getMessage());
	}

	@Test
	void givenValidEmailAndUrlExpectCallsJwtEncode() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		String email = "user@example.com";
		String recoveryUrl = "https://knowy.app/recovery";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(10);
		user.setEmail(email);
		user.setPassword("encoded-pass");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq(user.getPassword()), Mockito.anyLong()))
			.thenReturn("mocked-token");

		service.createRecoveryPasswordEmail(email, recoveryUrl);

		Mockito.verify(jwtTools, Mockito.times(1))
			.encode(Mockito.any(), Mockito.eq(user.getPassword()), Mockito.anyLong());
	}


	//CREATE DELETED ACCOUNT EMAIL
	@Test
	void givenNonExistingEmailWhenCreateDeletedAccountEmailThenThrowsUserNotFoundException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		Mockito.when(repo.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.createDeletedAccountEmail("notfound@example.com", "http://app.url");
		});
		Assertions.assertEquals("The user with email notfound@example.com was not found", ex.getMessage());
	}

	@Test
	void givenValidEmailWhenCreateDeletedAccountEmailThenReturnsMailMessage() throws Exception {
		// Arrange
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(1);
		user.setEmail("email@example.com");
		user.setPassword("encoded-password");

		Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.of(user));

		String expectedToken = "mocked-token";

		Mockito.when(jwtTools.encode(Mockito.any(PasswordResetInfo.class), Mockito.eq("encoded-password"), Mockito.anyLong())).thenReturn(expectedToken);

		MailMessage mailMessage = service.createDeletedAccountEmail("email@example.com", "http://app.url");

		Assertions.assertEquals("email@example.com", mailMessage.to());
		Assertions.assertEquals("Tu enlace para recuperar la cuenta de Knowy está aquí", mailMessage.subject());
		Assertions.assertTrue(mailMessage.body().contains(expectedToken));

		Mockito.verify(jwtTools, Mockito.times(1))
			.encode(Mockito.any(PasswordResetInfo.class), Mockito.eq("encoded-password"), Mockito.anyLong());
	}


	// DESACTIVATE USER ACCOUNT
	@Test
	void givenNonexistentUserExpectUserNotFoundException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "nonexistent@mail.com";
		String password = "pass";
		String confirmPassword = "pass";

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.desactivateUserAccount(email, password, confirmPassword);
		});
		Assertions.assertEquals("User not found", ex.getMessage());
		Mockito.verify(repo, Mockito.never()).save(Mockito.any());
	}

	@Test
	void givenValidEmailButPasswordsDoNotMatchExpectWrongPasswordException() {
		// Arrange
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "user@mail.com";
		String password = "pass1";
		String confirmPassword = "pass2";

		KnowyWrongPasswordException ex = Assertions.assertThrows(KnowyWrongPasswordException.class, () -> {
			service.desactivateUserAccount(email, password, confirmPassword);
		});
		Assertions.assertEquals("Passwords do not match", ex.getMessage());

		Mockito.verify(repo, Mockito.never()).save(Mockito.any());
	}

	@Test
	void givenValidEmailAndWrongPasswordExpectWrongPasswordException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "user@mail.com";
		String password = "wrongPass";
		String confirmPassword = "wrongPass";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail(email);
		user.setActive(true);
		user.setPassword("encodedPassword");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(encoder.matches(password, user.getPassword())).thenReturn(false);

		KnowyWrongPasswordException ex = Assertions.assertThrows(KnowyWrongPasswordException.class, () -> {
			service.desactivateUserAccount(email, password, confirmPassword);
		});
		Assertions.assertEquals("Wrong password for user with id: null", ex.getMessage());

		Mockito.verify(repo, Mockito.never()).save(Mockito.any());
	}

	@Test
	void givenValidEmailAndCorrectPasswordExpectDeactivateAndSave() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "user@mail.com";
		String password = "correctPass";
		String confirmPassword = "correctPass";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail(email);
		user.setActive(true);
		user.setPassword("encodedPassword");

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.of(user));
		Mockito.when(encoder.matches(password, user.getPassword())).thenReturn(true);

		service.desactivateUserAccount(email, password, confirmPassword);

		Assertions.assertFalse(user.isActive());
		Mockito.verify(repo, Mockito.times(1)).save(user);
	}


	// REACTIVATE USER ACCOUNT
	@Test
	void givenInvalidTokenExpectThrowsJwtKnowyException() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String token = "invalid-token";

		Mockito.when(jwt.decodeUnverified(Mockito.eq(token), Mockito.any()))
			.thenThrow(new KnowyTokenException("Invalid token"));

		KnowyTokenException ex = Assertions.assertThrows(KnowyTokenException.class, () -> {
			service.reactivateUserAccount(token);
		});

		Assertions.assertEquals("Invalid token", ex.getMessage());
	}

	@Test
	void givenValidTokenButUserNotFoundExpectUserNotFoundException() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String token = "valid-token";

		PasswordResetInfo info = new PasswordResetInfo(99, "email@example.com");
		Mockito.when(jwt.decodeUnverified(token, PasswordResetInfo.class)).thenReturn(info);

		Mockito.when(repo.findById(99)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = Assertions.assertThrows(KnowyUserNotFoundException.class, () -> {
			service.reactivateUserAccount(token);
		});
		Assertions.assertEquals("User not found with id: 99", ex.getMessage());
	}

	@Test
	void givenValidTokenAndUserAlreadyActiveExpectNotSave() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String token = "valid-token";

		PasswordResetInfo info = new PasswordResetInfo(42, "email@example.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setActive(true);

		Mockito.when(jwt.decodeUnverified(token, PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(42)).thenReturn(Optional.of(user));
		// Para que jwtTools.decode no lance excepción (es llamada dentro de verifyPasswordToken)
		Mockito.when(jwt.decode(Mockito.anyString(), Mockito.eq(token), Mockito.eq(PasswordResetInfo.class)))
			.thenReturn(info);

		service.reactivateUserAccount(token);

		Mockito.verify(repo, Mockito.never()).save(Mockito.any());
	}

	@Test
	void givenValidTokenAndUserInactiveExpectActivateAndSave() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String token = "valid-token";

		PasswordResetInfo info = new PasswordResetInfo(7, "email@example.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setActive(false);

		Mockito.when(jwt.decodeUnverified(token, PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(7)).thenReturn(Optional.of(user));
		Mockito.when(jwt.decode(Mockito.anyString(), Mockito.eq(token), Mockito.eq(PasswordResetInfo.class)))
			.thenReturn(info);

		service.reactivateUserAccount(token);

		Assertions.assertTrue(user.isActive());
		Mockito.verify(repo).save(user);
	}
	*/

}
