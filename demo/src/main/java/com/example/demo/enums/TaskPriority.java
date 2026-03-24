package com.example.demo.enums;

public enum TaskPriority {
	HIGH(1, "Cao"), MEDIUM(2, "Trung bình"), LOW(3, "Thấp");

	private final int value;
	private final String description;

	TaskPriority(int value, String description) {
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
