package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class Pozivnica {
	private Long id;
	private RegistrovaniKorisnik koSalje;
	private RegistrovaniKorisnik komeSalje;
	private Rezervacija rezervacija;
	private Boolean prihvacen; //da li je prihvacen ili nije
	private Boolean reagovanoNaPoziv; //da li je isteklo vreme
	private Date datumSlanja;
	
	public Pozivnica() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RegistrovaniKorisnik getKoSalje() {
		return koSalje;
	}
	public void setKoSalje(RegistrovaniKorisnik koSalje) {
		this.koSalje = koSalje;
	}
	public RegistrovaniKorisnik getKomeSalje() {
		return komeSalje;
	}
	public void setKomeSalje(RegistrovaniKorisnik komeSalje) {
		this.komeSalje = komeSalje;
	}
	public Rezervacija getRezervacija() {
		return rezervacija;
	}
	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}
	public Boolean getPrihvacen() {
		return prihvacen;
	}
	public void setPrihvacen(Boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
	public Boolean getReagovanoNaPoziv() {
		return reagovanoNaPoziv;
	}
	public void setReagovanoNaPoziv(Boolean reagovanoNaPoziv) {
		this.reagovanoNaPoziv = reagovanoNaPoziv;
	}
	public Date getDatumSlanja() {
		return datumSlanja;
	}
	public void setDatumSlanja(Date datumSlanja) {
		this.datumSlanja = datumSlanja;
	}
	
	
}
