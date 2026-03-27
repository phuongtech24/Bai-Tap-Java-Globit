package com.example.demo.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Person;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exception.AppException;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import com.example.demo.specification.TaskSpecification;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;
	private final PersonRepository personRepository;

	public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository,
			PersonRepository personRepository) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
		this.personRepository = personRepository;
	}

	@Override
	public Page<TaskResponse> searchByPage(Pageable pageable, UUID companyId, UUID projectId, UUID personId,
			TaskStatus status, TaskPriority priority, String name) {
		Specification<Task> spec = TaskSpecification.filterTasks(companyId, projectId, personId, status, priority,
				name);
		return taskRepository.findAll(spec, pageable).map(TaskResponse::new);
	}

	@Override
	public TaskResponse create(TaskRequest request) {
		Task task = new Task();
		mapRequestToEntity(request, task);
		Project project = getValidProject(request.getProjectId());
		Person person = getValidPerson(request.getPersonId());
		task.setPerson(person);
		task.setProject(project);
		return new TaskResponse(taskRepository.save(task));
	}

	@Override
	public TaskResponse update(UUID id, TaskRequest request) {
		Task existingTask = taskRepository.findById(id)
				.orElseThrow(() -> new AppException("Lỗi: Nhiệm vụ không tồn tại", HttpStatus.NOT_FOUND));
		mapRequestToEntity(request, existingTask);
		Project project = getValidProject(request.getProjectId());
		Person person = getValidPerson(request.getPersonId());
		existingTask.setPerson(person);
		existingTask.setProject(project);
		return new TaskResponse(taskRepository.save(existingTask));
	}

	@Override
	public void delete(UUID id) {
		if (!taskRepository.existsById(id)) {
			throw new AppException("Lỗi: Không tìm thấy Công việc để xóa", HttpStatus.NOT_FOUND);
		}
		taskRepository.deleteById(id);
	}

	@Override
	public ByteArrayInputStream exportTasksToExcel() throws IOException {
		List<Task> tasks = taskRepository.findAll();
		String[] columns = { "Project", "Description", "Start Time", "End Time", "Priority", "Status", "Person" };

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Task List");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (Task task : tasks) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(task.getProject().getName());
				row.createCell(1).setCellValue(task.getDescription());
				row.createCell(2).setCellValue(task.getStartTime().toString());
				row.createCell(3).setCellValue(task.getEndTime().toString());
				row.createCell(4).setCellValue(task.getPriority().toString());
				row.createCell(5).setCellValue(task.getStatus().toString());
				row.createCell(6)
						.setCellValue(task.getPerson() != null ? task.getPerson().getFullName() : "Chưa phân công");
			}

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	private void mapRequestToEntity(TaskRequest request, Task task) {
		task.setName(request.getName());
		task.setDescription(request.getDescription());
		task.setStartTime(request.getStartTime());
		task.setEndTime(request.getEndTime());
		task.setStatus(request.getStatus());
		task.setPriority(request.getPriority());
	}

	private Project getValidProject(UUID projectId) {
		if (projectId == null) {
			throw new AppException("Lỗi: Dự án không được để trống", HttpStatus.BAD_REQUEST);
		}
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy dự án", HttpStatus.BAD_REQUEST));
	}

	private Person getValidPerson(UUID personId) {
		if (personId == null) {
			return null;
		}
		return personRepository.findById(personId)
				.orElseThrow(() -> new AppException("Lỗi: Không tìm thấy nhân sự", HttpStatus.BAD_REQUEST));
	}
}