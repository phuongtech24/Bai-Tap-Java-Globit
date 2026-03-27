package com.example.demo.common;

public class ApiResponse<T> {
	private int status;
	private String message;
	private T data;

	public ApiResponse() {
	}

	public static <T> ApiResponse<T> success(T data, String message) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setStatus(200);
		response.setMessage(message);
		response.setData(data);
		return response;
	}

	public static <T> ApiResponse<T> error(String message) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setStatus(400);
		response.setMessage(message);
		response.setData(null);
		return response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}