package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.model.UserSecurityDetails;
import jakarta.servlet.http.HttpServletRequest;
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

@Slf4j
@Service
public class UserSecurityDetailsService implements UserDetailsService {

	private final PrivateUserRepository privateUserRepository;
	private final HttpServletRequest httpServletRequest;

	// ! TODO - Change Exception to custom exception extends of Exception

	/**
	 * The constructor
	 *
	 * @param privateUserRepository the privateUserRepository
	 * @param httpServletRequest    the httpServletRequest
	 */
	public UserSecurityDetailsService(PrivateUserRepository privateUserRepository, HttpServletRequest httpServletRequest) {
		this.privateUserRepository = privateUserRepository;
		this.httpServletRequest = httpServletRequest;
	}

	/**
	 * Loads the user details based on the provided email address.
	 * <p>
	 * This method is the implementation of the {@link org.springframework.security.core.userdetails.UserDetailsService}
	 * interface, used by Spring Security during the authentication process. It searches for the user in the database by
	 * email, and if found, returns a {@link UserSecurityDetails} object containing the user's information.
	 * </p>
	 *
	 * @param email the email address of the user to authenticate
	 * @return a {@link UserSecurityDetails} object implementing
	 * {@link org.springframework.security.core.userdetails.UserDetails}, required for the authentication process in
	 * Spring Security
	 * @throws UsernameNotFoundException if no user is found with the given email
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		PrivateUserEntity privateUser = privateUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		return new UserSecurityDetails(privateUser);
	}

	/**
	 * Refreshes the current user's authentication details in the security context.
	 * <p>
	 * This method retrieves the current authenticated user's principal from the {@link SecurityContextHolder}, reloads
	 * the user details from the data source (to reflect any changes), and updates the authentication token in the
	 * security context.
	 * </p>
	 *
	 * @throws UsernameNotFoundException if the current user's username cannot be found in the data source
	 */
	public void refreshUserAuthentication() throws UsernameNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserSecurityDetails userDetails = (UserSecurityDetails) authentication.getPrincipal();

		UserSecurityDetails updateUserDetails = (UserSecurityDetails) loadUserByUsername(userDetails.getUsername());

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(updateUserDetails, authentication.getCredentials(), updateUserDetails.getAuthorities()));
	}

	/**
	 * Automatically authenticates and logs in a user based on their email address.
	 * <p>
	 * This method loads the user details by email, creates an authentication token, sets it in the Spring Security
	 * context, and synchronizes the security context with the current HTTP session to maintain the authenticated
	 * state.
	 * </p>
	 *
	 * @param email the email address of the user to be automatically logged in
	 * @throws UsernameNotFoundException if no user is found with the given email
	 */
	public void autoLoginUserByEmail(String email) throws UsernameNotFoundException {
		UserSecurityDetails updateUserDetails = (UserSecurityDetails) loadUserByUsername(email);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(updateUserDetails, null, updateUserDetails.getAuthorities());

		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authToken);
		SecurityContextHolder.setContext(securityContext);

		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}
}
