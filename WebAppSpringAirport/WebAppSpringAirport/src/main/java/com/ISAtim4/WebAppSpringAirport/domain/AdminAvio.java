package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("avio")
public class AdminAvio extends Korisnik {
	private static final long serialVersionUID = 7282033796376524253L;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonBackReference(value="adminiavio")
	private AvioKompanija avio_kompanija; // koje avio kompanije

	public AdminAvio() {
		super.setUlogovanPrviPut(false);
		super.setEnabled(true);
	}

	public AvioKompanija getAvio_kompanija() {
		return avio_kompanija;
	}

	public void setAvio_kompanija(AvioKompanija avio_kompanija) {
		this.avio_kompanija = avio_kompanija;
	}
}
