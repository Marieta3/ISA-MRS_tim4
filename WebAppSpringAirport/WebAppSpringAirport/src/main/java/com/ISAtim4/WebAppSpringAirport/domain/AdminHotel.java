package com.ISAtim4.WebAppSpringAirport.domain;

public class AdminHotel extends Korisnik {
	private Hotel adminHotela;
	private Boolean UlogovanPrviPut; //da li je prvi put ili ne
	
	public AdminHotel() {
	}
	
	public Hotel getAdminHotela() {
		return adminHotela;
	}
	public void setAdminHotela(Hotel adminHotela) {
		this.adminHotela = adminHotela;
	}
	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}
	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}
}
