package com.ISAtim4.WebAppSpringAirport.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "OCENA")
public class Ocena {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer ocena;
	
	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private Rezervacija rezervacija;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private Hotel hotel;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private Soba soba;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private RentACar rent;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private Vozilo vozilo;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private AvioKompanija avio;

	@JoinColumn(nullable = true)
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private Let let;

	@Column(nullable = true)
	private Date datumOcenjivanja;
	
	@Column(nullable = true )
	private Long korisnik_id;

	public Ocena() {
		super();
		datumOcenjivanja = new Date();
	}

	public Ocena(Long id, Integer ocena, Rezervacija rezervacija, Hotel hotel,
			Soba soba, RentACar rent, Vozilo vozilo, AvioKompanija avio,
			Let let, Date datumOcenjivanja, Long korisnik) {
		super();
		this.id = id;
		this.ocena = ocena;
		this.rezervacija = rezervacija;
		this.hotel = hotel;
		this.soba = soba;
		this.rent = rent;
		this.vozilo = vozilo;
		this.avio = avio;
		this.let = let;
		this.datumOcenjivanja = datumOcenjivanja;
		this.korisnik_id = korisnik;
	}
	
	public Long getKorisnik_id() {
		return korisnik_id;
	}

	public void setKorisnik_id(Long korisnik_id) {
		this.korisnik_id = korisnik_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Soba getSoba() {
		return soba;
	}

	public void setSoba(Soba soba) {
		this.soba = soba;
	}

	public RentACar getRent() {
		return rent;
	}

	public void setRent(RentACar rent) {
		this.rent = rent;
	}

	public Vozilo getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vozilo vozilo) {
		this.vozilo = vozilo;
	}

	public AvioKompanija getAvio() {
		return avio;
	}

	public void setAvio(AvioKompanija avio) {
		this.avio = avio;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Date getDatumOcenjivanja() {
		return datumOcenjivanja;
	}

	public void setDatumOcenjivanja(Date datumOcenjivanja) {
		this.datumOcenjivanja = datumOcenjivanja;
	}
	
	public Rezervacija getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}

	public static double getProsek(List<Ocena> ocene){
		if(ocene.isEmpty()){
			return 0.0;
		}
		int counter = ocene.size();
		double sum = 0.0;
		for (Ocena ocena : ocene) {
			sum += ocena.getOcena();
		}
		BigDecimal big = BigDecimal.valueOf(sum/counter);
		return big.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
