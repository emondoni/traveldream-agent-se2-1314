package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.SessionManager;
import it.polimi.traveldream.ejb.client.interfaces.UserManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.UserDTO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="userMgmtBean")
@ViewScoped
public class UserManagementWebBean {
	@EJB
	private UserManager userMgr;
	@EJB
	private SessionManager sessionMgr;
	private UserDTO user;
	private List<UserDTO> filteredUsers;
	private List<UserDTO> userList;
	private UserDTO[] selectedUsers;
	private UserDTO selectedUser;
	
	public UserManagementWebBean() {
		this.user = new UserDTO();
	}
	
	@PostConstruct
	public void initializeFields() {
		reloadUserList();
	}

	public String addUser() {
		if (userMgr.usernameTaken(user.getUsername())) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ouch!", "The username you entered is already in use! Please choose another one!"));
			return null;
		}
		
		userMgr.addUser(user);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The user " + user.getUsername() + " has been correctly added!"));
		user = new UserDTO();
		reloadUserList();
		
		return null;
	}
	
	public String editUser() {
		if (selectedUser.getPassword().equals("")) 
			userMgr.editUserNoPassword(selectedUser);
		else 
			userMgr.editUser(selectedUser);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The user " + selectedUser.getUsername() + " has been correctly edited!"));
		
		reloadUserList();
		return null;
	}
	
	public void deleteSelectedUsers() {
		if (selectedUsers != null && selectedUsers.length > 0) {
			int deleted = 0;
			for(UserDTO u : selectedUsers) {
				if (isItMe(u))
					FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_WARN, "Caution!", "You cannot delete your own account!"));
				else {
					userMgr.deleteUser(u.getUsername());
					deleted++;
				}
			}
			
			if (deleted > 0) {
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deleted + (deleted > 1 ? " users have" : " user has") + " been correctly deleted!"));
				reloadUserList();
			}
		}
		else FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No user has been selected for deletion."));
	}
	
	public boolean isItMe(UserDTO userDTO) {
		return userDTO.getUsername().equals(sessionMgr.getCurrentUsername());
	}
	
	public boolean isItMe() {
		return isItMe(selectedUser);
	}
	
	public boolean isItCustomer() {
		return selectedUser.getCategory().equals(Category.CUSTOMER);
	}
	
	public List<UserDTO> retrieveUserList() {
		return userMgr.retrieveAllUsersDTO();
	}
	
	public void reloadUserList() {
		setUserList(retrieveUserList());
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<UserDTO> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<UserDTO> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public UserDTO[] getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(UserDTO[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public UserDTO getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserDTO selectedUser) {
		this.selectedUser = selectedUser;
	}
	
}
