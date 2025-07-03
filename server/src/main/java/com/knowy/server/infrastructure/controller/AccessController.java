package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.ProfileImage;
import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.KnowyException;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.application.service.exception.InvalidUserException;
import com.knowy.server.application.service.usecase.login.KnowyUserLoginException;
import com.knowy.server.application.service.usecase.login.LoginCommand;
import com.knowy.server.application.service.usecase.recovery.SendPasswordRecoveryMessageCommand;
import com.knowy.server.application.service.usecase.register.UserSignUpCommand;
import com.knowy.server.application.service.usecase.update.password.KnowyUserPasswordUpdateException;
import com.knowy.server.application.service.usecase.update.password.UpdateUserPasswordCommand;
import com.knowy.server.infrastructure.controller.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;


@Controller
@Slf4j
public class AccessController {

    public static final String SESSION_LOGGED_USER = "loggedUser";
    private static final String ERROR_ATTRIBUTE = "error";
    private final PrivateUserService privateUserService;
    private final TokenMapper tokenMapper;

    public AccessController(PrivateUserService privateUserService, TokenMapper tokenMapper) {
        this.privateUserService = privateUserService;
        this.tokenMapper = tokenMapper;
    }

    @GetMapping(Endpoint.REGISTER)
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return Template.REGISTER;
    }

    @PostMapping(Endpoint.REGISTER)
    public String procesarFormulario(@Valid @ModelAttribute UserDto user, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return Template.REGISTER;
        }

        try {
            privateUserService.signUpUser(new UserSignUpCommand(
                    user.getUsername(),
                    ProfileImage.DEFAULT,
                    user.getEmail(),
                    user.getPassword()
            ));

            return Redirect.TO_HOME;
        } catch (InvalidUserException | IllegalKnowyPasswordException | IllegalKnowyEmailException e) {
            model.addAttribute("user", user);
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());

            return Template.REGISTER;
        } catch (KnowyPersistenceException e) {
            model.addAttribute("user", user);
            model.addAttribute(ERROR_ATTRIBUTE,
                    "An unexpected error occurs. The error UUID was %s".formatted(e.errorUUID()));

            return Template.REGISTER;
        }
    }

    @GetMapping(Endpoint.LOGIN)
    public String viewLogin(Model model) {
        LoginFormDto loginForm = new LoginFormDto();
        model.addAttribute("loginForm", loginForm);

        return Template.LOGIN;
    }

    @PostMapping(Endpoint.LOGIN)
    public String postLogin(@ModelAttribute("loginForm") LoginFormDto login, Model model, HttpSession session) {
        var userLogin = new LoginCommand(login.getEmail(), login.getPassword());

        try {
            PrivateUser user = privateUserService.doLogin(userLogin);

            return loginSuccess(session, user);
        } catch (KnowyUserNotFoundException | IllegalKnowyEmailException e) {
            return loginError(model, "No user with email %s was found".formatted(login.getEmail()));
        } catch (IllegalKnowyPasswordException e) {
            return loginError(model, "Invalid password for user %s".formatted(login.getEmail()));
        } catch (KnowyUserLoginException e) {
            return loginError(model,
                    "Unexpected error trying to login. The error UUID was %s".formatted(e.errorUUID()));
        }
    }

    private String loginSuccess(HttpSession session, PrivateUser user) {
        session.setAttribute(SESSION_LOGGED_USER, new SessionUser(user));
        log.info("Login de usuario {} correcto", user.id());

        return Redirect.TO_HOME;
    }

    private String loginError(Model model, String errorMessage) {
        model.addAttribute("loginError", errorMessage);
        model.addAttribute("loginForm", new LoginFormDto());

        return Template.LOGIN;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return Redirect.TO_ROOT;
    }

    /**
     * Handles GET requests to display the plainPassword change email form.
     *
     * @param model the model to which the email form DTO is added
     * @return the name of the view for the plainPassword change email page
     */
    @GetMapping(Endpoint.PASSWORD_CHANGE_EMAIL)
    public String passwordChangeEmail(Model model) {
        model.addAttribute("emailForm", new UserEmailFormDto());

        return Template.PASSWORD_CHANGE_EMAIL;
    }

    /**
     * Handles the POST request to initiate a plainPassword change by sending a recovery email.
     *
     * @param emailDto           the form object containing the user's email address
     * @param redirectAttributes attributes used to pass flash messages during redirect
     * @param httpServletRequest the HTTP servlet request object, used to build the plainPassword change URL
     * @return a redirect string to either the login page on success or back to the email form on failure
     */
    @PostMapping(Endpoint.PASSWORD_CHANGE_EMAIL)
    public String passwordChangeEmail(
            @ModelAttribute("emailForm") UserEmailFormDto emailDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest
    ) {
        try {
            var email = new Email(emailDto.getEmail());
            privateUserService.launchRecoveryPasswordProcess(new SendPasswordRecoveryMessageCommand(
                    email, getPasswordChangeURI(httpServletRequest))
            );

            return Redirect.TO_LOGIN;
        } catch (KnowyException e) {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, e.getMessage());

            return Redirect.TO_PASSWORD_CHANGE_EMAIL;
        }
    }


    private URI getPasswordChangeURI(HttpServletRequest httpServletRequest) {
        return URI.create(getDomainUrl(httpServletRequest) + Endpoint.PASSWORD_CHANGE);
    }

    private String getDomainUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        return scheme + "://" + serverName + ":" + serverPort;
    }

    /**
     * Handles GET requests to display the plainPassword change form if the token is valid.
     *
     * @param token the token used to verify the plainPassword change request
     * @param model the model to which the token and plainPassword form DTO are added
     * @return the name of the plainPassword change view if the token is registered, otherwise redirects to the home
     * page
     */
    @GetMapping(Endpoint.PASSWORD_CHANGE)
    public String passwordChange(@RequestParam String token, Model model) {
        if (!tokenMapper.isValid(token)) {
            return Redirect.TO_ROOT;
        }

        model.addAttribute("token", token);
        model.addAttribute("passwordForm", new UserPasswordFormDto());

        return Template.PASSWORD_CHANGE;

    }

    /**
     * Handles POST requests to update a user's plainPassword as part of a plainPassword reset flow.
     *
     * <p>This endpoint expects a valid JWT token and a form containing the new plainPassword and its confirmation.
     * If the token is valid and all validations pass, the user's plainPassword is updated and the user is redirected
     * to the
     * login page.</p>
     *
     * <p>In case of failure (e.g., invalid token, mismatched passwords, or attempt to reuse the old plainPassword),
     * the user is still redirected to the login page, and an error message can be passed via
     * {@link RedirectAttributes}.</p>
     *
     * @param token               the JWT token used to authorize the plainPassword change request
     * @param userPasswordFormDto the form DTO containing the new plainPassword and its confirmation
     * @param redirectAttributes  attributes used to pass query parameters or flash messages during redirection
     * @return a redirect string to the login page, whether the operation succeeds or fails
     */
    @PostMapping(Endpoint.PASSWORD_CHANGE)
    public String passwordChange(
            @RequestParam("token") String token,
            @ModelAttribute("passwordForm") UserPasswordFormDto userPasswordFormDto,
            RedirectAttributes redirectAttributes
    ) {
        try {
            privateUserService.updateUserPassword(
                    new UpdateUserPasswordCommand(userPasswordFormDto.getPassword(), token)
            );
            log.info("User plainPassword updated");

            return Redirect.TO_LOGIN;
        } catch (KnowyUserPasswordUpdateException | KnowyPersistenceException e) {
            redirectAttributes.addAttribute(ERROR_ATTRIBUTE, "Se ha producido un error al actualizar la contraseña");
            log.error("Failed to update user plainPassword", e);

            return Redirect.TO_LOGIN;
        } catch (IllegalKnowyPasswordException e) {
            redirectAttributes.addAttribute(ERROR_ATTRIBUTE, """
                    Formato de contraseña inválido. Debe tener al menos:
                    - 8 caracteres
                    - 1 mayúscula, 1 minúscula
                    - 1 número y 1 símbolo
                    - Sin espacios
                    """);
            log.error("Invalid plainPassword format", e);

            return Redirect.TO_PASSWORD_CHANGE + "?token=" + token;
        }
    }

    private static class Endpoint {
        private static final String HOME = "/home";
        private static final String LOGIN = "/login";
        private static final String PASSWORD_CHANGE = "/plainPassword-change";
        private static final String PASSWORD_CHANGE_EMAIL = "/plainPassword-change/email";
        private static final String REGISTER = "/register";
        private static final String ROOT = "/";
    }

    private static class Redirect {
        private static final String TO_HOME = to(Endpoint.HOME);
        private static final String TO_LOGIN = to(Endpoint.LOGIN);
        private static final String TO_PASSWORD_CHANGE = to(Endpoint.PASSWORD_CHANGE);
        private static final String TO_PASSWORD_CHANGE_EMAIL = to(Endpoint.PASSWORD_CHANGE_EMAIL);
        private static final String TO_ROOT = to(Endpoint.ROOT);

        private static String to(String endpoint) {
            return "redirect:%s".formatted(endpoint);
        }
    }

    private static class Template {
        private static final String PASSWORD_CHANGE = "pages/access/plainPassword-change";
        private static final String PASSWORD_CHANGE_EMAIL = "pages/access/plainPassword-change-email";
        private static final String REGISTER = "pages/access/register";
        private static final String LOGIN = "pages/access/login";
    }
}