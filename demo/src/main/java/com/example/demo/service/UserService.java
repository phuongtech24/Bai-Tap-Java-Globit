package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(UserRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());

		// Lấy danh sách Role hợp lệ và gán cho User
		List<Role> validRoles = getValidRoles(request.getRoleIds());
		user.setRoles(validRoles);

		return userRepository.save(user);
	}

	public User updateUser(UUID id, UserRequest request) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy User!", HttpStatus.NOT_FOUND));

		existingUser.setUsername(request.getUsername());
		existingUser.setPassword(request.getPassword());

		// Lấy danh sách Role hợp lệ và gán đè lên quyền cũ
		List<Role> validRoles = getValidRoles(request.getRoleIds());
		existingUser.setRoles(validRoles);

		return userRepository.save(existingUser);
	}

	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}

	// Validate Many-to-Many: Chỉ cần check xem ID Role gửi lên có tồn tại thật
	// trong DB không
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