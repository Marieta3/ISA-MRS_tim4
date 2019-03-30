package com.ISAtim4.WebAppSpringAirport.domain;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AvioKompanija {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String naziv;

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "admin_id")
	private AdminAvio admin;
	
	/*
	private String adresa;
	private String opis;
	
	@JsonManagedReference
	private Set<Destinacija> listaDestinacija;
	
	@JsonManagedReference
	private Set<Let> listaLetova;
	*/
	
	public AvioKompanija() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public AdminAvio getAdmin() {
		return admin;
	}

	public void setAdmin(AdminAvio admin) {
		this.admin = admin;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	

	
}
