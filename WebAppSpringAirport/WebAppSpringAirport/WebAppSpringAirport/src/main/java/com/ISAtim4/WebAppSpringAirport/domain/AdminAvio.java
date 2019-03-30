package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("AVIO")
public class AdminAvio extends Korisnik {
	
	@OneToOne(mappedBy = "admin",fetch = FetchType.LAZY)
	private AvioKompanija avioKompanija; //koje avio kompanije
	
	@Column(nullable=false)
	private Boolean UlogovanPrviPut; //mora prvi put da izmeni lozinku, posle ne
	
	public AdminAvio() {}	
	
	
	public AvioKompanija getAvioKompanija() {
		return avioKompanija;
	}


	public void setAvioKompanija(AvioKompanija avioKompanija) {
		this.avioKompanija = avioKompanija;
	}


	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}
	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}

	
	
	
}
