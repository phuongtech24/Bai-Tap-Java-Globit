package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.entity.Role;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll(); // Role thường ít nên trả về List luôn, ít khi cần phân trang
	}

	public Role createRole(RoleRequest request) {
		Role role = new Role();
		role.setName(request.getName());
		role.setDescription(request.getDescription());
		return roleRepository.save(role);
	}

	public Role updateRole(UUID id, RoleRequest request) {
		Role existingRole = roleRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Role!", HttpStatus.NOT_FOUND));

		existingRole.setName(request.getName());
		existingRole.setDescription(request.getDescription());

		return roleRepository.save(existingRole);
	}

	public void deleteRole(UUID id) {
		roleRepository.deleteById(id);
	}
}