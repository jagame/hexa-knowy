package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityDetailsService implements UserDetailsService {

	private final PrivateUserRepository privateUserRepository;

	public UserSecurityDetailsService(PrivateUserRepository privateUserRepository) {
		this.privateUserRepository = privateUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		PrivateUserEntity privateUser = privateUserRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(email));

		return new UserSecurityDetails(privateUser);
	}
}
