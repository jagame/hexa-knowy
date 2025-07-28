package com.knowy.server.util;

import com.knowy.server.infrastructure.adapters.repository.entity.PrivateUserEntity;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.application.service.model.UserSecurityDetails;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.UnaryOperator;

@Slf4j
@Service
public class UserSecurityDetailsHelper {

	private final UserDetailsService userDetailsService;
	private final UserPrivateRepository privateUserRepository;
	private final HttpSession httpSession;

	/**
	 * The constructor
	 *
	 * @param privateUserRepository the privateUserRepository
	 * @param httpSession           the httpSession
	 */
	public UserSecurityDetailsHelper(UserDetailsService userDetailsService, UserPrivateRepository privateUserRepository, HttpSession httpSession) {
		this.userDetailsService = userDetailsService;
		this.privateUserRepository = privateUserRepository;
		this.httpSession = httpSession;
	}

	/**
	 * Refreshes the current user's authentication by reloading the user details using the current username (typically
	 * the email).
	 *
	 * <p>This method is useful when the user's data has changed (e.g., roles, permissions)
	 * and needs to be reloaded into the security context without requiring the user to log out and log back in.</p>
	 *
	 * @throws UsernameNotFoundException if the user cannot be found using the current username
	 */
	public void refreshUserAuthentication() throws UsernameNotFoundException {
		refreshAuthentication(user -> (UserSecurityDetails) userDetailsService
			.loadUserByUsername(user.getUsername())
		);
	}

	/**
	 * Refreshes the current user's authentication by reloading the user details using the internal user ID.
	 *
	 * <p>This is a safer alternative to {@link #refreshUserAuthentication()} in cases
	 * where the username (email) may have changed, ensuring the correct user is reloaded based on a stable
	 * identifier.</p>
	 *
	 * @throws UsernameNotFoundException if the user cannot be found using their internal ID
	 */
	public void refreshUserAuthenticationById() throws UsernameNotFoundException {
		refreshAuthentication(user -> (UserSecurityDetails) loadUserById(user.getPublicUser().getId()));
	}

	private void refreshAuthentication(UnaryOperator<UserSecurityDetails> reloadFunction) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserSecurityDetails currentDetails = (UserSecurityDetails) authentication.getPrincipal();

		UserSecurityDetails updatedDetails = reloadFunction.apply(currentDetails);

		Authentication newAuth = new UsernamePasswordAuthenticationToken(
			updatedDetails,
			authentication.getCredentials(),
			updatedDetails.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	private UserDetails loadUserById(int id) throws UsernameNotFoundException {
		PrivateUserEntity privateUser = privateUserRepository.findById(id)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new UserSecurityDetails(privateUser);
	}

	/**
	 * Automatically authenticates and logs in a user based on their email address.
	 * <p>
	 * This method performs the following steps:
	 * <ul>
	 *   <li>Loads user details by the provided email</li>
	 *   <li>Creates an authentication token for the user</li>
	 *   <li>Creates and sets a new {@link org.springframework.security.core.context.SecurityContext} with the authentication</li>
	 *   <li>Binds the security context to the current HTTP session to maintain the authenticated state</li>
	 * </ul>
	 * This allows seamless login without requiring manual input of credentials, commonly used after registration or email verification flows.
	 * </p>
	 *
	 * @param email the email address of the user to be automatically logged in
	 * @throws UsernameNotFoundException if no user is found with the given email
	 */
	public void autoLoginUserByEmail(String email) throws UsernameNotFoundException {
		UsernamePasswordAuthenticationToken userAuthToken = createAuthTokenFromEmail(email);
		SecurityContext securityContext = createSecurityContextWithAuth(userAuthToken);
		bindSecurityContextInSession(securityContext);
	}

	private UsernamePasswordAuthenticationToken createAuthTokenFromEmail(String email) throws UsernameNotFoundException {
		UserSecurityDetails updateUserDetails = (UserSecurityDetails) userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(
			updateUserDetails,
			null,
			updateUserDetails.getAuthorities()
		);
	}

	private SecurityContext createSecurityContextWithAuth(UsernamePasswordAuthenticationToken authToken) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authToken);
		SecurityContextHolder.setContext(securityContext);
		return securityContext;
	}

	private void bindSecurityContextInSession(SecurityContext securityContext) {
		httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}
}
