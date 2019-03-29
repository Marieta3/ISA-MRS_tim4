package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AvioKompanija {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String naziv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public AvioKompanija() {
		super();
	}

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "admin_id")
	private AdminAvio admin;

	public AdminAvio getAdmin() {
		return admin;
	}

	public void setAdmin(AdminAvio admin) {
		this.admin = admin;
	}
	
	/*
	private String adresa;
	private String opis;
	private Set<Destinacija> listaDestinacija = new HashSet<>();
	private Set<Let> listaLetova = new HashSet<>();
	
	public AvioKompanija() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<Destinacija> getListaDestinacija() {
		return listaDestinacija;
	}

	public void setListaDestinacija(Set<Destinacija> listaDestinacija) {
		this.listaDestinacija = listaDestinacija;
	}

	public Set<Let> getListaLetova() {
		return listaLetova;
	}

	public void setListaLetova(Set<Let> listaLetova) {
		this.listaLetova = listaLetova;
	}*/
}
