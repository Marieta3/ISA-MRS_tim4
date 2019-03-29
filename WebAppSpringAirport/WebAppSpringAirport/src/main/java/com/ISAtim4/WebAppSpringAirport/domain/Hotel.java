package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;

public class Hotel {
	private Long id;
	private String naziv;
	private String adresa;
	private String opis;
	private double ocena;
	private HashSet<Soba> sobe = new HashSet<>();
	private HashSet<Usluga> usluge = new HashSet<>();
	
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

	public HashSet<Soba> getSobe() {
		return sobe;
	}

	public void setSobe(HashSet<Soba> sobe) {
		this.sobe = sobe;
	}

	public HashSet<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(HashSet<Usluga> usluge) {
		this.usluge = usluge;
	}
}
