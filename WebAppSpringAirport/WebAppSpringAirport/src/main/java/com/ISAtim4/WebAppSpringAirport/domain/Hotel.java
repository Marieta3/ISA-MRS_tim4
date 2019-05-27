package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String naziv;
	@Column(nullable = false)
	private String adresa;
	@Column(nullable = false)
	private String opis;
	@Column(nullable = true)
	private Double ocena=0.0;
	
	@Column(nullable = true)
	private String slika;
	
	@Column(nullable = false)
	private double coord1;
	
	@Column(nullable = false)
	private double coord2;
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonManagedReference
	@JsonIgnoreProperties("hotel")
	private Set<Soba> sobe = new HashSet<>();
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Usluga> usluge = new HashSet<>();
	
	@OneToMany(mappedBy="hotel", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<AdminHotel> admini_hotela=new HashSet<>();
	
	public Set<AdminHotel> getAdmini_hotela() {
		return admini_hotela;
	}

	public void setAdmini_hotela(Set<AdminHotel> admini_hotela) {
		this.admini_hotela = admini_hotela;
	}

	public Hotel() {}
	
	public Hotel(Long id, String naziv, String adresa, String opis,
			Double ocena, Set<Soba> sobe, Set<Usluga> usluge, Set<AdminHotel> admini_hotela, String slika, double coord1, double coord2) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.sobe = sobe;
		this.usluge = usluge;
		this.admini_hotela=admini_hotela;
		this.slika = slika;
		this.coord1=coord1;
		this.coord2=coord2;
	}

	public double getCoord1() {
		return coord1;
	}

	public void setCoord1(double coord1) {
		this.coord1 = coord1;
	}

	public double getCoord2() {
		return coord2;
	}

	public void setCoord2(double coord2) {
		this.coord2 = coord2;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
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

	public Double getOcena() {
		return ocena;
	}

	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}

	public Set<Soba> getSobe() {
		return sobe;
	}

	public void setSobe(Set<Soba> sobe) {
		this.sobe = sobe;
	}

	public Set<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}


}
