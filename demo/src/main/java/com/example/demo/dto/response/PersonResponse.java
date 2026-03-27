package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.entity.Person;

public class PersonResponse {
	private UUID id;
	private String fullName;
	private String gender;
	private LocalDate birthdate;
	private String phoneNumber;
	private String address;
	private String avatar;
	private String companyName;

	public PersonResponse() {
	}

	public PersonResponse(Person entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.fullName = entity.getFullName();
			this.gender = entity.getGender();
			this.birthdate = entity.getBirthdate();
			this.phoneNumber = entity.getPhoneNumber();
			this.address = entity.getAddress();
			this.avatar = entity.getAvatar();
			this.companyName = entity.getCompany() != null ? entity.getCompany().getName() : "Chưa có công ty";
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}