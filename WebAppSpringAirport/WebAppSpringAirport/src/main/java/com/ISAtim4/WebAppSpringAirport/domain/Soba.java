package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Soba {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String opis;
	
	@Column(nullable = true)
	private double ocena;
	
	@Column(nullable = false)
	private Integer brojKreveta;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Hotel hotel;
	
	public Soba() {}
	
	public Soba(Long id, String opis, double ocena, Integer brojKreveta,
			Hotel hotel) {
		super();
		this.id = id;
		this.opis = opis;
		this.ocena = ocena;
		this.brojKreveta = brojKreveta;
		this.hotel = hotel;
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
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}


}
