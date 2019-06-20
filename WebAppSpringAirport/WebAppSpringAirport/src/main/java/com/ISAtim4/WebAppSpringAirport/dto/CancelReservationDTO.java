package com.ISAtim4.WebAppSpringAirport.dto;

public class CancelReservationDTO {
	private boolean flightID;
	private boolean hotelID;
	private boolean carID;
	
	public CancelReservationDTO() {
		super();
	}
	public CancelReservationDTO(boolean flightID, boolean hotelID, boolean carID) {
		super();
		this.flightID = flightID;
		this.hotelID = hotelID;
		this.carID = carID;
	}
	public boolean isFlightID() {
		return flightID;
	}
	public void setFlightID(boolean flightID) {
		this.flightID = flightID;
	}
	public boolean isHotelID() {
		return hotelID;
	}
	public void setHotelID(boolean hotelID) {
		this.hotelID = hotelID;
	}
	public boolean isCarID() {
		return carID;
	}
	public void setCarID(boolean carID) {
		this.carID = carID;
	}
	
	
	
}
