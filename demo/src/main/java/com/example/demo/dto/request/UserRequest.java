package com.example.demo.dto.request;

import java.util.List;
import java.util.UUID;

public class UserRequest {
	private String username;
	private String password;
	private List<UUID> roleIds; // Chìa khóa để phân quyền N-N

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UUID> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<UUID> roleIds) {
		this.roleIds = roleIds;
	}
}