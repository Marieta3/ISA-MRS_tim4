package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.ArrayList;

public class SobaDTO {
	private String opis;
	private Integer brojKreveta;
	private Long idHotela;
	private String slika;
	private ArrayList<Long> usluge=new ArrayList<>();
	public SobaDTO() {}

	public SobaDTO(String opis, Integer brojKreveta, Long idHotela, String slika, ArrayList<Long> usluge) {
		super();
		this.opis = opis;
		this.brojKreveta = brojKreveta;
		this.idHotela = idHotela;
		this.slika=slika;
		this.usluge=usluge;
	}

	public ArrayList<Long> getUsluge() {
		return usluge;
	}

	public void setUsluge(ArrayList<Long> usluge) {
		this.usluge = usluge;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(Integer brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public Long getIdHotela() {
		return idHotela;
	}

	public void setIdHotela(Long idHotela) {
		this.idHotela = idHotela;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}
	
}
