package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("avio")
public class AdminAvio extends Korisnik {
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JsonBackReference
	private AvioKompanija avio_kompanija; //koje avio kompanije
	
	
	
	public AdminAvio() {super.setUlogovanPrviPut(false);super.setEnabled(true);}	
	
	
	

	public AvioKompanija getAvio_kompanija() {
		return avio_kompanija;
	}




	public void setAvio_kompanija(AvioKompanija avio_kompanija) {
		this.avio_kompanija = avio_kompanija;
	}




	
	
	
	
}
