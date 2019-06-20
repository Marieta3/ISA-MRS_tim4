package com.ISAtim4.WebAppSpringAirport.dto;

public class BrzaSobaVoziloDTO {
	private Long let_id;
	private String row_col;
	private Long brz_id;
	
	public BrzaSobaVoziloDTO() {}

	public Long getLet_id() {
		return let_id;
	}

	public void setLet_id(Long let_id) {
		this.let_id = let_id;
	}

	public String getRow_col() {
		return row_col;
	}

	public void setRow_col(String row_col) {
		this.row_col = row_col;
	}

	public Long getBrz_id() {
		return brz_id;
	}

	public void setBrz_id(Long brz_id) {
		this.brz_id = brz_id;
	}
	
	
}
