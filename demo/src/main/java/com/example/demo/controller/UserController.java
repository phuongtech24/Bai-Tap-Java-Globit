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
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<UserResponse>>> searchByPage(@RequestParam(defaultValue = "0") int pageIndex,
			@RequestParam(defaultValue = "10") int pageSize) {
		Page<UserResponse> pageData = userService.searchByPage(pageIndex, pageSize);
		return ResponseEntity.ok(ApiResponse.success(pageData, "Lấy danh sách User thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(userService.createUser(request), "Tạo User và phân quyền thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable UUID id, @RequestBody UserRequest request) {
		return ResponseEntity.ok(ApiResponse.success(userService.updateUser(id, request), "Cập nhật User thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		userService.deleteUser(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa User thành công"));
	}
}