package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public Page<UserResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<User> userPage = userRepository.findAll(pageable);
		return userPage.map(UserResponse::new);
	}

	@Override
	public UserResponse createUser(UserRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());

		List<Role> validRoles = getValidRoles(request.getRoleIds());
		user.setRoles(validRoles);
		User savedUser = userRepository.save(user);
		return new UserResponse(savedUser);
	}

	@Override
	public UserResponse updateUser(UUID id, UserRequest request) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy User!", HttpStatus.NOT_FOUND));

		existingUser.setUsername(request.getUsername());
		existingUser.setPassword(request.getPassword());

		List<Role> validRoles = getValidRoles(request.getRoleIds());
		existingUser.setRoles(validRoles);
		User updatedUser = userRepository.save(existingUser);
		return new UserResponse(updatedUser);
	}

	@Override
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}

	private List<Role> getValidRoles(List<UUID> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return null;
		}

		List<Role> roles = roleRepository.findAllById(roleIds);

		if (roles.size() != roleIds.size()) {
			throw new AppException("Lỗi: Có chứa ID Role không tồn tại trong hệ thống!", HttpStatus.BAD_REQUEST);
		}

		return roles;
	}
}