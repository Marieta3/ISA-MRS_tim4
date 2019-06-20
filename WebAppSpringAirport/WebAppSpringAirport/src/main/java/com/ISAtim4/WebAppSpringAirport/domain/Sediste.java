package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Sediste {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Version
	private int version=1;
	
	@Column(nullable=false)
	private String klasa;
	
	@Column(nullable=false)
	private boolean rezervisano=false;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	//@JsonBackReference
	@JsonIgnoreProperties("sedista")
	private Let let;
	
	//u kojoj se nalazi dato sediste
	@Column(nullable=false)
	private int brojReda;
	
	//u kojoj se nalazi dato sediste
	@Column(nullable=false)
	private int brojKolone;
	
	@Column(nullable=false)
	private String row_col;
	
	@Column(nullable=false)
	private double cena;
	
	@OneToOne(mappedBy="sediste", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("sediste")
	private BrzoSediste brzo_sediste;
	
	@OneToOne(mappedBy="sediste", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("sediste")
	private BrzaSoba brza_soba;
	
	@OneToOne(mappedBy="sediste", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("sediste")
	private BrzoVozilo brzo_vozilo;
	
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
		this.row_col=brojReda+"_"+brojKolone;
	}

	public Sediste() {
		super();
	}

	public String getRow_col() {
		return row_col;
	}

	public void setRow_col(String row_col) {
		this.row_col = row_col;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BrzoSediste getBrzo_sediste() {
		return brzo_sediste;
	}

	public void setBrzo_sediste(BrzoSediste brzo_sediste) {
		this.brzo_sediste = brzo_sediste;
	}

	
	
	
	
}
