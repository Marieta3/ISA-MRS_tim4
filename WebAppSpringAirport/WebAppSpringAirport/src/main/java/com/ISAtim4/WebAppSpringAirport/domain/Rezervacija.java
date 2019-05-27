package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Rezervacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Double cena = 0.0;
	
	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_sediste", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sediste_id", referencedColumnName = "id"))
	private Set<Sediste> odabranaSedista=new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_soba", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "soba_id", referencedColumnName = "id"))
	private Set<Soba> odabraneSobe=new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_vozilo", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vozilo_id", referencedColumnName = "id"))
	private Set<Vozilo> odabranaVozila=new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_korisnik", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "korisnik_id", referencedColumnName = "id"))
	private Set<RegistrovaniKorisnik> korisnici=new HashSet<>(); 
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = false)
	private Date datumRezervacije;
	
	@Column(nullable = false)
	private Boolean aktivnaRezervacija=true; //da li je prosao datum leta ili ne
	
	public Rezervacija() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Set<Sediste> getOdabranaSedista() {
		return odabranaSedista;
	}

	public void setOdabranaSedista(Set<Sediste> odabranaSedista) {
		this.odabranaSedista = odabranaSedista;
	}

	public Set<Soba> getOdabraneSobe() {
		return odabraneSobe;
	}

	public void setOdabraneSobe(Set<Soba> odabraneSobe) {
		this.odabraneSobe = odabraneSobe;
	}

	public Set<Vozilo> getOdabranaVozila() {
		return odabranaVozila;
	}

	public void setOdabranaVozila(Set<Vozilo> odabranaVozila) {
		this.odabranaVozila = odabranaVozila;
	}

	public Date getDatumRezervacije() {
		return datumRezervacije;
	}
	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}
	public Boolean getAktivnaRezervacija() {
		return aktivnaRezervacija;
	}
	public void setAktivnaRezervacija(Boolean aktivnaRezervacija) {
		this.aktivnaRezervacija = aktivnaRezervacija;
	}

	public Set<RegistrovaniKorisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(Set<RegistrovaniKorisnik> korisnici) {
		this.korisnici = korisnici;
	}

	

}
