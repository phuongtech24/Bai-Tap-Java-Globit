package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;

public interface TaskService {
	Page<TaskResponse> searchByPage(Pageable pageable, UUID companyId, UUID projectId, UUID personId, TaskStatus status,
			TaskPriority priority, String name);

	TaskResponse create(TaskRequest request);

	TaskResponse update(UUID id, TaskRequest request);

	void delete(UUID id);

	ByteArrayInputStream exportTasksToExcel() throws IOException;
}