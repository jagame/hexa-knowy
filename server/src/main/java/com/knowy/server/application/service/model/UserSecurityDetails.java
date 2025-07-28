package com.knowy.server.application.service.model;

import com.knowy.server.infrastructure.adapters.repository.entity.PrivateUserEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {

	private final PrivateUserEntity privateUser;

	public UserSecurityDetails(PrivateUserEntity privateUser) {
		this.privateUser = privateUser;
	}

	public PublicUserEntity getPublicUser() {
		return privateUser.getPublicUserEntity();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return privateUser.getPassword();
	}

	@Override
	public String getUsername() {
		return privateUser.getEmail();
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
		return privateUser.isActive();
	}
}
