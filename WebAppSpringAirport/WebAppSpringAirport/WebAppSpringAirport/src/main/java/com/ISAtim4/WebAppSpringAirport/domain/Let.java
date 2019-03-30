package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@Column(nullable=false)
	private Date vremePolaska;
	
	@Column(nullable=false)
	private Date vremeDolaska;
	
	@Column(nullable=false)
	private Integer duzinaPutovanja;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference
	private AvioKompanija avio_kompanija;
	
	public Let(Long id, String pocetnaDestinacija, String krajnjaDestinacija, Date vremePolaska, Date vremeDolaska,
			Integer duzinaPutovanja, AvioKompanija avio_kompanija) {
		super();
		this.id = id;
		this.pocetnaDestinacija = pocetnaDestinacija;
		this.krajnjaDestinacija = krajnjaDestinacija;
		this.vremePolaska = vremePolaska;
		this.vremeDolaska = vremeDolaska;
		this.duzinaPutovanja = duzinaPutovanja;
		this.avio_kompanija = avio_kompanija;
	}

	public Let() {
		super();
	}

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

	public AvioKompanija getAvio_kompanija() {
		return avio_kompanija;
	}

	public void setAvio_kompanija(AvioKompanija avio_kompanija) {
		this.avio_kompanija = avio_kompanija;
	}
	
	
}
