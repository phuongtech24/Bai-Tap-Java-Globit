package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.DepartmentCreateRequest;
import com.example.demo.entity.Company;
import com.example.demo.entity.Department;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final CompanyRepository companyRepository;

	public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
		this.departmentRepository = departmentRepository;
		this.companyRepository = companyRepository;

	}

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public Department createDepartment(DepartmentCreateRequest request) {
		Department department = new Department();
		department.setName(request.getName());
		department.setCode(request.getCode());
		Company validCompany = getValidCompany(request.getCompanyId());
		Department validParent = getValidParent(request.getCompanyId(), request.getParentId(), null);
		department.setCompany(validCompany);
		department.setParent(validParent);

		return departmentRepository.save(department);
	}

	public Department updateDepartment(UUID id, DepartmentCreateRequest request) {
		Department existingDepartment = departmentRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy phòng ban", HttpStatus.BAD_REQUEST));
		existingDepartment.setName(request.getName());
		existingDepartment.setCode(request.getCode());

		Company company = getValidCompany(request.getCompanyId());
		Department departmet = getValidParent(request.getCompanyId(), request.getParentId(), id);

		existingDepartment.setCompany(company);
		existingDepartment.setParent(departmet);
		return departmentRepository.save(existingDepartment);
	}

	public void deleteDepartment(UUID id) {
		departmentRepository.deleteById(id);
	}

	public List<Department> searchByCompany(UUID companyId) {
		return departmentRepository.findByCompanyId(companyId);
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
