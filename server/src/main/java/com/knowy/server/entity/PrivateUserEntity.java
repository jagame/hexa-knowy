package com.knowy.server.entity;

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
public class PrivateUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = 	"email", length = 100, nullable = false)
	private String email;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "public_user_id")
	private PublicUserEntity publicUserEntity;
}
