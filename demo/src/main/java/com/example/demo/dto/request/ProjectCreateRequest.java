package com.example.demo.dto.request;

import java.util.List;
import java.util.UUID;

public class ProjectCreateRequest {
	private String code;
	private String name;
	private String description;

	private UUID companyId;

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

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public List<UUID> getPersonIds() {
		return personIds;
	}

	public void setPersonIds(List<UUID> personIds) {
		this.personIds = personIds;
	}

	private List<UUID> personIds;
}
