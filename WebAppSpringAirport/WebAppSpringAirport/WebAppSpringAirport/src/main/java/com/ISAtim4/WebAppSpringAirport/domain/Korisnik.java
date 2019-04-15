package com.ISAtim4.WebAppSpringAirport.domain;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy=SINGLE_TABLE)

@DiscriminatorColumn(name="uloga", discriminatorType=STRING)
public abstract class Korisnik {
	@Id
	@GeneratedValue
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	@Column(nullable=false)
	private String ime;
	@Column(nullable=false)
	private String prezime;
	@Column(nullable=false)
	private String korisnickoIme;
	@Column(nullable=false)
	private String lozinka;
	
	@Column(nullable=false)
	private String mail;
	
	public Korisnik() {	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public Korisnik(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String mail) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.mail = mail;
	}
}
