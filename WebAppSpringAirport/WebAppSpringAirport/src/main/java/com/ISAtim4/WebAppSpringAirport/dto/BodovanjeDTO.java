package com.ISAtim4.WebAppSpringAirport.dto;


public class BodovanjeDTO {
	private int maxBroj;
	private int kmZaBroj;
	
	public BodovanjeDTO(int maxBroj, int kmZaBroj) {
		super();
		this.maxBroj = maxBroj;
		this.kmZaBroj = kmZaBroj;
	}
	public BodovanjeDTO() {
		super();
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
