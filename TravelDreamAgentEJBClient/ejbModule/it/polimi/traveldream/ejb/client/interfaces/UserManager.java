package it.polimi.traveldream.ejb.client.interfaces;

import it.polimi.traveldream.ejb.dto.UserDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserManager {
	public void addUser(UserDTO userDTO);
	public void editUser(UserDTO userDTO);
	public void editUserNoPassword(UserDTO userDTO);
	public void deleteUser(String username);
	public boolean usernameTaken(String username);
	public UserDTO retrieveUserDTOByUsername(String username);
	public List<UserDTO> retrieveAllUsersDTO();
}
