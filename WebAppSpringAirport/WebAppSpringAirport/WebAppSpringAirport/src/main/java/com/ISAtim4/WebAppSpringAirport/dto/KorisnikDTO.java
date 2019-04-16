package com.ISAtim4.WebAppSpringAirport.dto;

public class KorisnikDTO {
	public String username;
	public String password;
	
	public KorisnikDTO() {
		super();
	}
	
	public KorisnikDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	
	
}
