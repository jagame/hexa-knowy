package com.knowy.server.service;


import com.knowy.server.application.exception.KnowyPasswordFormatException;
import com.knowy.server.application.exception.KnowyWrongPasswordException;
import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.infrastructure.adapters.repository.entity.PrivateUserEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.application.service.exception.KnowyInvalidUserEmailException;
import com.knowy.server.application.service.exception.KnowyInvalidUserPasswordFormatException;
import com.knowy.server.application.service.exception.KnowyUnchangedEmailException;
import com.knowy.server.application.service.exception.KnowyUserNotFoundException;
import com.knowy.server.application.service.model.MailMessage;
import com.knowy.server.application.service.model.PasswordResetInfo;
import com.knowy.server.util.JwtTools;
import com.knowy.server.application.exception.KnowyTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserPrivateServiceTest {

	// CREATE
	@Test
	void givenExistingEmailExpectInvalidUserEmailException() {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "exists@mail.com";
		String password = "ValidPass123.";
		PublicUserEntity publicUser = new PublicUserEntity();

		when(repo.findByEmail(email)).thenReturn(Optional.of(new PrivateUserEntity()));

		KnowyInvalidUserEmailException ex = assertThrows(KnowyInvalidUserEmailException.class,
			() -> service.create(email, password, publicUser));

		assertEquals("Email already exists", ex.getMessage());
	}

	@Test
	void givenInvalidPasswordExpectInvalidUserPasswordFormatException() {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);

		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "new@mail.com";
		String badPassword = "invalid123";
		PublicUserEntity publicUser = new PublicUserEntity();

		when(repo.findByEmail(email)).thenReturn(Optional.empty());

		KnowyInvalidUserPasswordFormatException ex = assertThrows(KnowyInvalidUserPasswordFormatException.class, () ->
			service.create(email, badPassword, publicUser));

		assertEquals("Invalid password format", ex.getMessage());
	}

	@Test
		//Happy Path
	void givenValidEmailAndPasswordExpectUserSaved() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "user@example.com";
		String validPassword = "ValidPass123.";
		PublicUserEntity publicUser = new PublicUserEntity();

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.empty());
		Mockito.when(encoder.encode(validPassword)).thenReturn("ENCODED_PASS");

		service.create(email, validPassword, publicUser);

		Mockito.verify(repo).save(Mockito.argThat(saved ->
			saved.getEmail().equals(email) &&
				saved.getPassword().equals("ENCODED_PASS") &&
				saved.getPublicUserEntity() == publicUser
		));
	}


	// UPDATE EMAIL
	@Test
	void givenSameEmailExpectUnchangedEmailException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		int userId = 16;
		String email = "same@mail.com";
		String password = "CALID.password123";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(userId);
		user.setEmail(email);

		Mockito.when(repo.findById(userId)).thenReturn(Optional.of(user));

		KnowyUnchangedEmailException ex = Assertions.assertThrows(KnowyUnchangedEmailException.class, () -> {
			service.updateEmail(email, userId, password);
		});
		Assertions.assertEquals("Email must be different from the current one.", ex.getMessage());
	}

	@Test
	void givenEmailAlreadyExistsExpectInvalidUserEmailException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		int userId = 16;
		String currentEmail = "current@mail.com";
		String newEmail = "taken@mail.com";
		String password = "VALID.password123";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(userId);
		user.setEmail(currentEmail);

		PrivateUserEntity otherUser = new PrivateUserEntity();
		otherUser.setEmail(newEmail);

		Mockito.when(repo.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(repo.findByEmail(newEmail)).thenReturn(Optional.of(otherUser));

		KnowyInvalidUserEmailException ex = Assertions.assertThrows(KnowyInvalidUserEmailException.class, () -> {
			service.updateEmail(newEmail, userId, password);
		});
		Assertions.assertEquals("The provided email is already associated with an existing account.", ex.getMessage());
	}

	@Test
	void givenWrongPasswordExpectThrowsWrongPasswordException() {
		// Arrange
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		int userId = 16;
		String currentEmail = "current@mail.com";
		String newEmail = "new@mail.com";
		String password = "wrong-password";
		String encodedPassword = "encoded-password";

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(userId);
		user.setEmail(currentEmail);
		user.setPassword(encodedPassword);

		Mockito.when(repo.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(repo.findByEmail(newEmail)).thenReturn(Optional.empty());
		Mockito.when(encoder.matches(password, encodedPassword)).thenReturn(false);

		KnowyWrongPasswordException ex = Assertions.assertThrows(KnowyWrongPasswordException.class, () -> {
			service.updateEmail(newEmail, userId, password);
		});
		Assertions.assertEquals("Wrong password for user with id: 16", ex.getMessage());
	}

	@Test
		// Happy Path
	void givenValidEmailAndCorrectPasswordExpectEmailUpdated() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail("old@mail.com");
		user.setPassword("encoded-pass");

		Mockito.when(repo.findById(5)).thenReturn(Optional.of(user));
		Mockito.when(repo.findByEmail("new@mail.com")).thenReturn(Optional.empty());

		Mockito.when(encoder.matches("raw-pass", "encoded-pass")).thenReturn(true);

		service.updateEmail("new@mail.com", 5, "raw-pass");

		Assertions.assertEquals("new@mail.com", user.getEmail());
		Mockito.verify(repo, Mockito.times(1)).save(user);
	}


	// IS VALID TOKEN
	@Test
	void givenNullTokenExpectNullPointerException() {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		NullPointerException ex = Assertions.assertThrows(NullPointerException.class, () -> {
			service.isValidToken(null);
		});
		assertEquals("A not null token is required", ex.getMessage());
	}

	@Test
	void givenValidTokenExpectTrue() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String validToken = "valid-token";

		PasswordResetInfo info = new PasswordResetInfo(1, "user@mail.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(1);
		user.setPassword("encoded-pass");

		Mockito.when(jwt.decodeUnverified(validToken, PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(jwt.decode(user.getPassword(), validToken, PasswordResetInfo.class)).thenReturn(info);

		boolean result = service.isValidToken(validToken);

		Assertions.assertEquals(true, result);
	}

	@Test
	void givenInvalidToken_whenIsValidToken_thenReturnsFalse() throws Exception {
		UserPrivateRepository repo = Mockito.mock(UserPrivateRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String invalidToken = "invalid-token";

		Mockito.when(jwt.decodeUnverified(invalidToken, PasswordResetInfo.class)).thenThrow(new KnowyTokenException("Invalid token"));

		boolean result = service.isValidToken(invalidToken);

		Assertions.assertEquals(false, result);
	}


	// RESET PASSWORD
	@Test
	void givenMismatchedPasswordsExpectJwtKnowyException() {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		KnowyTokenException ex = assertThrows(KnowyTokenException.class, () -> {
			service.resetPassword("some-token", "VALID.pass1234", "VALID.diffPass1234");
		});
		assertEquals("Passwords do not match", ex.getMessage());
	}

	@Test
	void givenInvalidPasswordFormatExpectPasswordFormatException() {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String invalidPassword = "invalidpassword";

		KnowyPasswordFormatException ex = assertThrows(KnowyPasswordFormatException.class, () -> {
			service.resetPassword("token", invalidPassword, invalidPassword);
		});
		assertEquals("Invalid password format", ex.getMessage());
	}

	@Test
	void givenInvalidTokenExpectJwtKnowyException() throws Exception {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String token = "invalid-token";
		String password = "VALIDpass123.";

		PasswordResetInfo info = new PasswordResetInfo(16, "x@x.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(16);
		user.setPassword("SECRET");

		when(jwt.decodeUnverified(token, PasswordResetInfo.class)).thenReturn(info);
		when(repo.findById(16)).thenReturn(Optional.of(user));
		when(jwt.decode("SECRET", token, PasswordResetInfo.class)).thenThrow(new KnowyTokenException("Invalid or expired token"));

		KnowyTokenException ex = assertThrows(KnowyTokenException.class, () -> {
			service.resetPassword(token, password, password);
		});
		assertEquals("Invalid or expired token", ex.getMessage());
	}

	@Test
	void givenNonExistentUserExpectUserNotFoundException() throws Exception {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(404, "ghost@nowhere.com");

		when(jwt.decodeUnverified("token", PasswordResetInfo.class)).thenReturn(info);
		when(repo.findById(404)).thenReturn(Optional.empty());

		KnowyUserNotFoundException ex = assertThrows(KnowyUserNotFoundException.class, () -> {
			service.resetPassword("token", "VALIDpass123.", "VALIDpass123.");
		});
		assertEquals("User not found with id: 404", ex.getMessage());
	}

	@Test
	void givenValidTokenAndMatchingPasswordsExpectPasswordResetSuccess() throws Exception {
		UserPrivateRepository repo = mock(UserPrivateRepository.class);
		PasswordEncoder encoder = mock(PasswordEncoder.class);
		JwtTools jwt = mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(11, "user@mail.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(11);
		user.setEmail("user@mail.com");
		user.setPassword("OLD");

		when(jwt.decodeUnverified("valid-token", PasswordResetInfo.class)).thenReturn(info);
		when(repo.findById(11)).thenReturn(Optional.of(user));
		when(jwt.decode("OLD", "valid-token", PasswordResetInfo.class)).thenReturn(info);
		when(encoder.encode("VALIDpass123.")).thenReturn("ENCODED");

		service.resetPassword("valid-token", "VALIDpass123.", "VALIDpass123.");

		Mockito.verify(repo).save(Mockito.argThat(saved ->
			saved.getPassword().equals("ENCODED") && saved.getEmail().equals("user@mail.com")
		));
	}


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

}
