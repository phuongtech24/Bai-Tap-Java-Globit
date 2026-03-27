package com.example.demo.dto.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.entity.User;

public class UserResponse {
	private UUID id;
	private String username;
	private List<String> roleNames;

	public UserResponse() {
	}

	public UserResponse(User entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.username = entity.getUsername();
			
			if(entity.getRoles() != null) {
				this.roleNames = entity.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
}
