package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class ZahtevPrijateljstvo {
	private Long id;
	private RegistrovaniKorisnik koSalje;
	private RegistrovaniKorisnik komeSalje;
	private Boolean prihvacen; //da li je prihvacen ili nije
	private Date datumSlanja;
	
	public ZahtevPrijateljstvo() {
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
	public Boolean getPrihvacen() {
		return prihvacen;
	}
	public void setPrihvacen(Boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
	public Date getDatumSlanja() {
		return datumSlanja;
	}
	public void setDatumSlanja(Date datumSlanja) {
		this.datumSlanja = datumSlanja;
	}
	
	
}
