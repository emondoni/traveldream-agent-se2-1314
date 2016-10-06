package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.beans.ConversionHelper;
import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Purchase
 *
 */
@Entity
@Table(name="PURCHASE")
@NamedQueries({@NamedQuery(name=Purchase.byUsername, query="SELECT p FROM Purchase p WHERE p.buyer.username = :username")})
public class Purchase implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PURCHASE_ID")
	private Integer purchaseID;
	@ManyToOne(cascade=CascadeType.ALL)
	private User buyer;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PACKAGE_ID")
	private TravelPackage boughtPackage;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="FLIGHT_DATE_ASSIGNMENT", joinColumns={@JoinColumn(name="PURCHASE_ID")})
	private List<FlightDateAssignment> flightDateAssignments;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="EXCURSION_DATE_ASSIGNMENT", joinColumns={@JoinColumn(name="PURCHASE_ID")})
	private List<ExcursionDateAssignment> excursionDateAssignments;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="HOTEL_DATE_ASSIGNMENT", joinColumns={@JoinColumn(name="PURCHASE_ID")})
	private List<HotelDateAssignment> hotelDateAssignments;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String byUsername = "Purchase.byUsername";

	public Purchase() {
		super();
	}   
	
	public Purchase(PurchaseDTO purchaseDTO) {
		this.purchaseID = purchaseDTO.getPurchaseID();
		this.buyer = new User(purchaseDTO.getBuyer());
		this.boughtPackage = (purchaseDTO.getBoughtPackage() instanceof CustomPackageDTO) ? new CustomPackage((CustomPackageDTO)purchaseDTO.getBoughtPackage()) : new TravelPackage(purchaseDTO.getBoughtPackage());
		this.flightDateAssignments = ConversionHelper.flightAssignmentDTOListToEntity(purchaseDTO.getFlightDateAssignments());
		this.excursionDateAssignments = ConversionHelper.excursionAssignmentDTOListToEntity(purchaseDTO.getExcursionDateAssignments());
		this.hotelDateAssignments = ConversionHelper.hotelAssignmentDTOListToEntity(purchaseDTO.getHotelDateAssignments());
	}
	
	public Integer getPurchaseID() {
		return this.purchaseID;
	}

	public void setPurchaseID(Integer purchaseID) {
		this.purchaseID = purchaseID;
	}
	
	public User getBuyer() {
		return buyer;
	}
	
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	public TravelPackage getBoughtPackage() {
		return boughtPackage;
	}
	
	public void setBoughtPackage(TravelPackage boughtPackage) {
		this.boughtPackage = boughtPackage;
	}

	public List<FlightDateAssignment> getFlightDateAssignments() {
		return flightDateAssignments;
	}

	public void setFlightDateAssignments(
			List<FlightDateAssignment> flightDateAssignments) {
		this.flightDateAssignments = flightDateAssignments;
	}

	public List<ExcursionDateAssignment> getExcursionDateAssignments() {
		return excursionDateAssignments;
	}

	public void setExcursionDateAssignments(
			List<ExcursionDateAssignment> excursionDateAssignments) {
		this.excursionDateAssignments = excursionDateAssignments;
	}

	public List<HotelDateAssignment> getHotelDateAssignments() {
		return hotelDateAssignments;
	}

	public void setHotelDateAssignments(
			List<HotelDateAssignment> hotelDateAssignments) {
		this.hotelDateAssignments = hotelDateAssignments;
	}   
   
}
