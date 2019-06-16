package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class VoziloPretragaDTO {
	private Date vremeDolaska;
	private Integer brojDana;
	
	public VoziloPretragaDTO() {
		
	}

	public Date getVremeDolaska() {
		return vremeDolaska;
	}

	public void setVremeDolaska(Date vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}

	public Integer getBrojDana() {
		return brojDana;
	}

	public void setBrojDana(Integer brojDana) {
		this.brojDana = brojDana;
	}
}
