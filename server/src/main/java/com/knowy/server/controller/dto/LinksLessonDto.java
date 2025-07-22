package com.knowy.server.controller.dto;

import com.knowy.server.entity.DocumentationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksLessonDto {
	private String linkTitle;
	private String linkUrl;
	private LinkType type;
	private String fileName;

	public static List<LinksLessonDto> fromEntities(Collection<DocumentationEntity> docs) {
		return docs.stream()
			.map(LinksLessonDto::fromEntity)
			.distinct()
			.toList();
	}

	public static LinksLessonDto fromEntity(DocumentationEntity doc) {
		boolean isExternal = doc.getLink().startsWith("http");
		String fileName = (!isExternal && doc.getLink().contains("/"))
			? doc.getLink().substring(doc.getLink().lastIndexOf("/") + 1)
			: null;

		return new LinksLessonDto(
			doc.getTitle(),
			doc.getLink(),
			isExternal ? LinksLessonDto.LinkType.EXTERNAL : LinksLessonDto.LinkType.DOCUMENT,
			fileName
		);
	}

	public enum LinkType {
		EXTERNAL,
		DOCUMENT
	}
}




