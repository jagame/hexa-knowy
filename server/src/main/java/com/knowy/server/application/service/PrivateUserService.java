package com.knowy.server.application.service;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.KnowyException;
import com.knowy.server.application.port.gateway.MessageDispatcher;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.persistence.PublicUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.exception.InvalidUserException;
import com.knowy.server.application.service.usecase.login.KnowyUserLoginException;
import com.knowy.server.application.service.usecase.login.LoginCommand;
import com.knowy.server.application.service.usecase.login.LoginUseCase;
import com.knowy.server.application.service.usecase.recovery.SendPasswordRecoveryMessageCommand;
import com.knowy.server.application.service.usecase.recovery.SendPasswordRecoveryMessageUseCase;
import com.knowy.server.application.service.usecase.register.UserSignUpCommand;
import com.knowy.server.application.service.usecase.register.UserSignUpUseCase;
import com.knowy.server.application.service.usecase.update.email.KnowyUserEmailUpdateException;
import com.knowy.server.application.service.usecase.update.email.UpdateUserEmailCommand;
import com.knowy.server.application.service.usecase.update.email.UpdateUserEmailUseCase;
import com.knowy.server.application.service.usecase.update.password.KnowyUserPasswordUpdateException;
import com.knowy.server.application.service.usecase.update.password.UpdateUserPasswordCommand;
import com.knowy.server.application.service.usecase.update.password.UpdateUserPasswordUseCase;

import java.util.Optional;

public class PrivateUserService {

    private final PrivateUserRepository privateUserRepository;
    private final UpdateUserEmailUseCase updateUserEmailUseCase;
    private final LoginUseCase loginUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UserSignUpUseCase userSignUpUseCase;
    private final SendPasswordRecoveryMessageUseCase sendPasswordRecoveryMessageUseCase;

    public PrivateUserService(
            PrivateUserRepository privateUserRepository,
            PublicUserRepository publicUserRepository,
            TokenMapper tokenMapper,
            MessageDispatcher messageDispatcher
    ) {
        this.privateUserRepository = privateUserRepository;
        this.updateUserEmailUseCase = new UpdateUserEmailUseCase(privateUserRepository);
        this.loginUseCase = new LoginUseCase(privateUserRepository, tokenMapper);
        this.updateUserPasswordUseCase = new UpdateUserPasswordUseCase(privateUserRepository, tokenMapper);
        this.userSignUpUseCase = new UserSignUpUseCase(privateUserRepository, publicUserRepository);
        this.sendPasswordRecoveryMessageUseCase = new SendPasswordRecoveryMessageUseCase(privateUserRepository,
                tokenMapper, messageDispatcher);
    }

    public PrivateUser signUpUser(UserSignUpCommand userSignUpCommand)
            throws
            IllegalKnowyPasswordException,
            IllegalKnowyEmailException,
            InvalidUserException,
            KnowyPersistenceException {

        return userSignUpUseCase.execute(userSignUpCommand);
    }

    public void launchRecoveryPasswordProcess(SendPasswordRecoveryMessageCommand sendPasswordRecoveryMessageCommand) throws KnowyException {
        sendPasswordRecoveryMessageUseCase.execute(sendPasswordRecoveryMessageCommand);
    }

    public Optional<PrivateUser> findPrivateUserByEmail(Email email) throws KnowyException {
        return privateUserRepository.findByEmail(email);
    }

    private boolean isEmailRegistered(Email email) throws KnowyException {
        return privateUserRepository.findByEmail(email).isPresent();
    }

    public boolean isTokenRegistered(String token) throws KnowyException {
        return privateUserRepository.findByToken(token).isPresent();
    }

    public Void updateEmail(Email email, String newEmail, String plainPassword)
            throws IllegalKnowyEmailException, IllegalKnowyPasswordException, KnowyUserEmailUpdateException {

        return updateUserEmailUseCase.execute(new UpdateUserEmailCommand(email, newEmail, plainPassword));
    }

    public PrivateUser updateUserPassword(UpdateUserPasswordCommand command) throws IllegalKnowyPasswordException,
            KnowyUserPasswordUpdateException, KnowyPersistenceException {
        return updateUserPasswordUseCase.execute(command);
    }

    public PrivateUser doLogin(LoginCommand loginCommand)
            throws
            KnowyUserNotFoundException,
            IllegalKnowyEmailException,
            IllegalKnowyPasswordException,
            KnowyUserLoginException {

        return loginUseCase.execute(loginCommand);
    }
}
