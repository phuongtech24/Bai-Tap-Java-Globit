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
import com.example.demo.dto.request.CompanyCreateRequest;
import com.example.demo.dto.response.CompanyResponse;
import com.example.demo.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<CompanyResponse>>> searchByPage(
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {

		Page<CompanyResponse> companies = companyService.searchByPage(pageIndex, pageSize);
		return ResponseEntity.ok(ApiResponse.success(companies, "Lấy danh sách công ty thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<CompanyResponse>> create(@RequestBody CompanyCreateRequest companyCreateRequest) {
		CompanyResponse savedCompany = companyService.create(companyCreateRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(savedCompany, "Thêm công ty thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CompanyResponse>> update(@PathVariable UUID id,
			@RequestBody CompanyCreateRequest companyCreateRequest) {
		CompanyResponse savedCompany = companyService.update(id, companyCreateRequest);

		return ResponseEntity.ok(ApiResponse.success(savedCompany, "Cập nhật công ty thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		companyService.delete(id);

		return ResponseEntity.ok(ApiResponse.success(null, "Xóa công ty thành công"));
	}
}