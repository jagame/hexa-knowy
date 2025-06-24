package com.knowy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity

@Table(name = "public_user")
public class PublicUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 50)
	private String nickname;

	@OneToOne(mappedBy = "publicUser", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private PrivateUser privateUser;

//	@OneToMany(mappedBy = "publicUser")
//	private List<PublicUserMission> missions = new ArrayList<>();
//
//	@OneToMany(mappedBy = "publicUser")
//	private List<PublicUserLesson> lessons = new ArrayList<>();
//
//	@OneToMany(mappedBy = "publicUser")
//	private List<PublicUserOption> selectedOptions = new ArrayList<>();
}
