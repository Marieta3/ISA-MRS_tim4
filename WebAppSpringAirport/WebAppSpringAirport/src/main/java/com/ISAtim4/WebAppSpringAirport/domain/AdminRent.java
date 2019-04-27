package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("rent")
public class AdminRent extends Korisnik {
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private RentACar rent_a_car;
	

	public AdminRent() {super.setUlogovanPrviPut(false);super.setEnabled(true);}

	public AdminRent(RentACar rent_a_car) {
		super();
		super.setEnabled(true);
		super.setUlogovanPrviPut(false);
		this.rent_a_car = rent_a_car;
		
	}

	public RentACar getRent_a_car() {
		return rent_a_car;
	}

	public void setRent_a_car(RentACar rent_a_car) {
		this.rent_a_car = rent_a_car;
	}

	

	
}
