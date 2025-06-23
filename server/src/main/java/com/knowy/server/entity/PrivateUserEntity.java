package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name= "private_user")
@Entity
public class PrivateUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name="password", length=100)
	private String password;

	@Column(name = "email", length=100)
	private String email;
}
