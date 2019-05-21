package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.ArrayList;

public class RezervacijaDTO {
	private Long id_leta;
	public Long getId_leta() {
		return id_leta;
	}
	public void setId_leta(Long id_leta) {
		this.id_leta = id_leta;
	}
	private ArrayList<String> sedista=new ArrayList<>();
	private ArrayList<Long> sobe=new ArrayList<>();
	private ArrayList<Long> vozila=new ArrayList<>();
	private double ukupnaCena=0.0;
	public double getUkupnaCena() {
		return ukupnaCena;
	}
	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}
	public RezervacijaDTO() {
		
	}
	public ArrayList<String> getSedista() {
		return sedista;
	}
	public void setSedista(ArrayList<String> sedista) {
		this.sedista = sedista;
	}
	public ArrayList<Long> getSobe() {
		return sobe;
	}
	public void setSobe(ArrayList<Long> sobe) {
		this.sobe = sobe;
	}
	public ArrayList<Long> getVozila() {
		return vozila;
	}
	public void setVozila(ArrayList<Long> vozila) {
		this.vozila = vozila;
	}
	
	
}
