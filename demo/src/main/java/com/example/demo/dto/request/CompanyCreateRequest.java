package com.example.demo.dto.request;

import java.util.List;
import java.util.UUID;

public class CompanyCreateRequest {
	private String name;
	private List<UUID> employeeIds;
	private List<UUID> departmentIds;
	private List<UUID> projectIds;

	public CompanyCreateRequest() {

	}

	public CompanyCreateRequest(String name, List<UUID> employeeIds, List<UUID> departmentIds, List<UUID> projectIds) {
		super();
		this.name = name;
		this.employeeIds = employeeIds;
		this.departmentIds = departmentIds;
		this.projectIds = projectIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UUID> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<UUID> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public List<UUID> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<UUID> departmentIds) {
		this.departmentIds = departmentIds;
	}

	public List<UUID> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<UUID> projectIds) {
		this.projectIds = projectIds;
	}

}
