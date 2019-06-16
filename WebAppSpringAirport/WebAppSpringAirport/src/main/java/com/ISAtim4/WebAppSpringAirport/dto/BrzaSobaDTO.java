package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class BrzaSobaDTO {
	private Long idSobe;
	private double novaCena;
	private Date startDatum;
	private Date endDatum;
	
	public BrzaSobaDTO() {}

	public Long getIdSobe() {
		return idSobe;
	}

	public void setIdSobe(Long idSobe) {
		this.idSobe = idSobe;
	}

	public double getNovaCena() {
		return novaCena;
	}

	public void setNovaCena(double novaCena) {
		this.novaCena = novaCena;
	}

	public Date getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(Date startDatum) {
		this.startDatum = startDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
		this.endDatum = endDatum;
	}
	
}
