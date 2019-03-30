package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vozilo {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String proizvodjac;
	@Column(nullable = false)
	private String model;
	@Column(nullable = false)
	private Integer godina;
	@Column(nullable = false)
	private String tablica;
	@Column(nullable = false)
	private Double cena;
	@Column(nullable = false)
	private Integer brojMesta;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RentACar rent_a_car;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RentACar filijala;

	public Vozilo() {
	}

	public Vozilo(Long id, String proizvodjac, String model, Integer godina,
			String tablica, Double cena, Integer brojMesta,
			RentACar rent_a_car, RentACar filijala) {
		super();
		this.id = id;
		this.proizvodjac = proizvodjac;
		this.model = model;
		this.godina = godina;
		this.tablica = tablica;
		this.cena = cena;
		this.brojMesta = brojMesta;
		this.rent_a_car = rent_a_car;
		this.filijala = filijala;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public String getTablica() {
		return tablica;
	}

	public void setTablica(String tablica) {
		this.tablica = tablica;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Integer getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(Integer brojMesta) {
		this.brojMesta = brojMesta;
	}

	public RentACar getRent_a_car() {
		return rent_a_car;
	}

	public void setRent_a_car(RentACar rent_a_car) {
		this.rent_a_car = rent_a_car;
	}

	public RentACar getFilijala() {
		return filijala;
	}

	public void setFilijala(RentACar filijala) {
		this.filijala = filijala;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tablica == null) ? 0 : tablica.hashCode());
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
		Vozilo other = (Vozilo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tablica == null) {
			if (other.tablica != null)
				return false;
		} else if (!tablica.equals(other.tablica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vozilo [id=" + id + ", proizvodjac=" + proizvodjac + ", model="
				+ model + ", godina=" + godina + ", tablica=" + tablica
				+ ", cena=" + cena + "]";
	}

}
