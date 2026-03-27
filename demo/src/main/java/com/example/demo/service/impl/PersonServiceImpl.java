package com.example.demo.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.PersonCreateRequest;
import com.example.demo.dto.response.PersonResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Person;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;
	private final CompanyRepository companyRepository;

	public PersonServiceImpl(PersonRepository personRepository, CompanyRepository companyRepository) {
		this.personRepository = personRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public Page<PersonResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<Person> personPage = personRepository.findAll(pageable);
		return personPage.map(PersonResponse::new);
	}

	@Override
	public PersonResponse createPerson(PersonCreateRequest request) {
		Person person = new Person();
		mapRequestToEntity(request, person);

		Company validCompany = getValidCompany(request.getCompanyId());
		person.setCompany(validCompany);

		Person savedPerson = personRepository.save(person);
		return new PersonResponse(savedPerson);
	}

	@Override
	public PersonResponse updatePerson(UUID id, PersonCreateRequest request) {
		Person existingPerson = personRepository.findById(id)
				.orElseThrow(() -> new AppException("Không tìm thấy nhân viên!", HttpStatus.NOT_FOUND));

		mapRequestToEntity(request, existingPerson);

		Company validCompany = getValidCompany(request.getCompanyId());
		existingPerson.setCompany(validCompany);

		Person updatedPerson = personRepository.save(existingPerson);
		return new PersonResponse(updatedPerson);
	}

	@Override
	public void deletePerson(UUID id) {
		personRepository.deleteById(id);
	}

	@Override
	public String uploadAvatar(UUID id, MultipartFile file) throws IOException {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy nhân viên", HttpStatus.NOT_FOUND));
		Path uploadPath = Paths.get("uploads");
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path filePath = uploadPath.resolve(fileName);

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		String avatarUrl = "/uploads/" + fileName;
		person.setAvatar(avatarUrl);
		personRepository.save(person);
		return avatarUrl;
	}

	private void mapRequestToEntity(PersonCreateRequest request, Person person) {
		person.setFullName(request.getFullName());
		person.setGender(request.getGender());
		person.setBirthdate(request.getBirthdate());
		person.setPhoneNumber(request.getPhoneNumber());
		person.setAddress(request.getAddress());
	}

	private Company getValidCompany(UUID companyId) {
		if (companyId == null) {
			return null;
		}
		return companyRepository.findById(companyId)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Công ty với ID này!", HttpStatus.BAD_REQUEST));
	}
}