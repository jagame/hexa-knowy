package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCardDTO {
	private String name;
	private String creator;
	private int progress;
	private String action;
	private ArrayList<String> tags;
}
