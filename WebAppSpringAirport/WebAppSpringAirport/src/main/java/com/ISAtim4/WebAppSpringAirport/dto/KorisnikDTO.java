package com.ISAtim4.WebAppSpringAirport.dto;

public class KorisnikDTO {
	public String ime;
	public String prezime;
	public String korisnickoIme;
	public String lozinka;
	public String mail;
	public String uloga;
	public String adminOf;
	public String adresa;
	public String telefon;
	public String slika;

	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(String ime, String prezime, String korisnickoIme,
			String lozinka, String mail, String uloga, String adminOf,
			String adresa, String telefon, String slika) {
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.mail = mail;
		this.uloga = uloga;
		this.adminOf = adminOf;
		this.adresa = adresa;
		this.telefon = telefon;
		this.slika = slika;
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

	public String getAdminOf() {
		return adminOf;
	}

	public void setAdminOf(String adminOf) {
		this.adminOf = adminOf;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	
}
