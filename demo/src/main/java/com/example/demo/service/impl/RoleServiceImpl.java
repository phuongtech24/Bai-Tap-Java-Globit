package com.example.demo.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Page<RoleResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return roleRepository.findAll(pageable).map(RoleResponse::new);
	}

	@Override
	public RoleResponse createRole(RoleRequest request) {
		Role role = new Role();
		role.setName(request.getName());
		role.setDescription(request.getDescription());
		Role savedRole = roleRepository.save(role);
		return new RoleResponse(savedRole);
	}

	@Override
	public RoleResponse updateRole(UUID id, RoleRequest request) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Role!", HttpStatus.NOT_FOUND));

		existingRole.setName(request.getName());
		existingRole.setDescription(request.getDescription());

		Role updatedRole = roleRepository.save(existingRole);
		return new RoleResponse(updatedRole);
	}

	@Override
	public void deleteRole(UUID id) {
		roleRepository.deleteById(id);
	}
}