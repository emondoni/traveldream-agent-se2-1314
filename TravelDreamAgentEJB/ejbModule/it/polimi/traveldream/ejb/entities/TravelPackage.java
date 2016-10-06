package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.beans.ConversionHelper;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;
import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TravelPackage
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="CUSTOM")
@DiscriminatorValue(value="NO")
@Table(name="PACKAGE")
@NamedQueries({@NamedQuery(name=TravelPackage.byId, query="SELECT tp FROM TravelPackage tp WHERE tp.packageID = :packageID"),
			   @NamedQuery(name=TravelPackage.allAvailable, query="SELECT tp FROM TravelPackage tp WHERE tp.available = true"),
			   @NamedQuery(name=TravelPackage.containingProduct, query="SELECT tp FROM TravelPackage tp WHERE :bp MEMBER OF tp.components AND tp.available = true")})
public class TravelPackage implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PACKAGE_ID")
	protected Integer packageID;
	protected String name;
	@Lob
	protected String description;
	@Column(precision=7, scale=2)
	protected BigDecimal price;
	@ManyToMany(fetch=FetchType.EAGER)
	protected List<BasicProduct> components;
	@ManyToMany
	@JoinTable(name="PACKAGE_PHOTO", joinColumns={@JoinColumn(name="PACKAGE_ID", referencedColumnName="PACKAGE_ID")},
			   inverseJoinColumns={@JoinColumn(name="PHOTO_ID", referencedColumnName="PHOTO_ID")})
	protected List<Photo> photos;
	protected Boolean available;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String byId = "TravelPackage.byId";
	public static final String allAvailable = "TravelPackage.allAvailable";
	public static final String containingProduct = "TravelPacakge.containingProduct";

	public TravelPackage() {
		super();
	}   
	public TravelPackage(TravelPackageDTO travelPackageDTO) {
		this.packageID = travelPackageDTO.getPackageID();
		this.name = travelPackageDTO.getName();
		this.description = travelPackageDTO.getDescription();
		this.price = travelPackageDTO.getPrice();
		this.available = travelPackageDTO.getAvailable();
		this.components = ConversionHelper.componentsListToEntity(travelPackageDTO.getComponents());
		this.photos = ConversionHelper.photosToEntity(travelPackageDTO.getPhotos());
	}
	public Integer getPackageID() {
		return this.packageID;
	}

	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}   

	public List<BasicProduct> getComponents() {
		return components;
	}
	public void setComponents(List<BasicProduct> components) {
		this.components = components;
	}
	public Boolean getAvailable() {
		return this.available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
   
}
