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
@Table(name = "public_user")
public class JpaPublicUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profile_image", referencedColumnName = "id")
	private JpaProfileImageEntity profileImage;

}