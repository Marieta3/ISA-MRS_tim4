package com.ISAtim4.WebAppSpringAirport.domain;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class AvioKompanija {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String naziv;

	@OneToMany(mappedBy="avio_kompanija",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<AdminAvio> admin= new HashSet<>();
	
	@OneToMany(mappedBy="avio_kompanija",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Destinacija> listaDestinacija= new HashSet<>();
	
	@OneToMany(mappedBy="avio_kompanija",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Let> listaLetova= new HashSet<>();
	
	@Column(nullable=false)
	private String adresa;
	
	@Column(nullable=false)
	private String opis;
	
	@Column(nullable=true)
	private String slika;
	
	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Set<AdminAvio> getAdmin() {
		return admin;
	}

	public void setAdmin(Set<AdminAvio> admin) {
		this.admin = admin;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<Destinacija> getListaDestinacija() {
		return listaDestinacija;
	}

	public void setListaDestinacija(Set<Destinacija> listaDestinacija) {
		this.listaDestinacija = listaDestinacija;
	}

	public Set<Let> getListaLetova() {
		return listaLetova;
	}

	public void setListaLetova(Set<Let> listaLetova) {
		this.listaLetova = listaLetova;
	}
	
	
	public AvioKompanija() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public AvioKompanija(Long id, String naziv, Set<AdminAvio> admin, Set<Destinacija> listaDestinacija,
			Set<Let> listaLetova, String adresa, String opis,String slika) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.admin = admin;
		this.listaDestinacija = listaDestinacija;
		this.listaLetova = listaLetova;
		this.adresa = adresa;
		this.opis = opis;
		this.slika = slika;
	}

	

	
}
