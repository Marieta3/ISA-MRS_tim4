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
//@DiscriminatorValue("AVIO")
public class AdminAvio extends Korisnik {
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonBackReference
	private AvioKompanija avio_kompanija; //koje avio kompanije
	
	@Column(nullable=false)
	private Boolean UlogovanPrviPut; //mora prvi put da izmeni lozinku, posle ne
	
	public AdminAvio() {}	
	
	
	

	public AvioKompanija getAvio_kompanija() {
		return avio_kompanija;
	}




	public void setAvio_kompanija(AvioKompanija avio_kompanija) {
		this.avio_kompanija = avio_kompanija;
	}




	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}
	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
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
