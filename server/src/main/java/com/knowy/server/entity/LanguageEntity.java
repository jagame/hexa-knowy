package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
public class LanguageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;

	@ManyToMany(mappedBy = "languages", fetch = FetchType.LAZY)
	private List<PublicUserEntity> publicUsers;
}
