package com.ISAtim4.WebAppSpringAirport.dto;

public class Chart3DTO {
	private String type; // monthly, weekly, daily
	private String value;

	public Chart3DTO() {
		super();
	}

	public Chart3DTO(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
