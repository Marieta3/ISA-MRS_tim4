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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "vozilo", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "tablica") })
public class Vozilo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(nullable = false , length= 50)
	private String proizvodjac;
	@Column(nullable = false , length= 50)
	private String model;
	@Column(nullable = false, length= 10)
	private Integer godina;
	@Column(name = "tablica", nullable = false, unique = true, length= 30)
	private String tablica;
	@Column(nullable = true)
	private Double cena;
	@Column(nullable = false)
	private Integer brojMesta;
	@Column(nullable = false)
	private boolean rezervisano=false;
	
	@Column(nullable = true)
	private Double ocena;

	public boolean isRezervisano() {
		return rezervisano;
	}

	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	//@JsonBackReference
	@JsonIgnoreProperties("vozila")
	private Filijala filijala;

	public Vozilo() {
	}

	public Vozilo(Long id, String proizvodjac, String model, Integer godina,
			String tablica, Double cena, Integer brojMesta,
			RentACar rent_a_car, Filijala filijala) {
		super();
		this.id = id;
		this.proizvodjac = proizvodjac;
		this.model = model;
		this.godina = godina;
		this.tablica = tablica;
		this.cena = cena;
		this.brojMesta = brojMesta;
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

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Integer getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(Integer brojMesta) {
		this.brojMesta = brojMesta;
	}

	public Filijala getFilijala() {
		return filijala;
	}

	public void setFilijala(Filijala filijala) {
		this.filijala = filijala;
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
	
	public Double getOcena() {
		return ocena;
	}

	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}

	@Override
	public String toString() {
		return "Vozilo [id=" + id + ", proizvodjac=" + proizvodjac + ", model="
				+ model + ", godina=" + godina + ", tablica=" + tablica
				+ ", cena=" + cena + "]";
	}

	

}
