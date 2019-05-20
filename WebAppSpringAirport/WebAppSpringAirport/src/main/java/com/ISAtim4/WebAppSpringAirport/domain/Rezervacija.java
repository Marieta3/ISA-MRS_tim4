package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Rezervacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Set<Sediste> odabranaSedista=new HashSet<>();
	@Column(nullable = true)
	private Set<Soba> odabraneSobe=new HashSet<>();
	@Column(nullable = true)
	private Set<Vozilo> odabranaVozila=new HashSet<>();
	@Column(nullable = false)
	private Date datumRezervacije;
	@Column(nullable = false)
	private Boolean aktivnaRezervacija=true; //da li je prosao datum leta ili ne
	
	public Rezervacija() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Set<Sediste> getOdabranaSedista() {
		return odabranaSedista;
	}

	public void setOdabranaSedista(Set<Sediste> odabranaSedista) {
		this.odabranaSedista = odabranaSedista;
	}

	public Set<Soba> getOdabraneSobe() {
		return odabraneSobe;
	}

	public void setOdabraneSobe(Set<Soba> odabraneSobe) {
		this.odabraneSobe = odabraneSobe;
	}

	public Set<Vozilo> getOdabranaVozila() {
		return odabranaVozila;
	}

	public void setOdabranaVozila(Set<Vozilo> odabranaVozila) {
		this.odabranaVozila = odabranaVozila;
	}

	public Date getDatumRezervacije() {
		return datumRezervacije;
	}
	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}
	public Boolean getAktivnaRezervacija() {
		return aktivnaRezervacija;
	}
	public void setAktivnaRezervacija(Boolean aktivnaRezervacija) {
		this.aktivnaRezervacija = aktivnaRezervacija;
	}

}
