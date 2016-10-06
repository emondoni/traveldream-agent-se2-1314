package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PackageAuthor
 *
 */
@Entity
@DiscriminatorValue(value="YES")
@Table(name="CUSTOM_PACKAGE")
@NamedQueries({@NamedQuery(name=CustomPackage.byUsername, query="SELECT cp FROM CustomPackage cp WHERE cp.author.username = :author AND cp.available = true"),
			   @NamedQuery(name=CustomPackage.allByUsername, query="SELECT cp FROM CustomPackage cp WHERE cp.author.username = :author"),
			   @NamedQuery(name=CustomPackage.lastCustomized, query="SELECT MAX(cp.packageID) FROM CustomPackage cp WHERE cp.author.username = :author AND cp.available = true")})

public class CustomPackage extends TravelPackage implements Serializable {
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="BASED_ON")
	private TravelPackage basedOn;
	@ManyToOne
	@JoinColumn(name="AUTHOR")
	private User author;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String byUsername = "CustomPackage.byUsername";
	public static final String allByUsername = "CustomPackage.allByUsername";
	public static final String lastCustomized = "CustomPackage.lastCustomized";

	public CustomPackage() {
		super();
	}

	public CustomPackage(CustomPackageDTO customPackageDTO) {
		super((TravelPackageDTO) customPackageDTO);
		this.basedOn = new TravelPackage(customPackageDTO.getBasedOn());
		this.author = new User(customPackageDTO.getAuthor());
	}

	public TravelPackage getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(TravelPackage basedOn) {
		this.basedOn = basedOn;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
   
}
