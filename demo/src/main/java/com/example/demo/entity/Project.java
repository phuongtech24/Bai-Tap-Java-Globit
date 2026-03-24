package com.example.demo.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToMany
	@JoinTable(name = "project_person", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> persons;

	@JsonIgnore
	@OneToMany(mappedBy = "project")
	private List<Task> tasks;

	public Project() {
	}

	public Project(UUID id, String code, String name, String description, Company company, List<Person> persons,
			List<Task> tasks) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.company = company;
		this.persons = persons;
		this.tasks = tasks;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
