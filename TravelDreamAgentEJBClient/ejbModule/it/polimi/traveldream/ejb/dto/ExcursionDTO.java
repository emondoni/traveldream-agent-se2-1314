package it.polimi.traveldream.ejb.dto;

import it.polimi.traveldream.ejb.client.shared.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExcursionDTO extends BasicProductDTO {
	@NotNull
	@Size(min=1, message="The excursion must have a name!")
	private String name;
	@NotNull
	@Size(min=1, message="It is necessary to specify the departure place!")
	private String departurePlace;
	@NotNull(message="Please, specify when the excursion starts!")
	private Date departureTime;
	@NotNull
	@Size(min=1, message="It is necessary to specify the arrival place!")
	private String arrivalPlace;
	@NotNull(message="Please, specify when the excursion ends!")
	private Date arrivalTime;
	@NotNull
	@Size(min=1, message="Please indicate a short list of the places which will be visited during the excursion!")
	private String visits;
	@NotNull
	private List<DayOfWeek> frequency;
	
	public ExcursionDTO() {
		super();
	}
	
	public String getFrequencyToString() {
		String frequencyString = "";
		for (Iterator<DayOfWeek> iterator = frequency.iterator(); iterator.hasNext(); ) {
			frequencyString = frequencyString + iterator.next().getShortLabel();
			if(iterator.hasNext()) frequencyString = frequencyString + ", ";
			else return frequencyString;
		}
		
		return "One-shot.";
	}
	
	public String getFrequencyProspect() {
		String frequencyProspect = "";
		if (frequency.size() > 0) {
			frequencyProspect = "The excursion is held every week on: ";
			for (Iterator<DayOfWeek> iterator = frequency.iterator(); iterator.hasNext(); ) {
				frequencyProspect = frequencyProspect.concat(iterator.next().getLabel() + "s");
				if(iterator.hasNext()) frequencyProspect = frequencyProspect.concat(", ");
			}

			return frequencyProspect.concat(".");
		}
		else return "The excursion is a one-time offer.";
	}
	
	public List<Integer> getFrequencyToInteger() {
		List<Integer> toReturn = new ArrayList<Integer>();
		for (DayOfWeek day : frequency)
			toReturn.add(day.getConversion());
		
		return toReturn;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeparturePlace() {
		return departurePlace;
	}
	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalPlace() {
		return arrivalPlace;
	}
	public void setArrivalPlace(String arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getVisits() {
		return visits;
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
	public String toString() {
		return this.productID.toString();
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof ExcursionDTO)
			return ((ExcursionDTO)o).getProductID().equals(this.productID);
		
		return false;
	}
}
