package com.ISAtim4.WebAppSpringAirport.dto;

import java.util.ArrayList;
import java.util.Date;

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
	private ArrayList<Long> pozvani_prijatelji=new ArrayList<>();
	private ArrayList<String> neregistrovani=new ArrayList<>();
	
	private double ukupnaCena=0.0;
	private Date sobaOD;
	//private Date sobaDO;
	private int brojNocenja;
	private Date voziloOD;
	private Date voziloDO;
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
	public Date getSobaOD() {
		return sobaOD;
	}
	public void setSobaOD(Date sobaOD) {
		this.sobaOD = sobaOD;
	}
	
	public Date getVoziloOD() {
		return voziloOD;
	}
	public void setVoziloOD(Date voziloOD) {
		this.voziloOD = voziloOD;
	}
	public Date getVoziloDO() {
		return voziloDO;
	}
	public void setVoziloDO(Date voziloDO) {
		this.voziloDO = voziloDO;
	}
	public int getBrojNocenja() {
		return brojNocenja;
	}
	public void setBrojNocenja(int brojNocenja) {
		this.brojNocenja = brojNocenja;
	}
	public ArrayList<Long> getPozvani_prijatelji() {
		return pozvani_prijatelji;
	}
	public void setPozvani_prijatelji(ArrayList<Long> pozvani_prijatelji) {
		this.pozvani_prijatelji = pozvani_prijatelji;
	}
	@Override
	public String toString() {
		return "RezervacijaDTO [id_leta=" + id_leta + ", sedista=" + sedista + ", sobe=" + sobe + ", vozila=" + vozila
				+ ", pozvani_prijatelji=" + pozvani_prijatelji + ", ukupnaCena=" + ukupnaCena + ", sobaOD=" + sobaOD
				+ ", brojNocenja=" + brojNocenja + ", voziloOD=" + voziloOD + ", voziloDO=" + voziloDO + "]";
	}
	public ArrayList<String> getNeregistrovani() {
		return neregistrovani;
	}
	public void setNeregistrovani(ArrayList<String> neregistrovani) {
		this.neregistrovani = neregistrovani;
	}
	
	
}
