package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskRequest {
	private String name;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startTime;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endTime;

	private TaskPriority priority;

	private TaskStatus status;

	private UUID projectId;

	private UUID personId;

	public TaskRequest() {
	}

	public TaskRequest(String name, String description, LocalDate startTime, LocalDate endTime, TaskPriority priority,
			TaskStatus status, UUID projectId, UUID personId) {
		super();
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		this.status = status;
		this.projectId = projectId;
		this.personId = personId;
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

	public UUID getProjectId() {
		return projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

}
