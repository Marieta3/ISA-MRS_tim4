package com.ISAtim4.WebAppSpringAirport.domain;

import java.io.Serializable;

public class Vozilo implements Serializable {
	
	private Long id;
	
	private String proizvodjac;
	private String model;
	private Integer godina;
	private String tablica;

	private double cena;
	private Integer brojMesta;
	
	public Vozilo() {
		super();
		this.id = null;
		this.proizvodjac = "";
		this.model = "";
		this.godina = null;
		this.tablica = "";
		this.cena = 0;
		this.brojMesta = 0;
	}

	public Vozilo(Long id, String proizvodjac, String model, Integer godina,
			String tablica, double cena,Integer brojMesta) {
		super();
		this.id = id;
		this.proizvodjac = proizvodjac;
		this.model = model;
		this.godina = godina;
		this.tablica = tablica;
		this.cena = cena;
		this.brojMesta = brojMesta;
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
