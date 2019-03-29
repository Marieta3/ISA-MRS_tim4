package com.ISAtim4.WebAppSpringAirport.domain;

public class AdminRent extends Korisnik {
	private RentACar adminRentAcar;
	private Boolean UlogovanPrviPut; //da li je prvi put ili ne

	public AdminRent() {}

	public RentACar getAdminRentAcar() {
		return adminRentAcar;
	}

	public void setAdminRentAcar(RentACar adminRentAcar) {
		this.adminRentAcar = adminRentAcar;
	}

	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}

	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}
}
