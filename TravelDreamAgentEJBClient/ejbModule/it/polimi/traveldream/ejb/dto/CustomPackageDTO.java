package it.polimi.traveldream.ejb.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class CustomPackageDTO extends TravelPackageDTO {
	@NotNull
	private TravelPackageDTO basedOn;
	@NotNull
	private UserDTO author;
	
	public BigDecimal componentPriceSum() {
		BigDecimal sum = new BigDecimal(0);
		for (BasicProductDTO bp : components)
			sum = sum.add(bp.getPrice());
		
		return sum;
	}
	
	public TravelPackageDTO getBasedOn() {
		return basedOn;
	}
	public void setBasedOn(TravelPackageDTO basedOn) {
		this.basedOn = basedOn;
	}
	public UserDTO getAuthor() {
		return author;
	}
	public void setAuthor(UserDTO author) {
		this.author = author;
	}
}
