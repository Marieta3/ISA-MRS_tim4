package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class Rezervacija {
	private Long id;
	private Let odabranLet;
	private Soba odabranaSoba;
	private Vozilo odabranoVozilo;
	private Date datumRezervacije;
	private Boolean aktivnaRezervacija; //da li je prosao datum leta ili ne
	
	public Rezervacija() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Let getOdabranLet() {
		return odabranLet;
	}
	public void setOdabranLet(Let odabranLet) {
		this.odabranLet = odabranLet;
	}
	public Soba getOdabranaSoba() {
		return odabranaSoba;
	}
	public void setOdabranaSoba(Soba odabranaSoba) {
		this.odabranaSoba = odabranaSoba;
	}
	public Vozilo getOdabranoVozilo() {
		return odabranoVozilo;
	}
	public void setOdabranoVozilo(Vozilo odabranoVozilo) {
		this.odabranoVozilo = odabranoVozilo;
	}
	public Date getDatumRezervacije() {
		return datumRezervacije;
	}
	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}
	public Boolean getAktivnaRezervacija() {
		return aktivnaRezervacija;
	}
	public void setAktivnaRezervacija(Boolean aktivnaRezervacija) {
		this.aktivnaRezervacija = aktivnaRezervacija;
	}

}
