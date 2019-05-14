package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Soba {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer brojKreveta;

	@Column(nullable = false)
	private String opis;

	@Column(nullable = true)
	private Double ocena = 0.0;

	@Column(nullable = true)
	private String slika;

	@Column(nullable = false)
	private boolean rezervisana = false;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonBackReference
	private Hotel hotel;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "soba_usluga", joinColumns = @JoinColumn(name = "soba_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "usluga_id", referencedColumnName = "id"))
	private List<Usluga> usluge;

	public Soba() {
	}

	public Soba(Long id, String opis, double ocena, Integer brojKreveta,
			Hotel hotel, String slika, List<Usluga> usluge, boolean rezervisana) {
		super();
		this.id = id;
		this.opis = opis;
		this.ocena = 0.0;
		this.brojKreveta = brojKreveta;
		this.hotel = hotel;
		this.slika = slika;
		this.usluge = usluge;
		this.rezervisana = rezervisana;
	}

	public List<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(List<Usluga> usluge) {
		this.usluge = usluge;
	}

	public boolean isRezervisana() {
		return rezervisana;
	}

	public void setRezervisana(boolean rezervisana) {
		this.rezervisana = rezervisana;
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

	public Integer getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(Integer brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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

}
