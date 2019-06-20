package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@DiscriminatorValue("hotel")
public class AdminHotel extends Korisnik {
	private static final long serialVersionUID = -5299829967225704146L;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JsonBackReference(value="adminhotela")
	//@JsonIgnoreProperties("admini_hotela")
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
