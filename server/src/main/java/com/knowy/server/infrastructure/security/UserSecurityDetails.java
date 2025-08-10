package com.knowy.server.infrastructure.security;

import com.knowy.server.domain.User;
import com.knowy.server.domain.UserPrivate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {

	private final UserPrivate userPrivate;

	public UserSecurityDetails(UserPrivate userPrivate) {
		this.userPrivate = userPrivate;
	}

	public User getUser() {
		return userPrivate.cropToUser();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return userPrivate.password();
	}

	@Override
	public String getUsername() {
		return userPrivate.email();
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return userPrivate.active();
	}
}
