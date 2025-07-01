package com.knowy.server.service;

import com.knowy.server.controller.dto.UserDto;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.repository.PublicUserRepository;
import com.knowy.server.service.exception.AccessException;
import com.knowy.server.service.exception.InvalidUserException;
import com.knowy.server.service.model.PasswordResetJwt;
import com.knowy.server.util.EmailClientService;
import com.knowy.server.util.JwtService;
import com.knowy.server.util.PasswordCheker;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

    private final JwtService jwtService;
    private final EmailClientService emailClientService;
    private final PrivateUserRepository privateUserRepository;
    private final PublicUserRepository publicUserRepository;

    /**
     * The constructor
     *
     * @param jwtService            the jwtService
     * @param emailClientService    the emailClientService
     * @param privateUserRepository the privateUserRepository
     */
    public AccessService(JwtService jwtService, EmailClientService emailClientService, PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository) {
        this.jwtService = jwtService;
        this.emailClientService = emailClientService;
        this.privateUserRepository = privateUserRepository;
        this.publicUserRepository = publicUserRepository;
    }

    // TODO - Change to HttpSession
    public PublicUserEntity registerNewUser(UserDto userDto) throws InvalidUserException {
        if (publicUserRepository.findByNickname(userDto.getUsername()).isPresent()) {
            throw new InvalidUserException("El nombre de usuario ya existe.");
        }

        if (privateUserRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new InvalidUserException("El email ya está en uso.");
        }

        PrivateUserEntity privateUser = new PrivateUserEntity();
        privateUser.setEmail(userDto.getEmail());
        privateUser.setPassword(userDto.getPassword());
        PublicUserEntity publicUser = new PublicUserEntity();
        publicUser.setNickname(userDto.getUsername());

        privateUser.setPublicUserEntity(publicUser);
        publicUser.setPrivateUserEntity(privateUser);

		// FIXME - privateUserRepository.save(privateUser);
        publicUser = publicUserRepository.save(publicUser);

        return publicUser;
    }

    /**
     * Sends a password recovery email to the user associated with the given email address.
     * <p>
     * This method will:
     * <ul>
     *     <li>Find the user by email</li>
     *     <li>Generate and update a new password recovery token</li>
     *     <li>Send an email with the recovery URL (including the token)</li>
     * </ul>
     *
     * @param email           the email address of the user requesting password recovery.
     * @param recoveryBaseUrl the base URL to which the token will be appended (e.g.
     *                        {@code https://example.com/recover-password}). The final URL will be
     *                        {@code recoveryBaseUrl + "?token=" + token}.
     * @throws AccessException if the user is not found or the email fails to send.
     */
    public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl) throws AccessException {
        try {
            PrivateUserEntity user = privateUserRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

            String token = createUserToken(user);

            sendRecoveryToken(user, token, recoveryBaseUrl);
        } catch (UserNotFoundException | MailDispatchException | JwtKnowyException e) {
            throw new AccessException("Failed to send the password reset url to the user's email", e);
        }
    }

    private String createUserToken(PrivateUserEntity user) throws JwtKnowyException {
        PasswordResetJwt passwordResetJwt = new PasswordResetJwt(user.getId(), user.getEmail());
        return jwtService.encode(passwordResetJwt, user.getPassword());
    }

    private void sendRecoveryToken(PrivateUserEntity user, String token, String appUrl) throws
            MailDispatchException {
        String to = user.getEmail();
        String subject = "Tu enlace para recuperar la cuenta de Knowy está aquí";
        String body = tokenBody(token, appUrl);

        emailClientService.sendEmail(to, subject, body);
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

    /**
     * Updates a user's password using a JWT token for verification.
     *
     * <p>This method performs several checks before updating the password:
     * <ul>
     *     <li>Ensures the new password and its confirmation match.</li>
     *     <li>Decodes the provided JWT token to extract the user ID.</li>
     *     <li>Validates the token using the user's current password as the secret.</li>
     *     <li>Prevents reusing the current password.</li>
     * </ul>
     *
     * <p>Once all validations pass, the user's password is updated in the system.</p>
     *
     * @param token           the JWT token used to authorize the password reset. This token must contain the user ID
     *                        and be verifiable using the user's current password.
     * @param password        the new password to set
     * @param confirmPassword the confirmation of the new password; must match {@code password}
     * @throws AccessException if any validation fails, the token is invalid or malformed, or the password update cannot
     *                         be completed
     */
    public void updateUserPassword(String token, String password, String confirmPassword) throws
            AccessException, PasswordFormatException {
        try {
            PasswordCheker.assertPasswordFormatIsRight(password);
            if (!password.equals(confirmPassword)) {
                throw new JwtKnowyException("Passwords do not match");
            }

            PasswordResetJwt passwordResetJwt = jwtService.decodeUnverified(token, PasswordResetJwt.class);
            PrivateUserEntity privateUser = privateUserRepository.findById(passwordResetJwt.getUserId());
            jwtService.decode(privateUser.getPassword(), token, PasswordResetJwt.class);

            privateUser.setPassword(password);
            privateUserRepository.save(privateUser);
        } catch (JwtKnowyException e) {
            throw new AccessException("Failed to decode and verify token", e);
        }
    }

    /**
     * Validates a password reset JWT token by decoding and verifying its signature.
     *
     * <p>The method performs the following steps:
     * <ul>
     *     <li>Decodes the token without verifying the signature to extract the user ID.</li>
     *     <li>Fetches the corresponding user from the database using the extracted ID.</li>
     *     <li>Verifies the token's signature using the user's current password as the secret key.</li>
     * </ul>
     *
     * <p>If all steps succeed, the token is considered valid. Otherwise, the method returns {@code false}.</p>
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is well-formed, matches a known user, and its signature is valid; {@code false}
     * otherwise
     */
    public boolean isValidToken(String token) {
        try {
            PasswordResetJwt passwordResetJwt = jwtService.decodeUnverified(token, PasswordResetJwt.class);
            PrivateUserEntity privateUser = privateUserRepository.findById(passwordResetJwt.getUserId());
            jwtService.decode(privateUser.getPassword(), token, PasswordResetJwt.class);
            return true;
        } catch (JwtKnowyException e) {
            return false;
        }
    }

    public Optional<PrivateUserEntity> authenticateUser(String email, String password) {
        Optional<PrivateUserEntity> foundUser = privateUserRepository.findByEmail(email);

        if (foundUser.isPresent()) {
            PrivateUserEntity user = foundUser.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
