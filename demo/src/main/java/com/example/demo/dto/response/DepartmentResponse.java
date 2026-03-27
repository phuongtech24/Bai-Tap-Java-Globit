package com.example.demo.dto.response;

import java.util.UUID;

import com.example.demo.entity.Department;

public class DepartmentResponse {
	private UUID id;
	private String name;
	private String code;
	private String companyName;
	private String parentName;

	public DepartmentResponse() {
	}

	public DepartmentResponse(Department entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			this.companyName = entity.getCompany() != null ? entity.getCompany().getName() : null;
			this.parentName = entity.getParent() != null ? entity.getParent().getName() : null;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}