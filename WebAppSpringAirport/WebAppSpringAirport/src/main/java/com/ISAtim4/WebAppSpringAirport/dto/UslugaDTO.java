package com.ISAtim4.WebAppSpringAirport.dto;

public class UslugaDTO {
	private String opis;
	private double cena;
	private Long hotel_id;
	
	public UslugaDTO() {}

	public UslugaDTO(String opis, double cena, Long hotel_id) {
		super();
		this.opis = opis;
		this.cena = cena;
		this.hotel_id = hotel_id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Long getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(Long hotel_id) {
		this.hotel_id = hotel_id;
	}
	
	
}
