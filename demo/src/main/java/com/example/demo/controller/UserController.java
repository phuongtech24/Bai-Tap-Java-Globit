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

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<User>>> getAll() {
		return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers(), "Lấy danh sách User thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<User>> create(@RequestBody UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(userService.createUser(request), "Tạo User và phân quyền thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<User>> update(@PathVariable UUID id, @RequestBody UserRequest request) {
		return ResponseEntity.ok(ApiResponse.success(userService.updateUser(id, request), "Cập nhật User thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		userService.deleteUser(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa User thành công"));
	}
}