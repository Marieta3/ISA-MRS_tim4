package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.Date;

public class BrzaRezervacijaDTO {
	private Long id;
	private double novaCena;
	private Date startDatum;
	private Date endDatum;
	private String row_col;
	
	public BrzaRezervacijaDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRow_col() {
		return row_col;
	}

	public void setRow_col(String row_col) {
		this.row_col = row_col;
	}
	
}
