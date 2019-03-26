package com.ISAtim4.WebAppSpringAirport.domain;

public class Soba {
	private Long id;
	private String opis;
	private double ocena;
	
	public Soba() {}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
}
