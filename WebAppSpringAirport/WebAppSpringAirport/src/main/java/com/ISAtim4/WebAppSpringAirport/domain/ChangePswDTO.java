package com.ISAtim4.WebAppSpringAirport.domain;

public class ChangePswDTO {
	private String oldPsw;
	private String newPsw;
	private String confirmPsw;
	
	public ChangePswDTO() {}

	public ChangePswDTO(String oldPsw, String newPsw, String confirmPsw) {
		super();
		this.oldPsw = oldPsw;
		this.newPsw = newPsw;
		this.confirmPsw = confirmPsw;
	}

	public String getOldPsw() {
		return oldPsw;
	}

	public void setOldPsw(String oldPsw) {
		this.oldPsw = oldPsw;
	}

	public String getNewPsw() {
		return newPsw;
	}

	public void setNewPsw(String newPsw) {
		this.newPsw = newPsw;
	}

	public String getConfirmPsw() {
		return confirmPsw;
	}

	public void setConfirmPsw(String confirmPsw) {
		this.confirmPsw = confirmPsw;
	}
	
	
}
