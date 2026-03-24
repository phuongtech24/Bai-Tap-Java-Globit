package com.example.demo.controller;

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

import com.example.demo.dto.request.CompanyCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Company;
import com.example.demo.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private final CompanyService companyService;

	// Dùng Constructor Injection chuẩn mực, bỏ @Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Company>>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Company> companies = companyService.getAll(pageable);

		return ResponseEntity.ok(ApiResponse.success(companies, "Lấy danh sách công ty thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Company>> create(@RequestBody CompanyCreateRequest companyCreateRequest) {
		Company savedCompany = companyService.create(companyCreateRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(savedCompany, "Thêm công ty thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Company>> update(@PathVariable UUID id,
			@RequestBody CompanyCreateRequest companyCreateRequest) {
		Company savedCompany = companyService.update(id, companyCreateRequest);

		return ResponseEntity.ok(ApiResponse.success(savedCompany, "Cập nhật công ty thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		companyService.delete(id);

		return ResponseEntity.ok(ApiResponse.success(null, "Xóa công ty thành công"));
	}
}