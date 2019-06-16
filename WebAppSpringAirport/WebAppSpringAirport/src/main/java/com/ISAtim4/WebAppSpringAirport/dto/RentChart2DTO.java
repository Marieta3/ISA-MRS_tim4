package com.ISAtim4.WebAppSpringAirport.dto;

public class RentChart2DTO {
	private String car;
	private Double carRating;

	public RentChart2DTO() {
		super();
	}

	public RentChart2DTO(String car, Double carRating) {
		super();
		this.car = car;
		this.carRating = carRating;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public Double getCarRating() {
		return carRating;
	}

	public void setCarRating(Double carRating) {
		this.carRating = carRating;
	}

}
