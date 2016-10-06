package it.polimi.traveldream.ejb.dto;

import it.polimi.traveldream.ejb.client.shared.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.*;

public class HotelDTO extends BasicProductDTO {
	@NotNull
	@Size(min=1, message="The hotel must have a name!")
	private String name;
	@NotNull
	@Size(min=1, message="Please specify an address for the hotel!")
	private String address;
	@NotNull(message="It is mandatory to indicate the minimum check-in time!")
	private Date checkInTime;
	@NotNull(message="It is mandatory to indicate the maximum check-out time!")
	private Date checkOutTime;
	@NotNull
	private List<DayOfWeek> checkInDays;
	@NotNull
	private List<DayOfWeek> checkOutDays;
	
	public String getName() {
		return name;
	}
	public String getCheckInDaysToString() {
		return listToString(checkInDays);
	}
	public String getCheckOutDaysToString() {
		return listToString(checkOutDays);
	}
	public String listToString(List<DayOfWeek> list) {
		String toReturn = "";
		for (Iterator<DayOfWeek> iterator = list.iterator(); iterator.hasNext(); ) {
			toReturn = toReturn + iterator.next().getShortLabel();
			if(iterator.hasNext()) toReturn = toReturn + ", ";
			else return toReturn;
		}
		
		return "None.";
	}
	public List<Integer> getCheckInDaysToInteger() {
		return getDaysToInteger(checkInDays);
	}
	public List<Integer> getCheckOutDaysToInteger() {
		return getDaysToInteger(checkOutDays);
	}
	public List<Integer> getDaysToInteger(List<DayOfWeek> list) {
		List<Integer> toReturn = new ArrayList<Integer>();
		for (DayOfWeek day : list)
			toReturn.add(day.getConversion());
		
		return toReturn;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}
	public Date getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public List<DayOfWeek> getCheckInDays() {
		return checkInDays;
	}
	public void setCheckInDays(List<DayOfWeek> checkInDays) {
		this.checkInDays = checkInDays;
	}
	public List<DayOfWeek> getCheckOutDays() {
		return checkOutDays;
	}
	public void setCheckOutDays(List<DayOfWeek> checkOutDays) {
		this.checkOutDays = checkOutDays;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof HotelDTO)
			return (((HotelDTO)o).getProductID().equals(this.productID));
		
		return false;
	}
}
