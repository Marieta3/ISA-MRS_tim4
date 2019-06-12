package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class SobaPretragaDTO {
	private Date vremeDolaska;
	private Integer brojNocenja;
	
	public SobaPretragaDTO() {
		
	}

	public Date getVremeDolaska() {
		return vremeDolaska;
	}

	public void setVremeDolaska(Date vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}

	public Integer getBrojNocenja() {
		return brojNocenja;
	}

	public void setBrojNocenja(Integer brojNocenja) {
		this.brojNocenja = brojNocenja;
	}
}
