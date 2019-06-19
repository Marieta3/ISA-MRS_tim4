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
	/*@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	//@JsonBackReference
	@JsonIgnoreProperties("rezervacije")
	private Sediste sediste;*/
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_soba", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "soba_id", referencedColumnName = "id"))
	private Set<Soba> odabraneSobe=new HashSet<>();
	/*@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("rezervacije")
	private Soba soba;*/
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_vozilo", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vozilo_id", referencedColumnName = "id"))
	private Set<Vozilo> odabranaVozila=new HashSet<>();
	/*@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("rezervacije")
	private Vozilo vozilo;*/
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_korisnik", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "korisnik_id", referencedColumnName = "id"))
	private Set<RegistrovaniKorisnik> korisnici=new HashSet<>(); 
	/*@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("rezervacije")
	private RegistrovaniKorisnik putnik ;*/
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "rezervacija_neregistrovani", joinColumns = @JoinColumn(name = "rezervacija_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "neregistrovani_id", referencedColumnName = "id"))
	private Set<NeregistrovaniPutnik> neregistrovani=new HashSet<>(); 
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = false)
	private Date datumRezervacije;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = true)
	private Date sobaZauzetaOd;
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = true)
	private Date sobaZauzetaDo;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = true)
	private Date voziloZauzetoOd;
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	@Column(nullable = true)
	private Date voziloZauzetoDo;
	
	
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

	

	public Date getSobaZauzetaOd() {
		return sobaZauzetaOd;
	}

	public void setSobaZauzetaOd(Date sobaZauzetaOd) {
		this.sobaZauzetaOd = sobaZauzetaOd;
	}

	public Date getSobaZauzetaDo() {
		return sobaZauzetaDo;
	}

	public void setSobaZauzetaDo(Date sobaZauzetaDo) {
		this.sobaZauzetaDo = sobaZauzetaDo;
	}

	public Date getVoziloZauzetoOd() {
		return voziloZauzetoOd;
	}

	public void setVoziloZauzetoOd(Date voziloZauzetoOd) {
		this.voziloZauzetoOd = voziloZauzetoOd;
	}

	public Date getVoziloZauzetoDo() {
		return voziloZauzetoDo;
	}

	public void setVoziloZauzetoDo(Date voziloZauzetoDo) {
		this.voziloZauzetoDo = voziloZauzetoDo;
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

	public Set<RegistrovaniKorisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(Set<RegistrovaniKorisnik> korisnici) {
		this.korisnici = korisnici;
	}

	@Override
	public String toString() {
		return "Rezervacija [id=" + id + ", cena=" + cena + ", odabranaSedista=" + odabranaSedista + ", odabraneSobe="
				+ odabraneSobe + ", odabranaVozila=" + odabranaVozila + ", korisnici=" + korisnici
				+ ", datumRezervacije=" + datumRezervacije + ", sobaZauzetaOd=" + sobaZauzetaOd + ", sobaZauzetaDo="
				+ sobaZauzetaDo + ", voziloZauzetoOd=" + voziloZauzetoOd + ", voziloZauzetoDo=" + voziloZauzetoDo
				+ ", aktivnaRezervacija=" + aktivnaRezervacija + "]";
	}

	public Set<NeregistrovaniPutnik> getNeregistrovani() {
		return neregistrovani;
	}

	public void setNeregistrovani(Set<NeregistrovaniPutnik> neregistrovani) {
		this.neregistrovani = neregistrovani;
	}

	
	

}
