package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.UserManager;
import it.polimi.traveldream.ejb.dto.UserDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="accMgmtBean")
@ViewScoped
public class AccountManagementWebBean {
	@EJB
	private UserManager userMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private UserDTO currentUser;
	
	@PostConstruct
	public void initializeFields() {
		this.currentUser = sessionMgr.getCurrentUser();
	}
	
	public String submitChanges() {
		if (currentUser.getPassword().equals("")) 
			userMgr.editUserNoPassword(currentUser);
		else 
			userMgr.editUser(currentUser);
		sessionMgr.updateCurrentUser();
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Your details have been correctly edited!"));
		return null;
	}
	
	public UserDTO getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}

	public SessionManagerWebBean getSessionMgr() {
		return sessionMgr;
	}

	public void setSessionMgr(SessionManagerWebBean sessionMgr) {
		this.sessionMgr = sessionMgr;
	}
}
