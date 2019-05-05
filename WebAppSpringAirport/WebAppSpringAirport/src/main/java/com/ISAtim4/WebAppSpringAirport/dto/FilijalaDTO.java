package com.ISAtim4.WebAppSpringAirport.dto;

import java.io.Serializable;

public class FilijalaDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 893964200290503948L;

	private String opis;
	private String adresa;
	private Long idRent;
	private String slika;
	private String telefon;

	public FilijalaDTO() {
	}

	public FilijalaDTO(String opis, String adresa, Long idRent, String slika,
			String telefon) {
		super();
		this.opis = opis;
		this.adresa = adresa;
		this.idRent = idRent;
		this.slika = slika;
		this.telefon = telefon;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Long getIdRent() {
		return idRent;
	}

	public void setIdRent(Long idRent) {
		this.idRent = idRent;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
