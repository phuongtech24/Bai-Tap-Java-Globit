package com.example.demo.dto.response;

import java.util.UUID;

import com.example.demo.entity.Role;

public class RoleResponse {
	private UUID id;
	private String name;
	private String description;

	public RoleResponse() {
	}

	public RoleResponse(Role entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
}