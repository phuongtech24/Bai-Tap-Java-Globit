package com.example.demo.dto.response;

import java.util.UUID;

import com.example.demo.entity.Country;

public class CountryResponse {
	private UUID id;
	private String code;
	private String name;

	public CountryResponse() {
	}

	public CountryResponse(Country entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}