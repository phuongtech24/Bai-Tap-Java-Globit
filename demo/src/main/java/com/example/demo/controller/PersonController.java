package com.example.demo.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.dto.request.PersonCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
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

	@PostMapping
	public ResponseEntity<Person> createPerson(@RequestBody PersonCreateRequest request) {
		Person savedPerson = personService.createPerson(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable UUID id, @RequestBody PersonCreateRequest request) {
		Person updatedPerson = personService.updatePerson(id, request);
		return ResponseEntity.ok(updatedPerson);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
		personService.deletePerson(id);
		return ResponseEntity.ok().build(); // Trả về 200 OK, không cần body
	}

	@GetMapping
	public Page<Person> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return personService.getAll(pageable);
	}
}