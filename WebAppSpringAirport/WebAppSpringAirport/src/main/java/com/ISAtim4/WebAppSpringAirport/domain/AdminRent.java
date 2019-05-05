package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("rent")
public class AdminRent extends Korisnik {
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private RentACar rentACar;

	public AdminRent() {
		super.setUlogovanPrviPut(false);
		super.setEnabled(true);
	}

	public AdminRent(RentACar rentACar) {
		super();
		super.setEnabled(true);
		super.setUlogovanPrviPut(false);
		this.rentACar = rentACar;

	}

	public RentACar getrentACar() {
		return rentACar;
	}

	public void setrentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}

}
