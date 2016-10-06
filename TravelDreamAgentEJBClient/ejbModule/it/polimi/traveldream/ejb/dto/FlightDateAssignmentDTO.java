package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.*;

public class FlightDateAssignmentDTO {
	@NotNull
	private FlightDTO flight;
	@NotNull(message="A date must be chosen for this flight!")
	@Future(message="You must choose a future date for this flight!")
	private Date date;
	
	public FlightDateAssignmentDTO() {
		super();
	}
	
	public FlightDateAssignmentDTO(FlightDTO flight) {
		this.flight = flight;
	}
	
	public FlightDTO getFlight() {
		return flight;
	}
	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
