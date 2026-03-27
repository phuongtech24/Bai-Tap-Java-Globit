package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.CompanyCreateRequest;
import com.example.demo.dto.response.CompanyResponse;

public interface CompanyService {

	Page<CompanyResponse> searchByPage(int pageIndex, int pageSize);

	CompanyResponse create(CompanyCreateRequest companyCreateRequest);

	CompanyResponse update(UUID id, CompanyCreateRequest companyCreateRequest);

	void delete(UUID id);
}