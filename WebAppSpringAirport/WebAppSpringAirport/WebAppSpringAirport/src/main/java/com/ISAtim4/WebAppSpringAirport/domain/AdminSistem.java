package com.ISAtim4.WebAppSpringAirport.domain;

public class AdminSistem extends Korisnik {
	private Boolean predefinisaniAdmin; //onaj koji moze da dodaje druge sistem admine

	public AdminSistem() {}

	public Boolean getPredefinisaniAdmin() {
		return predefinisaniAdmin;
	}

	public void setPredefinisaniAdmin(Boolean predefinisaniAdmin) {
		this.predefinisaniAdmin = predefinisaniAdmin;
	}
}
