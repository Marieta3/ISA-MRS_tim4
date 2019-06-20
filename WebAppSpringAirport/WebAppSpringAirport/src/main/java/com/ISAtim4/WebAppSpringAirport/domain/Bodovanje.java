package com.ISAtim4.WebAppSpringAirport.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bodovanje")
public class Bodovanje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private int maxBroj;
	
	@Column(nullable = true)
	private int kmZaBroj;

	public Bodovanje() {
		super();
		maxBroj = 0;
		kmZaBroj = 100;
	}

	public Bodovanje(Long id, int maxBroj, int kmZaBroj) {
		super();
		this.id = id;
		this.maxBroj = maxBroj;
		this.kmZaBroj = kmZaBroj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMaxBroj() {
		return maxBroj;
	}

	public void setMaxBroj(int maxBroj) {
		this.maxBroj = maxBroj;
	}

	public int getKmZaBroj() {
		return kmZaBroj;
	}

	public void setKmZaBroj(int kmZaBroj) {
		this.kmZaBroj = kmZaBroj;
	}

}
