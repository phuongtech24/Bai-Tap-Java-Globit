package com.example.demo.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.PersonCreateRequest;
import com.example.demo.dto.response.PersonResponse;

public interface PersonService {
	Page<PersonResponse> searchByPage(int pageIndex, int pageSize);

	PersonResponse createPerson(PersonCreateRequest request);

	PersonResponse updatePerson(UUID id, PersonCreateRequest request);

	void deletePerson(UUID id);

	String uploadAvatar(UUID id, MultipartFile file) throws IOException;
}