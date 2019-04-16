package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

@Entity
//@DiscriminatorValue("SIS")
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
