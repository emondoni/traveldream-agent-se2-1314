package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.UserDTO;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name = "USER")
@NamedQueries({@NamedQuery(name=User.all, query="SELECT u FROM User u"),
			   @NamedQuery(name=User.byUsername, query="SELECT u FROM User u WHERE u.username = :username"),
			   @NamedQuery(name=User.byCategory, query="SELECT u FROM User u WHERE :category MEMBER OF u.categories"),
			   @NamedQuery(name=User.usernameTaken, query="SELECT COUNT(u) FROM User u WHERE u.username = :username")})
public class User implements Serializable {
	@Id
	private String username;
	private String password;
	private String name;
	private String surname;
	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;
	@Column(name="HOME_ADDRESS")
	private String homeAddress;
	@Column(name="EMAIL_ADDRESS")
	private String eMailAddress;
	@ElementCollection(targetClass = it.polimi.traveldream.ejb.client.shared.Category.class)
	@CollectionTable(name = "USER_CATEGORY", joinColumns = {@JoinColumn(name = "USERNAME")})
	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY")
	private List<Category> categories;
	private static final long serialVersionUID = 1L;
	
	//NamedQueries
	public static final String all = "User.all";
	public static final String byUsername = "User.byUsername";
	public static final String byCategory = "User.byCategory";
	public static final String usernameTaken = "User.usernameTaken";
	
	public User() {
		super();
	}  
	
	public User(UserDTO userDTO) {
		this.username = userDTO.getUsername();
		this.password = DigestUtils.sha256Hex(userDTO.getPassword());
		this.name = userDTO.getName();
		this.surname = userDTO.getSurname();
		this.dateOfBirth = userDTO.getDateOfBirth();
		this.homeAddress = userDTO.getHomeAddress();
		this.eMailAddress = userDTO.geteMailAddress();
		this.categories = new ArrayList<Category>();
		this.categories.add(userDTO.getCategory());
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}  
	
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getEMailAddress() {
		return this.eMailAddress;
	}

	public void setEMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
