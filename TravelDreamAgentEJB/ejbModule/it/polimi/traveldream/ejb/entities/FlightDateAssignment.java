package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.beans.ConversionHelper;
import it.polimi.traveldream.ejb.dto.FlightDateAssignmentDTO;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

/**
 * Entity implementation class for Entity: FlightDateAssignment
 *
 */

@Embeddable
public class FlightDateAssignment implements Serializable {
	@JoinColumn(name="FLIGHT_ID", referencedColumnName="PRODUCT_ID")
	private Flight flight;
//	@Temporal(TemporalType.DATE)
	private java.sql.Date date;
	private static final long serialVersionUID = 1L;

	public FlightDateAssignment() {
		super();
	}
	
	public FlightDateAssignment(FlightDateAssignmentDTO daDTO) {
		this.flight = (Flight)ConversionHelper.bpToEntity(daDTO.getFlight());
		this.date = ConversionHelper.toSQLDate(daDTO.getDate());
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}
   
}
