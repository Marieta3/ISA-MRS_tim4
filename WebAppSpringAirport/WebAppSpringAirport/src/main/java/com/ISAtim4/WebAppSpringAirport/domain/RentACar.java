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
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RentACar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(nullable = false)
	private String naziv;
	@Column(nullable = false)
	private String adresa;
	@Column(nullable = false)
	private String opis;
	@Column(nullable = true)
	private Double ocena=0.0;
	
	@Column(nullable = false)
	private double coord1;
	
	@Column(nullable = false)
	private double coord2;
	@Column(nullable = true)
	public String slika;

	@OneToMany(mappedBy = "rentACar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonManagedReference
	@JsonIgnoreProperties("rentACar")
	private Set<Filijala> filijale = new HashSet<>();
	@OneToMany(mappedBy = "rentACar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<AdminRent> admini = new HashSet<>();

	public RentACar() {
	}

	public RentACar(Long id, String naziv, String adresa, String opis,
			Set<Vozilo> vozila, Set<Filijala> filijale, Set<AdminRent> admini,
			String slika, Double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.filijale = filijale;
		this.admini = admini;
		this.slika = slika;
	}

	public RentACar(RentACar r) {
		super();
		this.id = r.getId();
		this.naziv = r.getNaziv();
		this.adresa = r.getAdresa();
		this.opis = r.getOpis();
		this.ocena = r.getOcena();
		this.filijale = r.getFilijale();
		this.admini = r.getAdmini();
		this.slika = r.getSlika();
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
}
