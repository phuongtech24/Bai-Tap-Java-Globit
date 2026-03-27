package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

public interface UserService {

	Page<UserResponse> searchByPage(int pageIndex, int pageSize);

	UserResponse createUser(UserRequest request);

	UserResponse updateUser(UUID id, UserRequest request);

	void deleteUser(UUID id);
}