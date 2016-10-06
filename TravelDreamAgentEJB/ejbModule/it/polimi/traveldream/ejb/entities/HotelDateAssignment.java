package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.beans.ConversionHelper;
import it.polimi.traveldream.ejb.dto.HotelDateAssignmentDTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

/**
 * Entity implementation class for Entity: HotelDateAssignment
 *
 */

@Embeddable
public class HotelDateAssignment implements Serializable {
	@JoinColumn(name="HOTEL_ID", referencedColumnName="PRODUCT_ID")
	private Hotel hotel;
	@Column(name="ARRIVAL_DATE")
//	@Temporal(TemporalType.DATE)
	private java.sql.Date arrivalDate;
	@Column(name="DEPARTURE_DATE")
//	@Temporal(TemporalType.DATE)
	private java.sql.Date departureDate;
	private static final long serialVersionUID = 1L;

	public HotelDateAssignment() {
		super();
	}   
	
	public HotelDateAssignment(HotelDateAssignmentDTO daDTO) {
		this.hotel = (Hotel)ConversionHelper.bpToEntity(daDTO.getHotel());
		this.arrivalDate = ConversionHelper.toSQLDate(daDTO.getArrivalDate());
		this.departureDate = ConversionHelper.toSQLDate(daDTO.getDepartureDate());
	}
	
	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}   
	public Date getArrivalDate() {
		return this.arrivalDate;
	}

	public void setArrivalDate(java.sql.Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}   
	public Date getDepartureDate() {
		return this.departureDate;
	}

	public void setDepartureDate(java.sql.Date departureDate) {
		this.departureDate = departureDate;
	}
   
}
