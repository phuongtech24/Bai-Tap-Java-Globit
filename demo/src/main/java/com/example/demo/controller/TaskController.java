package com.example.demo.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<TaskResponse>>> searchByPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) UUID companyId,
			@RequestParam(required = false) UUID projectId, @RequestParam(required = false) UUID personId,
			@RequestParam(required = false) TaskStatus status, @RequestParam(required = false) TaskPriority priority,
			@RequestParam(required = false) String name) {
		Pageable pageable = PageRequest.of(page, size);
		Page<TaskResponse> pages = taskService.searchByPage(pageable, companyId, projectId, personId, status, priority,
				name);
		return ResponseEntity.ok(ApiResponse.success(pages, "Lấy danh sách thành công"));

	}

	@PostMapping
	public ResponseEntity<ApiResponse<TaskResponse>> create(@RequestBody TaskRequest request) {
		TaskResponse task = taskService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body((ApiResponse.success(task, "Thêm thành công")));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<TaskResponse>> update(@PathVariable UUID id, @RequestBody TaskRequest request) {
		TaskResponse task = taskService.update(id, request);
		return ResponseEntity.ok(ApiResponse.success(task, "Cập nhật thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		taskService.delete(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xoá thành công"));

	}

	@GetMapping("/export-excel")
	public ResponseEntity<Resource> exportExcel() throws IOException {
		String filename = "tasks_report.xlsx";

		InputStreamResource file = new InputStreamResource(taskService.exportTasksToExcel());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(file);
	}
}
