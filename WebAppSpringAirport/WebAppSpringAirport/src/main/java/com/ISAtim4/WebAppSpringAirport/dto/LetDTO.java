package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class LetDTO {
	private String tipPutovanja;
	private String mestoPolaska;
	private String mestoDolaska;
	private Date vreme1;
	private Date vreme2;
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
			String mestoDolaska, Date vreme1, Date vreme2,
			int brojPutnika) {
		this.tipPutovanja = tipPutovanja;
		this.mestoPolaska = mestoPolaska;
		this.mestoDolaska = mestoDolaska;
		this.vreme1 = vreme1;
		this.vreme2 = vreme2;
		this.brojPutnika = brojPutnika;
	}

	public Date getVreme1() {
		return vreme1;
	}

	public void setVreme1(Date vreme1) {
		this.vreme1 = vreme1;
	}

	public Date getVreme2() {
		return vreme2;
	}

	public void setVreme2(Date vreme2) {
		this.vreme2 = vreme2;
	}
	
	
}
