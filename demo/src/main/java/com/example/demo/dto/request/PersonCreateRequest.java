package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PersonCreateRequest {

	private String fullName;
	private String gender;

	// Định dạng để khớp với chuỗi "yyyy-MM-dd" từ Postman gửi lên
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	private String phoneNumber;
	private String address;
	private UUID companyId;

	// ... Constructor không tham số và có tham số ...

	// --- Getters and Setters (Nhớ Generate đầy đủ nhé) ---
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

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}
}