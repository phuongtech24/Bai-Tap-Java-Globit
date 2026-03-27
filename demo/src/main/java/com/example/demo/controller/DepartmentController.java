package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.DepartmentCreateRequest;
import com.example.demo.dto.response.DepartmentResponse;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<DepartmentResponse>>> searchByPage(
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		Page<DepartmentResponse> pageData = departmentService.searchByPage(pageIndex, pageSize);
		return ResponseEntity.ok(ApiResponse.success(pageData, "Lấy danh sách phòng ban thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<DepartmentResponse>> create(@RequestBody DepartmentCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(departmentService.createDepartment(request), "Tạo phòng ban thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponse>> update(@PathVariable UUID id,
			@RequestBody DepartmentCreateRequest request) {
		return ResponseEntity.ok(
				ApiResponse.success(departmentService.updateDepartment(id, request), "Cập nhật phòng ban thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa phòng ban thành công"));
	}

	@GetMapping("/company/{id}")
	public ResponseEntity<ApiResponse<List<DepartmentResponse>>> searchByCompany(@PathVariable UUID id) {
		return ResponseEntity.ok(ApiResponse.success(departmentService.searchByCompany(id),
				"Tìm kiếm phòng ban theo công ty thành công"));
	}
}