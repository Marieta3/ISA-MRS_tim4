package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

	@Column(nullable = true)
	private double brojPoena; // mora prvi put da izmeni lozinku, posle
	
	public RegistrovaniKorisnik() {
		super();
		super.setUlogovanPrviPut(true);
		this.brojPoena = 0;
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

	public List<Pozivnica> getListaPozivnica() {
		return listaPozivnica;
	}

	public void setListaPozivnica(List<Pozivnica> listaPozivnica) {
		this.listaPozivnica = listaPozivnica;
	}

	public double getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(double brojPoena) {
		this.brojPoena = brojPoena;
	}

}
