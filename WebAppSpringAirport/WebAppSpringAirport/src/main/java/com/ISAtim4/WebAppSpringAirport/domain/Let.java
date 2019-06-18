package com.ISAtim4.WebAppSpringAirport.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Let {
	@Id
	@GeneratedValue
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(nullable=false)
	private String pocetnaDestinacija;
	
	@Column(nullable=false)
	private String krajnjaDestinacija;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable=false)
	private Date vremePolaska;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable=false)
	private Date vremeDolaska;
	
	@Column(nullable=false)
	private Integer duzinaPutovanja;

	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonIgnoreProperties("listaLetova")
	private AvioKompanija avioKompanija;
	
	@OneToMany(mappedBy = "let", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonManagedReference
	@JsonIgnoreProperties("let")
	private Set<Sediste> sedista = new HashSet<>();
	
	/*
	 * Slede atributi AVIONA	
	 */
	
	@Column(nullable=false)
	private String model;
	
	@Column(nullable=true)
	private Double ocena=0.0;

	@Column(nullable=false)
	private int brojRedova;
	
	@Column(nullable=false)
	private int brojKolona;
	
	@Column(nullable=false)
	//broj redova u first class-i
	private int brojRedovaFC;

	@Column(nullable=false)
	//broj redova u economy class-i
	private int brojRedovaEC;
	@Column(nullable=false)
	//broj redova u bussiness class-i
	private int brojRedovaBC;
	
	@Column(nullable=false)
	private double cenaFC;
	
	@Column(nullable=false)
	private double cenaEC;
	
	@Column(nullable=false)
	private double cenaBC;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPocetnaDestinacija() {
		return pocetnaDestinacija;
	}
	public void setPocetnaDestinacija(String pocetnaDestinacija) {
		this.pocetnaDestinacija = pocetnaDestinacija;
	}
	public String getKrajnjaDestinacija() {
		return krajnjaDestinacija;
	}
	public void setKrajnjaDestinacija(String krajnjaDestinacija) {
		this.krajnjaDestinacija = krajnjaDestinacija;
	}
	public Date getVremePolaska() {
		return vremePolaska;
	}
	public void setVremePolaska(Date vremePolaska) {
		this.vremePolaska = vremePolaska;
	}
	public Date getVremeDolaska() {
		return vremeDolaska;
	}
	public void setVremeDolaska(Date vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}
	public Integer getDuzinaPutovanja() {
		return duzinaPutovanja;
	}
	public void setDuzinaPutovanja(Integer duzinaPutovanja) {
		this.duzinaPutovanja = duzinaPutovanja;
	}
	
	public Set<Sediste> getSedista() {
		return sedista;
	}
	public void setSedista(Set<Sediste> sedista) {
		this.sedista = sedista;
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
	public Let() {
		super();
	}
	public Double getOcena() {
		return ocena;
	}
	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}
	public Let(Long id, String pocetnaDestinacija, String krajnjaDestinacija,
			Date vremePolaska, Date vremeDolaska, Integer duzinaPutovanja,
			AvioKompanija avio_kompanija, Set<Sediste> sedista, String model,
			int brojRedova, int brojKolona, int brojRedovaFC, int brojRedovaEC,
			int brojRedovaBC) {
		this.id = id;
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.vremePolaska = vremePolaska;
		this.vremeDolaska = vremeDolaska;
		this.duzinaPutovanja = duzinaPutovanja;
		this.avioKompanija = avio_kompanija;
		this.sedista = sedista;
		this.model = model;
		this.brojRedova = brojRedova;
		this.brojKolona = brojKolona;
		this.brojRedovaFC = brojRedovaFC;
		this.brojRedovaEC = brojRedovaEC;
		this.brojRedovaBC = brojRedovaBC;
	}
	public AvioKompanija getAvioKompanija() {
		return avioKompanija;
	}
	public void setAvioKompanija(AvioKompanija avioKompanija) {
		this.avioKompanija = avioKompanija;
	}
}
