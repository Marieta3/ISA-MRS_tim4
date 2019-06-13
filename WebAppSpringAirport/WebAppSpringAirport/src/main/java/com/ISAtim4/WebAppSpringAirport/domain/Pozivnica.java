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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pozivnica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	//@JsonBackReference
	@JsonIgnoreProperties("listaPozivnica")
	private RegistrovaniKorisnik koSalje;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	//@JsonIgnoreProperties("listaPozivnica")
	private RegistrovaniKorisnik komeSalje;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	//@JsonBackReference
	@JsonIgnoreProperties("listaPozivnica")
	private Rezervacija rezervacija;
	
	@Column(nullable = false)
	private Boolean prihvacen; //da li je prihvacen ili nije
	@Column(nullable = false)
	private Boolean reagovanoNaPoziv; //da li je isteklo vreme
	@Column(nullable = false)
	private Date datumSlanja;
	
	public Pozivnica() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RegistrovaniKorisnik getKoSalje() {
		return koSalje;
	}
	public void setKoSalje(RegistrovaniKorisnik koSalje) {
		this.koSalje = koSalje;
	}
	public RegistrovaniKorisnik getKomeSalje() {
		return komeSalje;
	}
	public void setKomeSalje(RegistrovaniKorisnik k) {
		this.komeSalje = k;
	}
	public Rezervacija getRezervacija() {
		return rezervacija;
	}
	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}
	public Boolean getPrihvacen() {
		return prihvacen;
	}
	public void setPrihvacen(Boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
	public Boolean getReagovanoNaPoziv() {
		return reagovanoNaPoziv;
	}
	public void setReagovanoNaPoziv(Boolean reagovanoNaPoziv) {
		this.reagovanoNaPoziv = reagovanoNaPoziv;
	}
	public Date getDatumSlanja() {
		return datumSlanja;
	}
	public void setDatumSlanja(Date datumSlanja) {
		this.datumSlanja = datumSlanja;
	}
	
	
}
