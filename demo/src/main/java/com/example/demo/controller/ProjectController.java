package com.example.demo.controller;

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
import com.example.demo.dto.request.ProjectCreateRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<ProjectResponse>>> searchByPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Page<ProjectResponse> projects = projectService.searchByPage(page, size);
		return ResponseEntity.ok(ApiResponse.success(projects, "Lấy danh sách thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProjectResponse>> create(@RequestBody ProjectCreateRequest request) {
		ProjectResponse savedProject = projectService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(savedProject, "Thêm dự án thành công"));

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProjectResponse>> update(@PathVariable UUID id,
			@RequestBody ProjectCreateRequest request) {
		ProjectResponse savedProject = projectService.update(id, request);
		return ResponseEntity.ok(ApiResponse.success(savedProject, "Cập nhật dự án thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {

		projectService.delete(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xoá dự án thành công"));
	}

}
