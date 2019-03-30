package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AdminRent extends Korisnik {
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RentACar rent_a_car;
	@Column(nullable= false)
	private Boolean UlogovanPrviPut; //da li je prvi put ili ne

	public AdminRent() {}

	public AdminRent(RentACar rent_a_car, Boolean ulogovanPrviPut) {
		super();
		this.rent_a_car = rent_a_car;
		UlogovanPrviPut = ulogovanPrviPut;
	}

	public RentACar getRent_a_car() {
		return rent_a_car;
	}

	public void setRent_a_car(RentACar rent_a_car) {
		this.rent_a_car = rent_a_car;
	}

	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}

	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}

	
}
