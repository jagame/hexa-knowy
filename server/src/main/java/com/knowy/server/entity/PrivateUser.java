package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "private_user")
public class PrivateUser {

	@Id
	private Integer id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private PublicUser publicUser;

	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "token", nullable = true, length = 255)
	private String token;


}
