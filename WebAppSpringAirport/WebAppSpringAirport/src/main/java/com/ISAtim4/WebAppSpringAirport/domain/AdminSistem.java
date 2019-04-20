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

	public AdminSistem() {}

	public boolean isPredefinisaniAdmin() {
		return predefinisaniAdmin;
	}

	public void setPredefinisaniAdmin(boolean predefinisaniAdmin) {
		this.predefinisaniAdmin = predefinisaniAdmin;
	}

	
}
