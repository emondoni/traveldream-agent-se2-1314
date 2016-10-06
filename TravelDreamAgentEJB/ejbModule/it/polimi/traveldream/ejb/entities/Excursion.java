package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.client.shared.BasicProductType;
import it.polimi.traveldream.ejb.client.shared.DayOfWeek;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Excursion
 *
 */
@Entity
@DiscriminatorValue(value=BasicProductType._EXCURSION)
@Table(name="EXCURSION")
@NamedQueries({@NamedQuery(name=Excursion.allAvailable, query="SELECT e FROM Excursion e WHERE e.available = true")})
public class Excursion extends BasicProduct implements Serializable {
	private String name;
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
	@Lob
	private String visits;
	@ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "EXCURSION_FREQUENCY", joinColumns = {@JoinColumn(name = "EXCURSION_ID")})
	@Enumerated(value=EnumType.STRING)
	@Column(name="DAY_OF_WEEK")
	private List<DayOfWeek> frequency;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String allAvailable = "Excursion.allAvailable";

	public Excursion() {
		super();
	}   
	
	public Excursion(ExcursionDTO excursionDTO) {
		this.productID = excursionDTO.getProductID();
		this.description = excursionDTO.getDescription();
		this.price = excursionDTO.getPrice();
		this.available = excursionDTO.getAvailable();
		this.name = excursionDTO.getName();
		this.departurePlace = excursionDTO.getDeparturePlace();
		this.departureTime = excursionDTO.getDepartureTime();
		this.arrivalPlace = excursionDTO.getArrivalPlace();
		this.arrivalTime = excursionDTO.getArrivalTime();
		this.visits = excursionDTO.getVisits();
		this.frequency = excursionDTO.getFrequency();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	public String getVisits() {
		return this.visits;
	}

	public void setVisits(String visits) {
		this.visits = visits;
	}

	public List<DayOfWeek> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<DayOfWeek> frequency) {
		this.frequency = frequency;
	}
   
}
