package com.example.demo.dto.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.entity.Company;

public class CompanyResponse {
	private UUID id;
	private String name;
	private List<String> departmentNames;
	private List<String> employeeNames;

	public CompanyResponse() {
	}

	public CompanyResponse(Company entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();

			if (entity.getDepartments() != null) {
				this.departmentNames = entity.getDepartments().stream().map(dept -> dept.getName())

						.collect(Collectors.toList());
			}
			if (entity.getEmployees() != null) {
				this.employeeNames = entity.getEmployees().stream().map(emp -> emp.getFullName())

						.collect(Collectors.toList());
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(List<String> departmentNames) {
		this.departmentNames = departmentNames;
	}

	public List<String> getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(List<String> employeeNames) {
		this.employeeNames = employeeNames;
	}
}