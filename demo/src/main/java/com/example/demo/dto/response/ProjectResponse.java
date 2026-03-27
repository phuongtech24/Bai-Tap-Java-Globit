package com.example.demo.dto.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.entity.Project;

public class ProjectResponse {
	private UUID id;
	private String code;
	private String name;
	private String description;
	private String companyName;
	private List<String> personNames;

	public ProjectResponse() {
	}

	public ProjectResponse(Project entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.companyName = entity.getCompany() != null ? entity.getCompany().getName() : "Chưa có công ty";

			if (entity.getPersons() != null) {
				this.personNames = entity.getPersons().stream().map(person -> person.getFullName())
						.collect(Collectors.toList());
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<String> getPersonNames() {
		return personNames;
	}

	public void setPersonNames(List<String> personNames) {
		this.personNames = personNames;
	}

}