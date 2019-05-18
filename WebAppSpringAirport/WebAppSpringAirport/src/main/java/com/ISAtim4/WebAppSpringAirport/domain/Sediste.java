package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Sediste {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(nullable=false)
	private String klasa;
	
	@Column(nullable=false)
	private boolean rezervisano;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JsonBackReference
	private Let let;
	
	//u kojoj se nalazi dato sediste
	@Column(nullable=false)
	private int brojReda;
	
	//u kojoj se nalazi dato sediste
	@Column(nullable=false)
	private int brojKolone;
	
	@Column(nullable=false)
	private double cena;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	public boolean isRezervisano() {
		return rezervisano;
	}

	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public int getBrojReda() {
		return brojReda;
	}

	public void setBrojReda(int brojReda) {
		this.brojReda = brojReda;
	}

	public int getBrojKolone() {
		return brojKolone;
	}

	public void setBrojKolone(int brojKolone) {
		this.brojKolone = brojKolone;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Sediste(Long id, String klasa, boolean rezervisano, Let let,
			int brojReda, int brojKolone, double cena) {
		super();
		this.id = id;
		this.klasa = klasa;
		this.rezervisano = rezervisano;
		this.let = let;
		this.brojReda = brojReda;
		this.brojKolone = brojKolone;
		this.cena = cena;
	}

	public Sediste() {
		super();
	}
	
	
	
}