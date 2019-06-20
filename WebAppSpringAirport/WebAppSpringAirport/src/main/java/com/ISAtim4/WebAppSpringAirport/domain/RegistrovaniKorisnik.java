package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@OneToOne(mappedBy="putnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("putnik")
	private BrzaSoba brza_soba;
	
	@OneToOne(mappedBy="putnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("putnik")
	private BrzoVozilo brzo_vozilo;
	
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
