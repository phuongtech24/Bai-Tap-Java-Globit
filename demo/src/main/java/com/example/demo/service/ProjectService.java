package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.ProjectCreateRequest;
import com.example.demo.dto.response.ProjectResponse;

public interface ProjectService {
	Page<ProjectResponse> searchByPage(int pageIndex, int pageSize);

	ProjectResponse create(ProjectCreateRequest request);

	ProjectResponse update(UUID id, ProjectCreateRequest request);

	void delete(UUID id);
}