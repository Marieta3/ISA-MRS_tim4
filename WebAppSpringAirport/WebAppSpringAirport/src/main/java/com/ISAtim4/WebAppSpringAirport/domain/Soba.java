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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private Double ocena=0.0;
	
	
	
	@Column(nullable=true)
	private String slika;
	
	@Column(nullable=false)
	private boolean rezervisana=false;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonBackReference
	private Hotel hotel;
	
	@OneToMany(mappedBy="soba", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference(value="soba-usluga")
	private Set<Usluga> usluge=new HashSet<>();
	
	public Soba() {}
	
	public Soba(Long id, String opis, double ocena, Integer brojKreveta,
			Hotel hotel, String slika, Set<Usluga> usluge, boolean rezervisana) {
		super();
		this.id = id;
		this.opis = opis;
		this.ocena = 0.0;
		this.brojKreveta = brojKreveta;
		this.hotel = hotel;
		this.slika=slika;
		this.usluge=usluge;
		this.rezervisana=rezervisana;
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
