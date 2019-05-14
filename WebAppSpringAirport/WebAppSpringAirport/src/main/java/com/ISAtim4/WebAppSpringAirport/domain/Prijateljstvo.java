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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="PRIJATELJSTVO")
public class Prijateljstvo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegistrovaniKorisnik sender;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegistrovaniKorisnik receiver;
	// if accepted=TRUE and reacted=TRUE => friends
	// if accepted=FALSE and reacted=TRUE => rejected
	// if accepted=FALSE and reacted=FALSE => no reaction yet
	// if accepted=TRUE and reacted=FALSE => impossible
	@Column(nullable = false)
	private Boolean accepted;
	@Column(nullable = false)
	private Boolean reacted;
	@Column(nullable = false)
	private Date datum;

	public Prijateljstvo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegistrovaniKorisnik getSender() {
		return sender;
	}

	public void setSender(RegistrovaniKorisnik sender) {
		this.sender = sender;
	}

	public RegistrovaniKorisnik getReceiver() {
		return receiver;
	}

	public void setReceiver(RegistrovaniKorisnik receiver) {
		this.receiver = receiver;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Boolean getReacted() {
		return reacted;
	}

	public void setReacted(Boolean reacted) {
		this.reacted = reacted;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
