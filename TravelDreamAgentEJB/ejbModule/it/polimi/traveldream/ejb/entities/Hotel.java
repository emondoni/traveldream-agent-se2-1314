package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.client.shared.BasicProductType;
import it.polimi.traveldream.ejb.client.shared.DayOfWeek;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.BasicProduct;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Hotel
 *
 */
@Entity
@DiscriminatorValue(value=BasicProductType._HOTEL)
@Table(name="HOTEL")
@NamedQueries({@NamedQuery(name=Hotel.allAvailable, query="SELECT h FROM Hotel h WHERE h.available = true")})
public class Hotel extends BasicProduct implements Serializable {
	private String name;
	private String address;
	@Temporal(TemporalType.TIME)
	@Column(name="CHECKIN_TIME")
	private Date checkInTime;
	@Temporal(TemporalType.TIME)
	@Column(name="CHECKOUT_TIME")
	private Date checkOutTime;
	@ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "HOTEL_CHECKIN", joinColumns = {@JoinColumn(name = "HOTEL_ID")})
	@Enumerated(value=EnumType.STRING)
	@Column(name="DAY_OF_WEEK")
	private List<DayOfWeek> checkInDays;
	@ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "HOTEL_CHECKOUT", joinColumns = {@JoinColumn(name = "HOTEL_ID")})
	@Enumerated(value=EnumType.STRING)
	@Column(name="DAY_OF_WEEK")
	private List<DayOfWeek> checkOutDays;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String allAvailable = "Hotel.allAvailable";

	public Hotel() {
		super();
	}   
	
	public Hotel(HotelDTO hotelDTO) {
		this.productID = hotelDTO.getProductID();
		this.description = hotelDTO.getDescription();
		this.price = hotelDTO.getPrice();
		this.available = hotelDTO.getAvailable();
		this.name = hotelDTO.getName();
		this.address = hotelDTO.getAddress();
		this.checkInTime = hotelDTO.getCheckInTime();
		this.checkOutTime = hotelDTO.getCheckOutTime();
		this.checkInDays = hotelDTO.getCheckInDays();
		this.checkOutDays = hotelDTO.getCheckOutDays();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}   
	
	public Date getCheckInTime() {
		return this.checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}   
	
	public Date getCheckOutTime() {
		return this.checkOutTime;
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
   
}
