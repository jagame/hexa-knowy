package com.knowy.server.infrastructure.adapter.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "private_user")
public class JpaPrivateUserEntity {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "plainPassword", length = 100, nullable = false)
	private String password;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private JpaPublicUserEntity publicUserEntity;
}
