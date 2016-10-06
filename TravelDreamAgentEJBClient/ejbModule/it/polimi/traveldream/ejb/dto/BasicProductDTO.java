package it.polimi.traveldream.ejb.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BasicProductDTO {
	protected Integer productID;
	protected String description;
	@NotNull(message="A price for this product must be provided!")
	@DecimalMin(value="0", message="Negative values are not allowed!")
	@DecimalMax(value="9999.99", message="The maximum value allowed for product prices is 9999.99.")
	protected BigDecimal price;
	@NotNull
	protected List<PhotoDTO> photos;
	@NotNull
	protected Boolean available;
	
	public String getShortDescription() {
		return (description.length() <= 100 ? description : description.substring(0, 99) + "...");
	}
	
	public Integer getProductID() {
		return productID;
	}
	public void setProductID(Integer productID) {
		this.productID = productID;
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
