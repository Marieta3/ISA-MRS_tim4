package com.ISAtim4.WebAppSpringAirport.dto;

public class OcenaDTO {
	private String entitetId; // npr soba_1, let_1 , vozilo_1,avio_1, hotel_1,
								// rent_1,
	private int ocena;
	private int rezervacijaId;

	public OcenaDTO() {
		super();
	}

	public OcenaDTO(String entitetId, int ocena, int rezervacijaId) {
		super();
		this.entitetId = entitetId;
		this.ocena = ocena;
		this.rezervacijaId = rezervacijaId;
	}

	public String getEntitetId() {
		return entitetId;
	}

	public void setEntitetId(String entitetId) {
		this.entitetId = entitetId;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public int getRezervacijaId() {
		return rezervacijaId;
	}

	public void setRezervacijaId(int rezervacijaId) {
		this.rezervacijaId = rezervacijaId;
	}

}
