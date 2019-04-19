package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Filijala {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String adresa;
	@Column(nullable = false)
	private String telefon;
	
	@OneToMany(mappedBy = "filijala", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Vozilo> vozila = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private RentACar rent_a_car;

	public Filijala() {
	}

	public Filijala(Long id, String adresa, String telefon, Set<Vozilo> vozila,
			RentACar rent_a_car) {
		super();
		this.id = id;
		this.adresa = adresa;
		this.telefon = telefon;
		this.vozila = vozila;
		this.rent_a_car = rent_a_car;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Set<Vozilo> getVozila() {
		return vozila;
	}

	public void setVozila(Set<Vozilo> vozila) {
		this.vozila = vozila;
	}

	public RentACar getRent_a_car() {
		return rent_a_car;
	}

	public void setRent_a_car(RentACar rent_a_car) {
		this.rent_a_car = rent_a_car;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filijala other = (Filijala) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Filijala [id=" + id + ", adresa=" + adresa + ", telefon="
				+ telefon + "]";
	}

}
