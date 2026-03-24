package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
	private UUID id;

	@Column(nullable = false)
	private String fullName;

	@Column
	private String gender;
	@Column
	private LocalDate birthdate;

	@Column
	private String phoneNumber;

	@Column
	private String address;

	@Column
	private String avatar;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
	private User user;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@JsonIgnore
	@ManyToMany(mappedBy = "persons")
	private List<Project> projects;

	@JsonIgnore
	@OneToMany(mappedBy = "person")
	private List<Task> tasks;

	public Person() {
	}

	public Person(UUID id, String fullName, String gender, LocalDate birthdate, String phoneNumber, String address,
			String avatar, User user, Company company, List<Project> projects, List<Task> tasks) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.avatar = avatar;
		this.user = user;
		this.company = company;
		this.projects = projects;
		this.tasks = tasks;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}