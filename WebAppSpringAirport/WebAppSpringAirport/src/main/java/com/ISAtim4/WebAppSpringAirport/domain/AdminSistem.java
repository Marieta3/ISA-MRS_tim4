package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sis")
public class AdminSistem extends Korisnik {
	private static final long serialVersionUID = 5871275446393811095L;

	@Column(nullable = true)
	private boolean predefinisaniAdmin; // onaj koji moze da dodaje druge sistem
										// admine

	public AdminSistem() {
		super.setUlogovanPrviPut(false);
		super.setEnabled(true);
	}

	public boolean isPredefinisaniAdmin() {
		return predefinisaniAdmin;
	}

	public void setPredefinisaniAdmin(boolean predefinisaniAdmin) {
		this.predefinisaniAdmin = predefinisaniAdmin;
	}

}
