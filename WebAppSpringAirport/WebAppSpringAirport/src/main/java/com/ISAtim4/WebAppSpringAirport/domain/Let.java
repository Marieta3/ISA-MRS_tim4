package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class Let {
	private Long id;
	
	private String pocetnaDestinacija;
	private String krajnjaDestinacija;
	private Date vremePolaska;
	private Date vremeDolaska;
	private Integer duzinaPutovanja;
	
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
	
	
}
