package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.DepartmentCreateRequest;
import com.example.demo.dto.response.DepartmentResponse;

public interface DepartmentService {
	Page<DepartmentResponse> searchByPage(int pageIndex, int pageSize);;

	DepartmentResponse createDepartment(DepartmentCreateRequest request);

	DepartmentResponse updateDepartment(UUID id, DepartmentCreateRequest request);

	void deleteDepartment(UUID id);

	List<DepartmentResponse> searchByCompany(UUID companyId);
}