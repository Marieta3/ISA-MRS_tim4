package com.ISAtim4.WebAppSpringAirport.domain;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "korisnik", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "mail"),
		@UniqueConstraint(columnNames = "korisnickoIme") })
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "uloga", discriminatorType = DiscriminatorType.STRING)
public class Korisnik implements UserDetails {
	private static final long serialVersionUID = 5608471345265931458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "ime", nullable = false, length = 30)
	private String ime;
	@Column(name = "prezime", nullable = false, length = 30)
	private String prezime;
	@Column(name = "korisnickoIme", unique = true, nullable = false, length = 30)
	private String korisnickoIme;
	@Column(name = "lozinka", nullable = false, length = 70)
	@JsonBackReference
	private String lozinka;

	@Column(name = "mail", unique = true, nullable = true, length = 50)
	private String mail;
	@Column(name = "adresa", unique = false, nullable = true, length = 50)
	private String adresa;
	@Column(name = "telefon", unique = false, nullable = true, length = 30)
	private String telefon;

	@Column(nullable = true, length = 70)
	private String slika;

	@Column(nullable = true)
	private Boolean enabled = false;

	@Column(name = "last_password_reset_date")
	private Timestamp lastPasswordResetDate;

	// @LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	@Column(nullable = true)
	private Boolean UlogovanPrviPut; // mora prvi put da izmeni lozinku, posle
										// ne

	public Boolean getUlogovanPrviPut() {
		return UlogovanPrviPut;
	}

	public void setUlogovanPrviPut(Boolean ulogovanPrviPut) {
		UlogovanPrviPut = ulogovanPrviPut;
	}

	public String getUloga() {
		return authorities.get(0).getName();
	}

	public Korisnik() {
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

	public Korisnik(Long id, String ime, String prezime, String korisnickoIme,
			String lozinka, String mail, String adresa, String telefon,
			String slika, Boolean enabled) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.mail = mail;
		this.adresa = adresa;
		this.telefon = telefon;
		this.slika = slika;
		this.enabled = enabled;
	}

	public Korisnik(Long id, String ime, String prezime, String korisnickoIme,
			String lozinka, String mail, String slika, boolean enabled) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.mail = mail;
		this.slika = slika;
		this.enabled = false;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", ime=" + ime + ", prezime=" + prezime
				+ ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka
				+ ", mail=" + mail + ", lastPasswordResetDate="
				+ lastPasswordResetDate + ", authorities=" + authorities + "]";
	}

	@Override
	public String getPassword() {
		return lozinka;
	}

	@Override
	public String getUsername() {
		return korisnickoIme;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((UlogovanPrviPut == null) ? 0 : UlogovanPrviPut.hashCode());
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result
				+ ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result
				+ ((korisnickoIme == null) ? 0 : korisnickoIme.hashCode());
		result = prime
				* result
				+ ((lastPasswordResetDate == null) ? 0 : lastPasswordResetDate
						.hashCode());
		result = prime * result + ((lozinka == null) ? 0 : lozinka.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
		result = prime * result + ((slika == null) ? 0 : slika.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
