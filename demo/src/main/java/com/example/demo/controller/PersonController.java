package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.request.PersonCreateRequest;
import com.example.demo.dto.response.PersonResponse;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<PersonResponse>>> searchByPage(
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		Page<PersonResponse> pageData = personService.searchByPage(pageIndex, pageSize);
		return ResponseEntity.ok(ApiResponse.success(pageData, "Lấy danh sách nhân sự thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<PersonResponse>> create(@RequestBody PersonCreateRequest request) {
		PersonResponse savedPerson = personService.createPerson(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(savedPerson, "Thêm mới nhân sự thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PersonResponse>> update(@PathVariable UUID id,
			@RequestBody PersonCreateRequest request) {
		PersonResponse updatedPerson = personService.updatePerson(id, request);
		return ResponseEntity.ok(ApiResponse.success(updatedPerson, "Cập nhật thông tin nhân sự thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		personService.deletePerson(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa nhân sự thành công"));
	}

	@PostMapping("/{id}/upload-avatar")
	public ResponseEntity<ApiResponse<String>> uploadAvatar(@PathVariable UUID id,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body(ApiResponse.error("Vui lòng chọn một file ảnh!"));
			}
			String avatarUrl = personService.uploadAvatar(id, file);
			return ResponseEntity.ok(ApiResponse.success(avatarUrl, "Upload ảnh thành công!"));
		} catch (IOException e) {
			return ResponseEntity.internalServerError()
					.body(ApiResponse.error("Lỗi khi lưu file ảnh vào máy: " + e.getMessage()));
		}
	}
}