package com.example.demo.dto.response;

import java.time.LocalDate; // (Hoặc Date tùy entity của bạn)
import java.util.UUID;

import com.example.demo.entity.Task;
import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;

public class TaskResponse {
	private UUID id;
	private String name;
	private String description;
	private LocalDate startTime;
	private LocalDate endTime;
	private TaskPriority priority;
	private TaskStatus status;

	private String projectName;
	private String personName;

	public TaskResponse() {
	}

	public TaskResponse(Task entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.startTime = entity.getStartTime();
			this.endTime = entity.getEndTime();
			this.priority = entity.getPriority();
			this.status = entity.getStatus();

			this.projectName = entity.getProject() != null ? entity.getProject().getName() : "Không có dự án";
			this.personName = entity.getPerson() != null ? entity.getPerson().getFullName() : "Chưa phân công";
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}