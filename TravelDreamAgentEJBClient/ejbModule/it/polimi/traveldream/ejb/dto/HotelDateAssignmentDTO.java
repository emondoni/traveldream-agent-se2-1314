package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.*;

public class HotelDateAssignmentDTO {
	@NotNull
	private HotelDTO hotel;
	@NotNull(message="A date must be chosen for this hotel!")
	@Future(message="You must provide a future arrival date for this hotel!")
	private Date arrivalDate;
	@NotNull(message="A date must be chosen for this hotel!")
	@Future(message="You must provide a future departure date for this hotel!")
	private Date departureDate;
	
	public HotelDateAssignmentDTO() {
		super();
	}
	
	public HotelDateAssignmentDTO(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	public HotelDTO getHotel() {
		return hotel;
	}
	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
}
