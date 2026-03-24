package com.example.demo.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
	private UUID id;

	@Column
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "company")
	private List<Person> employees;

	@JsonIgnore
	@OneToMany(mappedBy = "company")
	private List<Department> departments;

	@JsonIgnore
	@OneToMany(mappedBy = "company")
	private List<Project> projects;

	public Company() {
	}

	public Company(UUID id, String name, List<Person> employees, List<Department> departments, List<Project> projects) {
		super();
		this.id = id;
		this.name = name;
		this.employees = employees;
		this.departments = departments;
		this.projects = projects;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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

	public List<Person> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Person> employees) {
		this.employees = employees;
	}

}
