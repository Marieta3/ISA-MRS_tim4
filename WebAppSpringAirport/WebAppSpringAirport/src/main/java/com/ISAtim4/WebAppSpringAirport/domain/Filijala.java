package com.ISAtim4.WebAppSpringAirport.domain;


public class Filijala {
	
	private Long id; 
	
	private Adresa adresa;
	private String telefon;
	
	public Filijala(){}
	
	public Filijala(Adresa adresa, String telefon) {
		super();
		this.adresa = adresa;
		this.telefon = telefon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
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
