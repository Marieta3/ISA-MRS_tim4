package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RentACar {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String naziv;
	@Column(nullable = false)
	private String adresa;
	@Column(nullable = false)
	private String opis;
	//@Column(nullable = true)
	//private Double ocena;

	@OneToMany(mappedBy = "rent_a_car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Vozilo> vozila = new HashSet<Vozilo>();
	@OneToMany(mappedBy = "rent_a_car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Filijala> filijale = new HashSet<Filijala>();
	@OneToMany(mappedBy = "rent_a_car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<AdminRent> admini = new HashSet<AdminRent>();

	public RentACar() {
	}

	public RentACar(Long id, String naziv, String adresa, String opis, Set<Vozilo> vozila, Set<Filijala> filijale,
			Set<AdminRent> admini) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		//this.ocena = ocena;
		this.vozila = vozila;
		this.filijale = filijale;
		this.admini = admini;
	}

	public RentACar(RentACar r) {
		super();
		this.id = r.getId();
		this.naziv = r.getNaziv();
		this.adresa = r.getAdresa();
		this.opis = r.getOpis();
		//this.ocena = r.getOcena();
		this.vozila = r.getVozila();
		this.filijale = r.getFilijale();
		this.admini = r.getAdmini();
	}

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

	

	public Set<Vozilo> getVozila() {
		return vozila;
	}

	public void setVozila(Set<Vozilo> vozila) {
		this.vozila = vozila;
	}

	public Set<Filijala> getFilijale() {
		return filijale;
	}

	public void setFilijale(Set<Filijala> filijale) {
		this.filijale = filijale;
	}

	public Set<AdminRent> getAdmini() {
		return admini;
	}

	public void setAdmini(Set<AdminRent> admini) {
		this.admini = admini;
	}

	@Override
	public String toString() {
		return "RentACar [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa
				+ ", opis=" + opis + ", ocena="  + ", vozila=" + vozila
				+ ", filijale=" + filijale + ", admini=" + admini + "]";
	}

}
