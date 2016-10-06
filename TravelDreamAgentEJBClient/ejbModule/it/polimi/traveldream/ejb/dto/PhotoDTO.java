package it.polimi.traveldream.ejb.dto;

import javax.validation.constraints.*;

public class PhotoDTO {
	private Integer photoID;
	@NotNull
	private String filename;
	private byte[] photo;
	
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
