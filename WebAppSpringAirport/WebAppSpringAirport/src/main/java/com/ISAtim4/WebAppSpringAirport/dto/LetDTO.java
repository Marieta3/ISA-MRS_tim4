package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class LetDTO {
	private String tipPutovanja;
	private String mestoPolaska;
	private String mestoDolaska;
	private Date vremePolaska;
	private Date vremeDolaska;
	private int brojPutnika;
	
	public String getTipPutovanja() {
		return tipPutovanja;
	}
	
	public void setTipPutovanja(String tipPutovanja) {
		this.tipPutovanja = tipPutovanja;
	}
	
	public String getMestoPolaska() {
		return mestoPolaska;
	}
	
	public void setMestoPolaska(String mestoPolaska) {
		this.mestoPolaska = mestoPolaska;
	}
	public String getMestoDolaska() {
		return mestoDolaska;
	}
	
	public void setMestoDolaska(String mestoDolaska) {
		this.mestoDolaska = mestoDolaska;
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
	
	public int getBrojPutnika() {
		return brojPutnika;
	}
	
	public void setBrojPutnika(int brojPutnika) {
		this.brojPutnika = brojPutnika;
	}
	
	public LetDTO() {
		super();
	}
	
	public LetDTO(String tipPutovanja, String mestoPolaska,
			String mestoDolaska, Date vremePolaska, Date vremeDolaska,
			int brojPutnika) {
		this.tipPutovanja = tipPutovanja;
		this.mestoPolaska = mestoPolaska;
		this.mestoDolaska = mestoDolaska;
		this.vremePolaska = vremePolaska;
		this.vremeDolaska = vremeDolaska;
		this.brojPutnika = brojPutnika;
	}
	
	
}
