package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SIS")
public class AdminSistem extends Korisnik {
	
	@Column(nullable = false)
	private Boolean predefinisaniAdmin; //onaj koji moze da dodaje druge sistem admine

	public AdminSistem() {}

	public Boolean getPredefinisaniAdmin() {
		return predefinisaniAdmin;
	}

	public void setPredefinisaniAdmin(Boolean predefinisaniAdmin) {
		this.predefinisaniAdmin = predefinisaniAdmin;
	}
}
