package com.ISAtim4.WebAppSpringAirport.domain;

public abstract class Korisnik {
	private Long id;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private String mail;
	private String uloga;
	
	public Korisnik() {	}
	
	public Korisnik(Long id, String ime, String prezime, String korisnickoIme,
			String lozinka, String mail, String uloga) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.mail = mail;
		this.uloga = uloga;
	}
		
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
	public String getUloga() {
		return uloga;
	}
	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

}
