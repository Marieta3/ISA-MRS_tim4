package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class HotelDTO {
	private String lokNaziv; //ili lokacija ili naziv, u zavisnosti od tipa pretrage
	private Date datumPolaska;
	private Date datumDolaska;
	private String tipPretrage; //ili po lokaciji ili po nazivu hotela
	
	public String getLokNaziv() {
		return lokNaziv;
	}
	
	public void setLokNaziv(String lokNaziv) {
		this.lokNaziv = lokNaziv;
	}
	
	public Date getDatumPolaska() {
		return datumPolaska;
	}
	
	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}
	
	public Date getDatumDolaska() {
		return datumDolaska;
	}
	
	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	
	public String getTipPretrage() {
		return tipPretrage;
	}
	
	public void setTipPretrage(String tipPretrage) {
		this.tipPretrage = tipPretrage;
	}
	
	public HotelDTO() {
		super();
	}
	
	public HotelDTO(String lokNaziv, Date datumPolaska, Date datumDolaska,
			String tipPretrage) {
		this.lokNaziv = lokNaziv;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.tipPretrage = tipPretrage;
	}
}
