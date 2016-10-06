package it.polimi.traveldream.ejb.dto;

import java.util.List;
import javax.validation.constraints.*;

public class PurchaseDTO {
	private Integer purchaseID;
	@NotNull(message="There must be a user who completed this purchase!")
	private UserDTO buyer;
	@NotNull(message="A purchase must consist of a travel package!")
	private TravelPackageDTO boughtPackage;
	@NotNull
	private List<FlightDateAssignmentDTO> flightDateAssignments;
	@NotNull
	private List<ExcursionDateAssignmentDTO> excursionDateAssignments;
	@NotNull
	private List<HotelDateAssignmentDTO> hotelDateAssignments;
	
	public Integer getPurchaseID() {
		return purchaseID;
	}
	public void setPurchaseID(Integer purchaseID) {
		this.purchaseID = purchaseID;
	}
	public UserDTO getBuyer() {
		return buyer;
	}
	public void setBuyer(UserDTO buyer) {
		this.buyer = buyer;
	}
	public TravelPackageDTO getBoughtPackage() {
		return boughtPackage;
	}
	public void setBoughtPackage(TravelPackageDTO boughtPackage) {
		this.boughtPackage = boughtPackage;
	}
	public List<FlightDateAssignmentDTO> getFlightDateAssignments() {
		return flightDateAssignments;
	}
	public void setFlightDateAssignments(
			List<FlightDateAssignmentDTO> flightDateAssignments) {
		this.flightDateAssignments = flightDateAssignments;
	}
	public List<ExcursionDateAssignmentDTO> getExcursionDateAssignments() {
		return excursionDateAssignments;
	}
	public void setExcursionDateAssignments(
			List<ExcursionDateAssignmentDTO> excursionDateAssignments) {
		this.excursionDateAssignments = excursionDateAssignments;
	}
	public List<HotelDateAssignmentDTO> getHotelDateAssignments() {
		return hotelDateAssignments;
	}
	public void setHotelDateAssignments(
			List<HotelDateAssignmentDTO> hotelDateAssignments) {
		this.hotelDateAssignments = hotelDateAssignments;
	}
}
