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
import com.example.demo.dto.response.CountryResponse;
import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<CountryResponse>>> searchByPage(
			@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize) {
		Page<CountryResponse> pageData = countryService.searchByPage(pageIndex, pageSize);
		return ResponseEntity.ok(ApiResponse.success(pageData, "Lấy danh sách Quốc gia thành công"));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<CountryResponse>> create(@RequestBody Country country) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success(countryService.createCountry(country), "Thêm Quốc gia thành công"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CountryResponse>> update(@PathVariable UUID id, @RequestBody Country country) {
		return ResponseEntity
				.ok(ApiResponse.success(countryService.updateCountry(id, country), "Cập nhật Quốc gia thành công"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
		countryService.deleteCountry(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Xóa Quốc gia thành công"));
	}
}