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
@DiscriminatorValue("hotel")
public class AdminHotel extends Korisnik {
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JsonBackReference
	private Hotel hotel;
	
	
	
	public AdminHotel(Hotel hotel) {
		super();
		this.hotel = hotel;
		super.setUlogovanPrviPut(false);
		
	}

	public AdminHotel() {
		super.setUlogovanPrviPut(false);
		super.setEnabled(true);
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	
	
	
	
}
