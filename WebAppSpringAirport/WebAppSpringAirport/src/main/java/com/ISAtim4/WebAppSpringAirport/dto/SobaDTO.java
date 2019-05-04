package com.ISAtim4.WebAppSpringAirport.dto;

public class SobaDTO {
	private String opis;
	private Integer brojKreveta;
	private Long idHotela;
	private String slika;
	
	public SobaDTO() {}

	public SobaDTO(String opis, Integer brojKreveta, Long idHotela, String slika) {
		super();
		this.opis = opis;
		this.brojKreveta = brojKreveta;
		this.idHotela = idHotela;
		this.slika=slika;
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
