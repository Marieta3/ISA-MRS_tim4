package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Destinacija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(nullable = false)
	private String adresa;

	@Column(nullable = false)
	private String slika;

	/*
	 * @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	 * 
	 * @JsonBackReference private AvioKompanija avio_kompanija;
	 * 
	 * public AvioKompanija getAvio_kompanija() { return avio_kompanija; }
	 * 
	 * public void setAvio_kompanija(AvioKompanija avio_kompanija) {
	 * this.avio_kompanija = avio_kompanija; }
	 */

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Destinacija(Long id, String adresa, String slika) {
		super();
		this.id = id;
		this.adresa = adresa;
		this.slika = slika;
		// this.avio_kompanija = avio_kompanija;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Destinacija() {
		super();
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

}
