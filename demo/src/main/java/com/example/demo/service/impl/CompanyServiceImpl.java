package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.CompanyCreateRequest;
import com.example.demo.dto.response.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Department;
import com.example.demo.entity.Person;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	private final CompanyRepository companyRepository;
	private final PersonRepository personRepository;
	private final DepartmentRepository departmentRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository, PersonRepository personRepository,
			DepartmentRepository departmentRepository) {
		this.companyRepository = companyRepository;
		this.personRepository = personRepository;
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Page<CompanyResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<Company> companyPage = companyRepository.findAll(pageable);
		return companyPage.map(CompanyResponse::new);
	}

	@Override
	public CompanyResponse create(CompanyCreateRequest companyCreateRequest) {
		Company company = new Company();
		company.setName(companyCreateRequest.getName());
		List<Department> validDepart = getValidDepartments(companyCreateRequest.getDepartmentIds(), null);
		company.setDepartments(validDepart);
		List<Person> validPerson = getValidPersons(companyCreateRequest.getEmployeeIds(), null);
		company.setEmployees(validPerson);

		Company savedCompany = companyRepository.save(company);
		return new CompanyResponse(savedCompany);
	}

	@Override
	public CompanyResponse update(UUID id, CompanyCreateRequest companyCreateRequest) {
		Company existingCompany = companyRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy công ty", HttpStatus.NOT_FOUND));

		existingCompany.setName(companyCreateRequest.getName());
		List<Department> validDepart = getValidDepartments(companyCreateRequest.getDepartmentIds(), id);
		List<Person> validPerson = getValidPersons(companyCreateRequest.getEmployeeIds(), id);

		existingCompany.setDepartments(validDepart);
		existingCompany.setEmployees(validPerson);

		Company updatedCompany = companyRepository.save(existingCompany);
		return new CompanyResponse(updatedCompany);
	}

	@Override
	public void delete(UUID id) {
		companyRepository.deleteById(id);
	}

	private List<Department> getValidDepartments(List<UUID> departmentIds, UUID currentCompanyId) {
		if (departmentIds == null || departmentIds.isEmpty()) {
			return null;
		}
		List<Department> departments = departmentRepository.findAllById(departmentIds);
		if (departments.size() != departmentIds.size()) {
			throw new AppException("Lỗi: Có chứa ID Phòng ban không tồn tại!", HttpStatus.BAD_REQUEST);
		}
		for (Department dept : departments) {
			if (dept.getCompany() != null) {
				if (currentCompanyId == null || !dept.getCompany().getId().equals(currentCompanyId)) {
					throw new AppException("Lỗi: Phòng ban '" + dept.getName() + "' đã thuộc công ty khác",
							HttpStatus.CONFLICT);
				}
			}
		}
		return departments;
	}

	private List<Person> getValidPersons(List<UUID> personIds, UUID currentCompanyid) {
		if (personIds == null || personIds.isEmpty()) {
			return null;
		}

		List<Person> persons = personRepository.findAllById(personIds);

		if (persons.size() != personIds.size()) {
			throw new AppException("Lỗi: Có chứa ID Person không tồn tại!", HttpStatus.BAD_REQUEST);
		}
		for (Person per : persons) {
			if (per.getCompany() != null) {
				if (currentCompanyid == null || !per.getCompany().getId().equals(currentCompanyid)) {
					throw new AppException("Lỗi: Nhân viên '" + per.getFullName() + "' đã thuộc công ty khác",
							HttpStatus.CONFLICT);
				}
			}
		}
		return persons;
	}
}