package com.ISAtim4.WebAppSpringAirport.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RentACar implements Serializable {
	
	private Long id;
	private String naziv;
	private Adresa adresa;
	private String opis;
	private double ocena;
	private Set<Vozilo> vozila;
	private Set<Filijala> filijale;
	
	public RentACar() {
		super();
		this.id = null;
		this.naziv = "";
		this.adresa = new Adresa();
		this.opis = "";
		this.ocena = 0;
		this.vozila= new HashSet<Vozilo>();
		this.filijale = new HashSet<Filijala>();
	}
	
	public RentACar(Long id, String naziv, Adresa adresa, String opis,
			double ocena, Set<Vozilo> vozila, Set<Filijala> filijale) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
		this.vozila = vozila;
		this.filijale = filijale;
	}
	
	public RentACar(RentACar r) {
		super();
		this.id = r.getId();
		this.naziv = r.getNaziv();
		this.adresa = r.getAdresa();
		this.opis = r.getOpis();
		this.ocena = r.getOcena();
		this.vozila = r.getVozila();
		this.filijale = r.getFilijale();
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
	public Adresa getAdresa() {
		return adresa;
	}
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
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
	public Set<Vozilo> getVozila() {
		return vozila;
	}
	public void setVozila(Set<Vozilo> vozila) {
		this.vozila = vozila;
	}
	public Set<Filijala> getFilijale() {
		return filijale;
	}
	public void setFilijale(Set<Filijala> filijale) {
		this.filijale = filijale;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result
				+ ((filijale == null) ? 0 : filijale.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ocena);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((vozila == null) ? 0 : vozila.hashCode());
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
		RentACar other = (RentACar) obj;
		if (adresa == null) {
			if (other.adresa != null)
				return false;
		} else if (!adresa.equals(other.adresa))
			return false;
		if (filijale == null) {
			if (other.filijale != null)
				return false;
		} else if (!filijale.equals(other.filijale))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (Double.doubleToLongBits(ocena) != Double
				.doubleToLongBits(other.ocena))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (vozila == null) {
			if (other.vozila != null)
				return false;
		} else if (!vozila.equals(other.vozila))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RentACar [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa
				+ ", opis=" + opis + ", ocena=" + ocena + "]";
	}
	
	
	
	
}
