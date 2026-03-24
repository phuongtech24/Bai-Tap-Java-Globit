package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.PersonCreateRequest;
import com.example.demo.entity.Company;
import com.example.demo.entity.Person;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository personRepository;
	private final CompanyRepository companyRepository;

	public PersonService(PersonRepository personRepository, CompanyRepository companyRepository) {
		this.personRepository = personRepository;
		this.companyRepository = companyRepository;
	}

	private final Path fileStorageLocation = Paths.get("uploads");

	public Page<Person> getAll(Pageable pageable) {
		return personRepository.findAll(pageable);
	}

	public Person createPerson(PersonCreateRequest request) {
		Person person = new Person();
		// Map toàn bộ dữ liệu từ Request sang Entity
		mapRequestToEntity(request, person);

		Company validCompany = getValidCompany(request.getCompanyId());
		person.setCompany(validCompany);

		return personRepository.save(person);
	}

	public Person updatePerson(UUID id, PersonCreateRequest request) {
		Person existingPerson = personRepository.findById(id)
				.orElseThrow(() -> new AppException("Không tìm thấy nhân viên!", HttpStatus.NOT_FOUND));

		// Cập nhật lại toàn bộ thông tin mới
		mapRequestToEntity(request, existingPerson);

		Company validCompany = getValidCompany(request.getCompanyId());
		existingPerson.setCompany(validCompany);

		return personRepository.save(existingPerson);
	}

	// Viết riêng một hàm Map để dùng chung cho đỡ lặp code
	private void mapRequestToEntity(PersonCreateRequest request, Person person) {
		person.setFullName(request.getFullName());
		person.setGender(request.getGender());
		person.setBirthdate(request.getBirthdate());
		person.setPhoneNumber(request.getPhoneNumber());
		person.setAddress(request.getAddress());
	}

	public void deletePerson(UUID id) {
		personRepository.deleteById(id);
	}

	private Company getValidCompany(UUID companyId) {
		if (companyId == null) {
			return null;
		}
		return companyRepository.findById(companyId)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Công ty với ID này!", HttpStatus.BAD_REQUEST));
	}

	public String uploadAvatar(UUID id, MultipartFile file) throws IOException {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy nhân viên", HttpStatus.BAD_REQUEST));
		Path uploadPath = Paths.get("uploads");
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Đặt tên
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path filePath = uploadPath.resolve(fileName);

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		String avatarUrl = "/uploads/" + fileName;
		person.setAvatar(avatarUrl);
		personRepository.save(person);
		return avatarUrl;
	}
}