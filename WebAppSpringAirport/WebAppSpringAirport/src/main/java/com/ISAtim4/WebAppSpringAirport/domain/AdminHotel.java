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
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference
	private Hotel hotel;
	
	@Column(nullable=true)
	private Boolean UlogovanPrviPut; //da li je prvi put ili ne
	
	public AdminHotel(Hotel hotel, Boolean ulogovanPrviPut) {
		super();
		this.hotel = hotel;
		UlogovanPrviPut = ulogovanPrviPut;
	}

	public AdminHotel() {
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}

	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}
	
	
	
}
