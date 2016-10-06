package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.dto.PhotoDTO;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Photo
 *
 */
@Entity
@Table(name="PHOTO")
public class Photo implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PHOTO_ID")
	private Integer photoID;
	private String filename;
	@Lob
	private byte[] photo;
	private static final long serialVersionUID = 1L;

	public Photo() {
		super();
	}

	public Photo(PhotoDTO photoDTO) {
		this.photoID = photoDTO.getPhotoID();
		this.filename = photoDTO.getFilename();
		this.photo = photoDTO.getPhoto();
	}

	public Integer getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Integer photoID) {
		this.photoID = photoID;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
   
}
