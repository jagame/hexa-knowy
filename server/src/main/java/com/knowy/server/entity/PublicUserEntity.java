package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "public_user")
public class PublicUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profile_image", referencedColumnName = "id")
	private ProfileImageEntity profileImage;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "public_user_language",
		joinColumns = @JoinColumn(name = "id_public_user"),
		inverseJoinColumns = @JoinColumn(name = "id_language"))
	private Set<LanguageEntity> languages;

	@OneToOne(mappedBy = "publicUserEntity")
	private PrivateUserEntity privateUserEntity;
}