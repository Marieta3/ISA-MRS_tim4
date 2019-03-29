package com.ISAtim4.WebAppSpringAirport.domain;

public class AdminAvio extends Korisnik {
	private AvioKompanija adminKompanije; //koje avio kompanije
	private Boolean UlogovanPrviPut; //mora prvi put da izmeni lozinku, posle ne
	
	public AdminAvio() {}	
	
	public AvioKompanija getAdminKompanije() {
		return adminKompanije;
	}
	public void setAdminKompanije(AvioKompanija adminKompanije) {
		this.adminKompanije = adminKompanije;
	}
	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}
	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}

	
	
	
}
