package com.example.demo.enums;

public enum TaskStatus {
	CREATED(1, "Mới tạo"), DOING(2, "Đang làm"), COMPLETED(3, "Hoàn thành"), POSTPONED(4, "Tạm hoãn");

	private final int value;
	private final String description;

	TaskStatus(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
