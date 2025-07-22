package com.knowy.server.service;


import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.exception.InvalidUserEmailException;
import com.knowy.server.service.exception.InvalidUserPasswordFormatException;
import com.knowy.server.service.exception.UnchangedEmailException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.service.model.PasswordResetInfo;
import com.knowy.server.util.JwtTools;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.PasswordFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class PrivateUserServiceTest {

// CREATE
	@Test
	void givenExistsEmailExpectAlreadyExistException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		Mockito.when(repo.findByEmail("used@mail.com")).thenReturn(Optional.of(new PrivateUserEntity()));

		Assertions.assertThrows(InvalidUserEmailException.class, () -> {
			service.create("used@mail.com", "123ACabc?¿", new PublicUserEntity());
		});
	}

	@Test
	void givenInvalidPasswordExpectInvalidPasswordFormatException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		Mockito.when(repo.findByEmail("new@mail.com")).thenReturn(Optional.empty());

		Assertions.assertThrows(InvalidUserPasswordFormatException.class, () -> {
			service.create("new@mail.com", "invalidpassword", new PublicUserEntity());
		});
	}

	@Test
	void givenValidUserAndPasswordExpectCreatedUserSuccessfully() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		String email = "valid@mail.com";
		String password = "ValidPass123.";
		String encodedPassword = "encoded-password";
		PublicUserEntity publicUser = new PublicUserEntity();

		Mockito.when(repo.findByEmail(email)).thenReturn(Optional.empty());
		Mockito.when(encoder.encode(password)).thenReturn(encodedPassword);

		PrivateUserEntity savedUser = new PrivateUserEntity();
		savedUser.setEmail(email);
		savedUser.setPassword(encodedPassword);
		savedUser.setPublicUserEntity(publicUser);
		Mockito.when(repo.save(Mockito.any())).thenReturn(savedUser);

		PrivateUserEntity result = service.create(email, password, publicUser);

		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any());
		Assertions.assertEquals(email, result.getEmail());
		Assertions.assertEquals(encodedPassword, result.getPassword());
		Assertions.assertEquals(publicUser, result.getPublicUserEntity());
	}


	// UPDATE EMAIL
	@Test
	void givenSameEmailExpectUnchangedEmailException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail("same@mail.com");

		Mockito.when(repo.findById(7)).thenReturn(Optional.of(user));

		UnchangedEmailException ex = Assertions.assertThrows(UnchangedEmailException.class, () ->
			service.updateEmail("same@mail.com", 7, "irrelevant")
		);
		Assertions.assertEquals("Email must be different from the current one.", ex.getMessage());
	}

	@Test
	void givenExistingEmailExpectInvalidUserEmailException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail("old@mail.com");

		Mockito.when(repo.findById(3)).thenReturn(Optional.of(user));
		Mockito.when(repo.findByEmail("used@mail.com")).thenReturn(Optional.of(new PrivateUserEntity()));

		InvalidUserEmailException ex = Assertions.assertThrows(InvalidUserEmailException.class, () ->
			service.updateEmail("used@mail.com", 3, "password")
		);
		Assertions.assertEquals("The provided email is already associated with an existing account.", ex.getMessage());
	}

	@Test
	void givenInvalidUserIdThrowsUserNotFoundException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		Mockito.when(repo.findById(404)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.updateEmail("any@mail.com", 404, "any-pass")
		);
		Assertions.assertEquals("User not found with id: 404", ex.getMessage());
	}

	@Test   //UpdateEmail Happy Path
	void givenValidEmailAndCorrectPasswordExpectEmailUpdated() throws Exception {
		// Mocks
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
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
		Mockito.verify(repo,Mockito.times(1)).save(user);
	}


//RESET PASSWORD
	@Test
	void givenIncorrectPasswordsExpectJwtKnowyException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);
		JwtKnowyException ex = Assertions.assertThrows(JwtKnowyException.class, () ->
			service.resetPassword("any-token", "VALIDpassword123.", "VALIDdifferent123.")
		);
		Assertions.assertEquals("Passwords do not match", ex.getMessage());
	}

	@Test
	void givenInvalidPasswordFormatExpectPasswordFormatException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordFormatException ex = Assertions.assertThrows(PasswordFormatException.class, () ->
			service.resetPassword("any-token", "invalidpassword1", "invalidpassword2")
		);
		Assertions.assertEquals("Invalid password format", ex.getMessage());
	}

	@Test
	void givenTokenWithUnknownUserIdExpectUserNotFoundException() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(404, "ghost@mail.com");
		Mockito.when(jwt.decodeUnverified("valid-token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(404)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.resetPassword("valid-token", "VALIDpass123.", "VALIDpass123.")
		);
		Assertions.assertEquals("User not found with id: 404", ex.getMessage());
	}

	@Test
	void givenInvalidTokenExpectJwtKnowyExceptionFromDecodeUnverified() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		Mockito.doThrow(new JwtKnowyException("Token inválido"))
			.when(jwt).decodeUnverified("bad-token", PasswordResetInfo.class);

		JwtKnowyException ex = Assertions.assertThrows(JwtKnowyException.class, () ->
			service.resetPassword("bad-token", "VALIDpass123.", "VALIDpass123.")
		);
		Assertions.assertEquals("Token inválido", ex.getMessage());
	}

	@Test
	void givenValidTokenAndMatchingPasswordsExpectPasswordResetSuccess() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(11, "user@mail.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(11);
		user.setEmail("user@mail.com");
		user.setPassword("OLD");

		Mockito.when(jwt.decodeUnverified("valid-token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(11)).thenReturn(Optional.of(user));
		Mockito.when(jwt.decode("OLD", "valid-token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(encoder.encode("VALIDpass123.")).thenReturn("ENCODED");

		service.resetPassword("valid-token", "VALIDpass123.", "VALIDpass123.");

		Mockito.verify(repo).save(Mockito.argThat(saved ->
			saved.getPassword().equals("ENCODED") && saved.getEmail().equals("user@mail.com")
		));
	}


// VERIFY PASSWORD RESET TOKEN
	@Test
	void givenInvalidTokenExpectJwtKnowyExceptionFromDecodeUnverifiedResetToken() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		Mockito.doThrow(new JwtKnowyException("Decoding failed")).when(jwt).decodeUnverified("bad-token", PasswordResetInfo.class);
		JwtKnowyException ex = Assertions.assertThrows(JwtKnowyException.class, () ->
			service.verifyPasswordResetToken("bad-token")
		);
		Assertions.assertEquals("Decoding failed", ex.getMessage());
	}

	@Test
	void givenUserNotFoundExpectExceptionFromVerifyPasswordResetToken() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(42, "ghost@mail.com");
		Mockito.when(jwt.decodeUnverified("valid-token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(42)).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.verifyPasswordResetToken("valid-token")
		);
		Assertions.assertEquals("User not found with id: 42", ex.getMessage());
	}

	@Test
	void givenTokenFailsOnDecodeExpectJwtKnowyException() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(7, "user@mail.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(7);
		user.setEmail("user@mail.com");
		user.setPassword("hashed");

		Mockito.when(jwt.decodeUnverified("token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(7)).thenReturn(Optional.of(user));
		Mockito.doThrow(new JwtKnowyException("Decode error")).when(jwt).decode("hashed", "token", PasswordResetInfo.class);

		JwtKnowyException ex = Assertions.assertThrows(JwtKnowyException.class, () ->
			service.verifyPasswordResetToken("token")
		);
		Assertions.assertEquals("Decode error", ex.getMessage());
	}

	@Test
	void givenValidToken_expectPrivateUserReturned() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwt);

		PasswordResetInfo info = new PasswordResetInfo(3, "user@mail.com");
		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(3);
		user.setEmail("user@mail.com");
		user.setPassword("hashed");

		Mockito.when(jwt.decodeUnverified("token", PasswordResetInfo.class)).thenReturn(info);
		Mockito.when(repo.findById(3)).thenReturn(Optional.of(user));
		Mockito.when(jwt.decode("hashed", "token", PasswordResetInfo.class)).thenReturn(info);

		PrivateUserEntity result = service.verifyPasswordResetToken("token");

		Assertions.assertEquals("user@mail.com", result.getEmail());
		Assertions.assertEquals("hashed", result.getPassword());
	}


// IS VALID TOKEN
	@Test
	void givenInvalidTokenExpectFalse() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = Mockito.spy(new PrivateUserService(repo, encoder, jwt));

		Mockito.doThrow(new JwtKnowyException("Invalid")).when(service).verifyPasswordResetToken("invalid-token");

		boolean result = service.isValidToken("invalid-token");

		Assertions.assertFalse(result);
	}

	@Test //Happy Path
	void givenValidToken_expectTrue() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserService service = Mockito.spy(new PrivateUserService(repo, encoder, jwt));

		// Mock: No lanza excepción
		Mockito.doReturn(new PrivateUserEntity()).when(service).verifyPasswordResetToken("valid-token");

		boolean result = service.isValidToken("valid-token");

		Assertions.assertTrue(result);
	}


// SAVE
	@Test
	void givenRepoThrowsException_expectServiceAlsoThrows() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);
		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail("error@mail.com");

		Mockito.when(repo.save(user)).thenThrow(new RuntimeException("Database failure"));

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> {
			new PrivateUserService(repo, encoder, jwt).save(user);
		});
		Assertions.assertEquals("Database failure", ex.getMessage());

		Mockito.verify(repo, Mockito.times(1)).save(user);
	}

	@Test  //Happy Path
	void givenValidUser_expectSavedAndReturnedCorrectly() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwt = Mockito.mock(JwtTools.class);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setEmail("user@mail.com");

		Mockito.when(repo.save(user)).thenReturn(user);

		PrivateUserEntity result = new PrivateUserService(repo, encoder, jwt).save(user);

		Mockito.verify(repo, Mockito.times(1)).save(user);
		Mockito.verify(repo).save(Mockito.same(user));
		Assertions.assertEquals(user, result);
		Assertions.assertSame(user, result);
	}


// GET PRIVATE USER BY EMAIL
	@Test
	void givenNonExistingEmailThrowsUserNotFoundException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		Mockito.when(repo.findByEmail("missing@mail.com")).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.getPrivateUserByEmail("missing@mail.com")
		);
		Assertions.assertEquals("User not found", ex.getMessage());
	}

	@Test //Happy Path
	void givenExistingEmailExpectUserReturnedInGetPrivateUser() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		PrivateUserEntity expectedUser = new PrivateUserEntity();
		expectedUser.setEmail("found@mail.com");

		Mockito.when(repo.findByEmail("found@mail.com")).thenReturn(Optional.of(expectedUser));

		PrivateUserEntity result = service.getPrivateUserByEmail("found@mail.com");

		Assertions.assertEquals(expectedUser, result);
	}


//	CREATE RECOVERY PASSWORD EMAIL
	@Test
	void givenInvalidEmailExpectUserNotFoundException() {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		Mockito.when(repo.findByEmail("nonexisting@mail.com")).thenReturn(Optional.empty());

		UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () ->
			service.createRecoveryPasswordEmail("nonexisting@mail.com", "https://recover.com")
		);
		Assertions.assertEquals("The user with email " + "nonexisting@mail.com" +" was not found", ex.getMessage());
	}

	@Test
	void givenJwtEncodingFailsThrowsJwtKnowyException() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(5);
		user.setEmail("irrelevant@mail.com");
		user.setPassword("hashed-pass");

		Mockito.when(repo.findByEmail("irrelevant@mail.com")).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq("hashed-pass"))).thenThrow(new JwtKnowyException("Error generating token"));

		JwtKnowyException ex = Assertions.assertThrows(JwtKnowyException.class, () ->
			service.createRecoveryPasswordEmail("irrelevant@mail.com", "http://url")
		);
		Assertions.assertEquals("Error generating token", ex.getMessage());
	}

	@Test
	void givenValidEmailExpectMailMessageCreated() throws Exception {
		PrivateUserRepository repo = Mockito.mock(PrivateUserRepository.class);
		PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
		JwtTools jwtTools = Mockito.mock(JwtTools.class);
		PrivateUserService service = new PrivateUserService(repo, encoder, jwtTools);

		PrivateUserEntity user = new PrivateUserEntity();
		user.setId(10);
		user.setEmail("valid@mail.com");
		user.setPassword("hashed-pass");

		Mockito.when(repo.findByEmail("valid@mail.com")).thenReturn(Optional.of(user));
		Mockito.when(jwtTools.encode(Mockito.any(), Mockito.eq("hashed-pass"))).thenReturn("mocked-token");

		MailMessage result = service.createRecoveryPasswordEmail("valid@mail.com", "http://reset-url.com");
		Assertions.assertEquals("valid@mail.com", result.to());
		Assertions.assertTrue(result.body().contains("mocked-token"));
		Assertions.assertTrue(result.subject().contains("recuperar"));
	}

}
