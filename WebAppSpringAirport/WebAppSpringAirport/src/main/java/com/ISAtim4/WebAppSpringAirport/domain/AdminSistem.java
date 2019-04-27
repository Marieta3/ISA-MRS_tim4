package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

@Entity
@DiscriminatorValue("sis")
public class AdminSistem extends Korisnik {
	
	@Column(nullable = true)
	private boolean predefinisaniAdmin; //onaj koji moze da dodaje druge sistem admine

	@Column(nullable= true)
	private Boolean UlogovanPrviPut=false; //da li je prvi put ili ne

	public AdminSistem() {this.UlogovanPrviPut=false;super.setEnabled(true);}
	

	public boolean isPredefinisaniAdmin() {
		return predefinisaniAdmin;
	}

	public void setPredefinisaniAdmin(boolean predefinisaniAdmin) {
		this.predefinisaniAdmin = predefinisaniAdmin;
	}


	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}


	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}

	
	
}
