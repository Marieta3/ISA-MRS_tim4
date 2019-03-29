package com.ISAtim4.WebAppSpringAirport.domain;

public class Usluga {
	private Long id;
	private String opis;
	private double cena;
	
	public Usluga() {}
	
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
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
}
