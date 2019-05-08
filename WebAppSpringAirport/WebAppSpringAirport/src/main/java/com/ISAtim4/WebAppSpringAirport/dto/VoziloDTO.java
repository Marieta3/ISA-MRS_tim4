package com.ISAtim4.WebAppSpringAirport.dto;

import java.io.Serializable;

public class VoziloDTO implements Serializable {
	private static final long serialVersionUID = -74440139724884027L;

	private String proizvodjac;
	private String model;
	private Integer godina;
	private String tablica;
	private Double cena;
	private Integer brojMesta;
	private Long filijala_id;

	public VoziloDTO() {
		super();
	}

	public VoziloDTO(String proizvodjac, String model, Integer godina,
			String tablica, Double cena, Integer brojMesta, Long filijala_id) {
		super();
		this.proizvodjac = proizvodjac;
		this.model = model;
		this.godina = godina;
		this.tablica = tablica;
		this.cena = cena;
		this.brojMesta = brojMesta;
		this.filijala_id = filijala_id;
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

	public Long getFilijala_id() {
		return filijala_id;
	}

	public void setFilijala_id(Long filijala_id) {
		this.filijala_id = filijala_id;
	}

}
