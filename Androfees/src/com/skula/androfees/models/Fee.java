package com.skula.androfees.models;

public class Fee {
	private String id;
	private String label;
	private String amount;
	private String category;
	private String location;
	private String date;

	public Fee() {
	}

	public Fee(String id, String label, String amount, String category, String date,
			String location) {
		this.label = label;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
