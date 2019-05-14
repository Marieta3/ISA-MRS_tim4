package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Avion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	@Column(nullable=false)
	private String model;

	@Column(nullable=false)
	private int brojRedova;
	
	@Column(nullable=false)
	private int brojKolona;
	
	@Column(nullable=false)
	//broj redova u first class-i
	private int brojRedovaFC;
	
	/*@Column(nullable=false)
	//broj u first class-i
	private int brojKolonaFC;
	*/
	@Column(nullable=false)
	//broj redova u economy class-i
	private int brojRedovaEC;
	/*
	@Column(nullable=false)
	//broj u economy class-i
	private int brojKolonaEC;
	*/
	
	@Column(nullable=false)
	//broj redova u bussiness class-i
	private int brojRedovaBC;
	
	/*
	@Column(nullable=false)
	//broj u bussiness class-i
	private int brojKolonaBC;
	*/
	
	@Column(nullable=true)
	private String slika;
	
	public Avion() {
		super();
	}

	public Avion(Long id, String model, int brojRedova, int brojKolona,
			int brojRedovaFC, int brojRedovaEC,
			 int brojRedovaBC,String slika) {
		this.id = id;
		this.model = model;
		this.brojRedova = brojRedova;
		this.brojKolona = brojKolona;
		this.brojRedovaFC = brojRedovaFC;
		this.brojRedovaEC = brojRedovaEC;
		this.brojRedovaBC = brojRedovaBC;
		this.slika = slika;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getBrojRedova() {
		return brojRedova;
	}

	public void setBrojRedova(int brojRedova) {
		this.brojRedova = brojRedova;
	}

	public int getBrojKolona() {
		return brojKolona;
	}

	public void setBrojKolona(int brojKolona) {
		this.brojKolona = brojKolona;
	}

	public int getBrojRedovaFC() {
		return brojRedovaFC;
	}

	public void setBrojRedovaFC(int brojRedovaFC) {
		this.brojRedovaFC = brojRedovaFC;
	}

	public int getBrojRedovaEC() {
		return brojRedovaEC;
	}

	public void setBrojRedovaEC(int brojRedovaEC) {
		this.brojRedovaEC = brojRedovaEC;
	}

	public int getBrojRedovaBC() {
		return brojRedovaBC;
	}

	public void setBrojRedovaBC(int brojRedovaBC) {
		this.brojRedovaBC = brojRedovaBC;
	}
}
