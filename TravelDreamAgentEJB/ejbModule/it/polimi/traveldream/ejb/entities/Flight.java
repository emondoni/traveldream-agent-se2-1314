package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.client.shared.BasicProductType;
import it.polimi.traveldream.ejb.client.shared.DayOfWeek;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.entities.BasicProduct;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Flight
 *
 */
@Entity
@DiscriminatorValue(value=BasicProductType._FLIGHT)
@Table(name="FLIGHT")
@NamedQueries({@NamedQuery(name=Flight.allAvailable, query="SELECT f FROM Flight f WHERE f.available = true")})
public class Flight extends BasicProduct implements Serializable {
	@Column(name="CALL_SIGN")
	private String callSign;
	@Column(name="DEPARTURE_PLACE")
	private String departurePlace;
	@Temporal(TemporalType.TIME)
	@Column(name="DEPARTURE_TIME")
	private Date departureTime;
	@Column(name="ARRIVAL_PLACE")
	private String arrivalPlace;
	@Temporal(TemporalType.TIME)
	@Column(name="ARRIVAL_TIME")
	private Date arrivalTime;
	@ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "FLIGHT_FREQUENCY", joinColumns = {@JoinColumn(name = "FLIGHT_ID")})
	@Enumerated(value=EnumType.STRING)
	@Column(name="DAY_OF_WEEK")
	private List<DayOfWeek> frequency;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String allAvailable = "Flight.allAvailable";

	public Flight() {
		super();
	}   
	
	public Flight(FlightDTO flightDTO) {
		this.productID = flightDTO.getProductID();
		this.description = flightDTO.getDescription();
		this.price = flightDTO.getPrice();
		this.available = flightDTO.getAvailable();
		this.callSign = flightDTO.getCallSign();
		this.departurePlace = flightDTO.getDeparturePlace();
		this.departureTime = flightDTO.getDepartureTime();
		this.arrivalPlace = flightDTO.getArrivalPlace();
		this.arrivalTime = flightDTO.getArrivalTime();
		this.frequency = flightDTO.getFrequency();
	}
	
	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getDeparturePlace() {
		return this.departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}   
	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}   
	public String getArrivalPlace() {
		return this.arrivalPlace;
	}

	public void setArrivalPlace(String arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}   
	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<DayOfWeek> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<DayOfWeek> frequency) {
		this.frequency = frequency;
	}   
   
}
