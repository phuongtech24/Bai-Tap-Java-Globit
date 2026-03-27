package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;

public interface RoleService {
	Page<RoleResponse> searchByPage(int pageIndex, int pageSize);

	RoleResponse createRole(RoleRequest request);

	RoleResponse updateRole(UUID id, RoleRequest request);

	void deleteRole(UUID id);
}