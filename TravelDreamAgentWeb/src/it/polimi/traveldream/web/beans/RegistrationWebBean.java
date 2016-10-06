package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.UserManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.UserDTO;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="regBean")
@ViewScoped
public class RegistrationWebBean {
	@EJB
	private UserManager userMgr;
	private UserDTO user;
	
	public RegistrationWebBean() {
		this.user = new UserDTO();
	}
	
	public String register() {
		if (userMgr.usernameTaken(user.getUsername())) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ouch!", "The username you entered is already in use! Please choose another one!"));
			return null;
		}
		
		user.setCategory(Category.CUSTOMER);
		userMgr.addUser(user);
		
		return "/customer/mainPage?faces-redirect=true";
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
