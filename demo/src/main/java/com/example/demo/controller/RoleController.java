package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.ApiResponse; // Nhớ import ApiResponse của bạn vào nhé
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Role>>> getAll() {
		return ResponseEntity.ok(ApiResponse.success(roleService.getAllRoles(), "Lấy danh sách Role thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Role>> create(@RequestBody RoleRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(roleService.createRole(request), "Tạo Role thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Role>> update(@PathVariable UUID id, @RequestBody RoleRequest request) {
		return ResponseEntity.ok(ApiResponse.success(roleService.updateRole(id, request), "Cập nhật Role thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		roleService.deleteRole(id);
		// Xóa xong thì truyền null cho phần data
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa Role thành công"));
	}
}