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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String code;

	public Department() {

	}

	public Department(UUID id, String name, String code, Department parent, List<Department> children,
			Company company) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.parent = parent;
		this.children = children;
		this.company = company;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Department parent;

	@OneToMany(mappedBy = "parent")
	private List<Department> children;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

}
