package it.polimi.traveldream.ejb.dto;

import it.polimi.traveldream.ejb.client.shared.Category;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {
	@Size(min=1, message="Enter a username!")
	private String username;
	private String password;
	@Size(min=1, message="Enter your name!")
	private String name;
	@Size(min=1, message="Enter your surname!")
	private String surname;
	@NotNull(message="Enter your birth date!")
	@Past(message="Enter a valid date!")
	private Date dateOfBirth;
	@Size(min=1, message="Enter your home address!")
	private String homeAddress;
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="Invalid e-mail address!")
	private String eMailAddress;
	@NotNull
	private Category category;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String geteMailAddress() {
		return eMailAddress;
	}
	
	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
