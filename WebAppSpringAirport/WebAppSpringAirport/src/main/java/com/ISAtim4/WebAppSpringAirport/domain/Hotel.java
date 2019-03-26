package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashMap;

public class Hotel {
	private Long id;
	private String naziv;
	private String adresa;
	private String opis;
	private double ocena;
	private HashMap<Long, Soba> sobe;
	private HashMap<Long, Usluga> usluge;
	
	public Hotel() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
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

	public HashMap<Long, Soba> getSobe() {
		return sobe;
	}

	public void setSobe(HashMap<Long, Soba> sobe) {
		this.sobe = sobe;
	}

	public HashMap<Long, Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(HashMap<Long, Usluga> usluge) {
		this.usluge = usluge;
	}
	
	
}
