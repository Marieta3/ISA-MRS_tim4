package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class BrzaSoba {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("brze_sobe")
	private Soba soba;
	
	@Column(nullable = false)
	private Date start_date;
	
	@Column(nullable = false)
	private Date end_date;
	
	@Column(nullable=false)
	private double nova_cena;
	
	@Column(nullable=false)
	private boolean zauzeto=false;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("brza_soba")
	private Sediste sediste;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("brza_soba")
	private RegistrovaniKorisnik putnik;
	
	@Column(nullable=true)
	private Date datumRezervacije;
	
	public BrzaSoba() {
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Soba getSoba() {
		return soba;
	}

	public void setSoba(Soba soba) {
		this.soba = soba;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public double getNova_cena() {
		return nova_cena;
	}

	public void setNova_cena(double nova_cena) {
		this.nova_cena = nova_cena;
	}
	public boolean isZauzeto() {
		return zauzeto;
	}
	public void setZauzeto(boolean zauzeto) {
		this.zauzeto = zauzeto;
	}
	public Sediste getSediste() {
		return sediste;
	}
	public void setSediste(Sediste sediste) {
		this.sediste = sediste;
	}
	public RegistrovaniKorisnik getPutnik() {
		return putnik;
	}
	public void setPutnik(RegistrovaniKorisnik putnik) {
		this.putnik = putnik;
	}
	public Date getDatumRezervacije() {
		return datumRezervacije;
	}
	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}
	@Override
	public String toString() {
		return "BrzaSoba [id=" + id + ", soba=" + soba + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", nova_cena=" + nova_cena + ", zauzeto=" + zauzeto + ", sediste=" + sediste + ", putnik=" + putnik
				+ ", datumRezervacije=" + datumRezervacije + "]";
	}
	
	
}
