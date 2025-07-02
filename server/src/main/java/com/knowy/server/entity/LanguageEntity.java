package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name= "language")
public class LanguageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="id", nullable=false)
	private Integer id;
	@Column (name="name", nullable=false, unique = true, length=20)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "public_user_language",
		joinColumns = @JoinColumn(name = "id_language"),
		inverseJoinColumns = @JoinColumn(name = "id_public_user"))
	private List<PublicUserEntity> publicUsers;
}
