package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ProjectCreateRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.exception.AppException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final PersonRepository personRepository;
	private final CompanyRepository companyRepository;

	public ProjectServiceImpl(ProjectRepository projectRepository, PersonRepository personRepository,
			CompanyRepository companyRepository) {
		this.projectRepository = projectRepository;
		this.personRepository = personRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public Page<ProjectResponse> searchByPage(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return projectRepository.findAll(pageable).map(ProjectResponse::new);
	}

	@Override
	public ProjectResponse create(ProjectCreateRequest request) {
		Company company = companyRepository.findById(request.getCompanyId())
				.orElseThrow(() -> new AppException("Không tìm thấy công ty nào", HttpStatus.NOT_FOUND));

		List<Person> persons = personRepository.findAllById(request.getPersonIds());
		validatePersonsBelongToCompany(company.getId(), persons);

		Project project = new Project();
		project.setCode(request.getCode());
		project.setCompany(company);
		project.setDescription(request.getDescription());
		project.setName(request.getName());
		project.setPersons(persons);

		return new ProjectResponse(projectRepository.save(project));
	}

	@Override
	public ProjectResponse update(UUID id, ProjectCreateRequest request) {
		Project existingProject = projectRepository.findById(id)
				.orElseThrow(() -> new AppException("Không tìm thấy Dự án nào", HttpStatus.NOT_FOUND));

		Company company = companyRepository.findById(request.getCompanyId())
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy Công ty", HttpStatus.NOT_FOUND));

		List<Person> persons = personRepository.findAllById(request.getPersonIds());
		validatePersonsBelongToCompany(company.getId(), persons);

		existingProject.setCode(request.getCode());
		existingProject.setCompany(company);
		existingProject.setDescription(request.getDescription());
		existingProject.setName(request.getName());
		existingProject.setPersons(persons);

		return new ProjectResponse(projectRepository.save(existingProject));
	}

	@Override
	public void delete(UUID id) {
		if (!projectRepository.existsById(id)) {
			throw new AppException("Lỗi: Không tìm thấy Dự án nào", HttpStatus.NOT_FOUND);
		}
		projectRepository.deleteById(id);
	}

	private void validatePersonsBelongToCompany(UUID companyId, List<Person> persons) {
		if (persons != null && !persons.isEmpty()) {
			for (Person person : persons) {
				if (person.getCompany() == null || !person.getCompany().getId().equals(companyId)) {
					throw new AppException("Lỗi: Nhân viên " + person.getFullName() + " không thuộc Công ty này",
							HttpStatus.BAD_REQUEST);
				}
			}
		}
	}
}