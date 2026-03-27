package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.DepartmentCreateRequest;
import com.example.demo.dto.response.DepartmentResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Department;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final CompanyRepository companyRepository;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
		this.departmentRepository = departmentRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public Page<DepartmentResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return departmentRepository.findAll(pageable).map(DepartmentResponse::new);
	}

	@Override
	public DepartmentResponse createDepartment(DepartmentCreateRequest request) {
		Department department = new Department();
		department.setName(request.getName());
		department.setCode(request.getCode());
		Company validCompany = getValidCompany(request.getCompanyId());
		Department validParent = getValidParent(request.getCompanyId(), request.getParentId(), null);
		department.setCompany(validCompany);
		department.setParent(validParent);

		Department savedDepartment = departmentRepository.save(department);
		return new DepartmentResponse(savedDepartment);
	}

	@Override
	public DepartmentResponse updateDepartment(UUID id, DepartmentCreateRequest request) {
		Department existingDepartment = departmentRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy phòng ban", HttpStatus.BAD_REQUEST));
		existingDepartment.setName(request.getName());
		existingDepartment.setCode(request.getCode());

		Company company = getValidCompany(request.getCompanyId());
		Department departmet = getValidParent(request.getCompanyId(), request.getParentId(), id);

		existingDepartment.setCompany(company);
		existingDepartment.setParent(departmet);

		Department updatedDepartment = departmentRepository.save(existingDepartment);
		return new DepartmentResponse(updatedDepartment);
	}

	@Override
	public void deleteDepartment(UUID id) {
		departmentRepository.deleteById(id);
	}

	@Override
	public List<DepartmentResponse> searchByCompany(UUID companyId) {
		return departmentRepository.findByCompanyId(companyId).stream().map(DepartmentResponse::new)
				.collect(Collectors.toList());
	}

	private Company getValidCompany(UUID companyIds) {
		return companyRepository.findById(companyIds)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy công ty", HttpStatus.BAD_REQUEST));
	}

	private Department getValidParent(UUID companyIds, UUID parentId, UUID departId) {
		if (parentId == null) {
			return null;
		}
		if (departId != null && departId.equals(parentId)) {
			throw new AppException("Lỗi: Không được chọn chính mình làm cha", HttpStatus.BAD_REQUEST);
		}
		Department parent = departmentRepository.findById(parentId)
				.orElseThrow(() -> new AppException("Không tìm thấy phòng ban cha!", HttpStatus.BAD_REQUEST));
		if (!parent.getCompany().getId().equals(companyIds)) {
			throw new AppException("Lỗi dữ liệu: Phòng ban cha phải trực thuộc cùng một công ty!", HttpStatus.CONFLICT);
		}
		return parent;
	}
}