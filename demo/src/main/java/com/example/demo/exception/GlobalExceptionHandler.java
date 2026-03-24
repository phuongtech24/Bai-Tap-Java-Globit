package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AppException.class)
	public ResponseEntity<Map<String, Object>> handleAppException(AppException e) {
		Map<String, Object> error = new HashMap<>();
		HttpStatus status = e.getStatus();

		error.put("status", status.value());
		error.put("message", e.getMessage());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
		Map<String, Object> error = new HashMap<>();
		e.printStackTrace();
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("message", "Lỗi dữ liệu đầu vào hoặc định dạng không hợp lệ!");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}