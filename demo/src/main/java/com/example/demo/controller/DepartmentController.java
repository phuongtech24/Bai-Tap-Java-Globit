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

import com.example.demo.dto.request.DepartmentCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
		return ResponseEntity
				.ok(ApiResponse.success(departmentService.getAllDepartments(), "Lấy danh sách phòng ban thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody DepartmentCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(departmentService.createDepartment(request), "Tạo phòng ban thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Department>> updateDepartment(@PathVariable UUID id,
			@RequestBody DepartmentCreateRequest request) {
		return ResponseEntity.ok(
				ApiResponse.success(departmentService.updateDepartment(id, request), "Cập nhật phòng ban thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable UUID id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa phòng ban thành công"));
	}

	@GetMapping("/company/{id}")
	public ResponseEntity<ApiResponse<List<Department>>> searchByDepartment(@PathVariable UUID id) {
		return ResponseEntity.ok(ApiResponse.success(departmentService.searchByCompany(id),
				"Tìm kiếm phòng ban theo công ty thành công"));
	}
}