package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "public_user")
public class PublicUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private PrivateUserEntity privateUser;

	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;
}