package it.polimi.traveldream.ejb.entities;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: BasicProduct
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
@Table(name="BASIC_PRODUCT")
@NamedQueries({@NamedQuery(name=BasicProduct.byId, query="SELECT bp FROM BasicProduct bp WHERE bp.productID = :productID")})
public abstract class BasicProduct implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PRODUCT_ID")
	protected Integer productID;
	@Lob
	protected String description;
	@Column(precision=6, scale=2)
	protected BigDecimal price;
	@ManyToMany
	@JoinTable(name="BASIC_PRODUCT_PHOTO", joinColumns={@JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID")},
			   inverseJoinColumns={@JoinColumn(name="PHOTO_ID", referencedColumnName="PHOTO_ID")})
	protected List<Photo> photos;
	protected Boolean available;
	private static final long serialVersionUID = 1L;
	
	//Named queries
	public static final String byId = "BasicProduct.byId";

	public BasicProduct() {
		super();
	}   
	
	public Integer getProductID() {
		return this.productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
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

	public Boolean getAvailable() {
		return available;
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
