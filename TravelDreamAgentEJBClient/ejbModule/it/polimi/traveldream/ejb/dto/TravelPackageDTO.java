package it.polimi.traveldream.ejb.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.*;

public class TravelPackageDTO {
	private Integer packageID;
	@NotNull
	@Size(min=1, message="The travel package must have a name!")
	protected String name;
	protected String description;
	@NotNull(message="Please enter a price for this package!")
	@DecimalMin(value="0", message="Negative values are not allowed!")
	@DecimalMax(value="99999.99", message="The maximum value allowed for prices is 99999.99.")
	protected BigDecimal price;
	@NotNull
	@Size(min=1, message="The component list must not be empty!")
	protected List<BasicProductDTO> components;
	@NotNull
	protected List<PhotoDTO> photos;
	@NotNull
	protected Boolean available;
	
	public int getExcursionCount() {
		int excCount = 0;
		for (BasicProductDTO bp : components)
			if(bp instanceof ExcursionDTO)
				excCount++;
		
		return excCount;
	}
	
	public int getFlightCount() {
		int flightCount = 0;
		for (BasicProductDTO bp : components)
			if(bp instanceof FlightDTO)
				flightCount++;
		
		return flightCount;
	}
	
	public int getHotelCount() {
		int hotelCount = 0;
		for (BasicProductDTO bp : components)
			if(bp instanceof HotelDTO)
				hotelCount++;
		
		return hotelCount;
	}
	
	public String getShortDescription() {
		return (description.length() <= 100 ? description : description.substring(0, 99) + "...");
	}
	
	public Integer getPackageID() {
		return packageID;
	}
	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public List<BasicProductDTO> getComponents() {
		return components;
	}
	public void setComponents(List<BasicProductDTO> components) {
		this.components = components;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<PhotoDTO> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoDTO> photos) {
		this.photos = photos;
	}
}
