package com.example.demo.dto.request;

import java.util.UUID;

public class DepartmentCreateRequest {
	private String name;
	private String code;
	private UUID companyId;
	private UUID parentId;

	public DepartmentCreateRequest() {

	}

	public DepartmentCreateRequest(String name, String code, UUID companyId, UUID parentId) {
		super();
		this.name = name;
		this.code = code;
		this.companyId = companyId;
		this.parentId = parentId;
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

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyIds(UUID companyId) {
		this.companyId = companyId;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

}
