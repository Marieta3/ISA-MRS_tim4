package com.ISAtim4.WebAppSpringAirport.dto;

public class RentChart1DTO {
	private String service;
	private Double serviceRating;
	private String others;
	private Double othersRating;

	public RentChart1DTO() {
		super();
	}

	public RentChart1DTO(String service, Double serviceRating, String others,
			Double othersRating) {
		super();
		this.service = service;
		this.serviceRating = serviceRating;
		this.others = others;
		this.othersRating = othersRating;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Double getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Double serviceRating) {
		this.serviceRating = serviceRating;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Double getOthersRating() {
		return othersRating;
	}

	public void setOthersRating(Double othersRating) {
		this.othersRating = othersRating;
	}

}
