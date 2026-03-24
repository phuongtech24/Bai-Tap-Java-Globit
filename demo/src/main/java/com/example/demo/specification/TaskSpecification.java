package com.example.demo.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entity.Task;
import com.example.demo.enums.TaskPriority;
import com.example.demo.enums.TaskStatus;

import jakarta.persistence.criteria.Predicate;

public class TaskSpecification {

	public static Specification<Task> filterTasks(UUID companyId, UUID projectId, UUID personId, TaskStatus status,
			TaskPriority priority, String name) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (name != null && !name.trim().isEmpty()) {
				predicates.add(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
			}
			if (projectId != null) {
				predicates.add(criteriaBuilder.equal(root.get("project").get("id"), projectId));
			}
			if (personId != null) {
				predicates.add(criteriaBuilder.equal(root.get("person").get("id"), personId));
			}
			if (status != null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), status));
			}

			if (priority != null) {
				predicates.add(criteriaBuilder.equal(root.get("priority"), priority));
			}
			if (companyId != null) {
				predicates.add(criteriaBuilder.equal(root.get("project").get("company").get("id"), companyId));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
