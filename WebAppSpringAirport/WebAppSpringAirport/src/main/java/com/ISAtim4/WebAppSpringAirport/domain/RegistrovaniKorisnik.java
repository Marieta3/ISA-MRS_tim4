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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DiscriminatorValue("user")
public class RegistrovaniKorisnik extends Korisnik {
	private static final long serialVersionUID = -7495559707293346754L;

	@OneToMany(cascade = CascadeType.ALL)
	List<Prijateljstvo> listaPrijatelja;

	@OneToMany(cascade = CascadeType.ALL)
	List<Ocena> listaOcena;

	@OneToMany(cascade = CascadeType.ALL)
	List<Pozivnica> listaPozivnica;
	
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

	public List<Ocena> getListaOcena() {
		return listaOcena;
	}

	public void setListaOcena(List<Ocena> listaOcena) {
		this.listaOcena = listaOcena;
	}

	
	

}
