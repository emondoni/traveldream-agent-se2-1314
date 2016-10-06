package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.UserManager;
import it.polimi.traveldream.ejb.client.interfaces.SessionManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.UserDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="sessionMgr")
@SessionScoped
public class SessionManagerWebBean {
	@EJB
	private SessionManager sessionMgr;
	@EJB
	private UserManager userMgr;
	private boolean loggedStatus;
	private UserDTO currentUser;
	
	public SessionManagerWebBean() {
		this.loggedStatus = false;
	}
	
	public void updateCurrentUser() {
		if(currentUser == null && !sessionMgr.getCurrentUsername().equals("ANONYMOUS"));
			this.currentUser = userMgr.retrieveUserDTOByUsername(sessionMgr.getCurrentUsername());
	}
	
	public String getCurrentUserHomePage() {
		if (isSomeoneLogged()) {
			Category currentUserCategory = getCurrentUser().getCategory();
			if (currentUserCategory.equals(Category.ADMINISTRATOR))
				return "/administrator/mainPage.xhtml";
			else if (currentUserCategory.equals(Category.EMPLOYEE))
				return "/employee/mainPage.xhtml";
			else if (currentUserCategory.equals(Category.CUSTOMER))
				return "/customer/mainPage.xhtml";
		}
		
		return "/home.xhtml";
	}
	
	public boolean isSomeoneLogged() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		boolean nowLogged = (context != null && context.getUserPrincipal() != null && context.getUserPrincipal().getName() != "");
		
		//Updates the currentUser field if and only if there has been a login or a logout.
		if(nowLogged && !loggedStatus) updateCurrentUser();
		else if (!nowLogged && loggedStatus) currentUser = null;
		
		return nowLogged;
	}
	
	public boolean isCustomerLogged() {
		if (isSomeoneLogged()) return getCurrentUser().getCategory().equals(Category.CUSTOMER);
		else return false;
	}
	
	public String logout() {
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
		loggedStatus = false;
		return "/home?faces-redirect=true";
	}
	
	public UserDTO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}
}
