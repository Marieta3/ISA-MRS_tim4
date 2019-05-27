package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("user")
public class RegistrovaniKorisnik extends Korisnik {
	private static final long serialVersionUID = -7495559707293346754L;

	@OneToMany(cascade = CascadeType.ALL)
	List<Prijateljstvo> listaPrijatelja;
	

	public RegistrovaniKorisnik() {
		super();
		super.setUlogovanPrviPut(true);
	}

	public List<Prijateljstvo> getListaPrijatelja() {
		return listaPrijatelja;
	}

	public void setListaPrijatelja(List<Prijateljstvo> listaPrijatelja) {
		this.listaPrijatelja = listaPrijatelja;
	}

	

}
