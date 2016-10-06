package it.polimi.traveldream.ejb.dto;

import it.polimi.traveldream.ejb.client.shared.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.*;

public class FlightDTO extends BasicProductDTO {
	@NotNull(message="You must specify the call sign for this flight!")
	@Pattern(regexp="[a-zA-Z][a-zA-Z][0-9][0-9]*", message="The call sign you entered is not valid, please check.")
	private String callSign;
	@NotNull
	@Size(min=1, message="A departure airport needs to be specified!")
	private String departurePlace;
	@NotNull(message="Please indicate when the plane takes off!")
	private Date departureTime;
	@NotNull
	@Size(min=1, message="An arrival airport needs to be specified!")
	private String arrivalPlace;
	@NotNull(message="Please indicate when the plane lands!")
	private Date arrivalTime;
	@NotNull
	private List<DayOfWeek> frequency;
	
	public String getCallSign() {
		return callSign;
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
			frequencyProspect = "The flight takes place every week on: ";
			for (Iterator<DayOfWeek> iterator = frequency.iterator(); iterator.hasNext(); ) {
				frequencyProspect = frequencyProspect.concat(iterator.next().getLabel() + "s");
				if(iterator.hasNext())
					frequencyProspect = frequencyProspect.concat(", ");
			}

			return frequencyProspect.concat(".");
		}
		else return "The flight is a one-time offer.";
	}
	public List<Integer> getFrequencyToInteger() {
		List<Integer> toReturn = new ArrayList<Integer>();
		for (DayOfWeek day : frequency)
			toReturn.add(day.getConversion());
		
		return toReturn;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
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
	public List<DayOfWeek> getFrequency() {
		return frequency;
	}
	public void setFrequency(List<DayOfWeek> frequency) {
		this.frequency = frequency;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof FlightDTO)
			return (((FlightDTO)o).getProductID().equals(this.productID));
		
		return false;
	}
}
