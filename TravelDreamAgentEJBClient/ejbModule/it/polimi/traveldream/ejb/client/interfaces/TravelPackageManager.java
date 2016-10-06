package it.polimi.traveldream.ejb.client.interfaces;

import java.util.List;

import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;
import it.polimi.traveldream.ejb.dto.UserDTO;

public interface TravelPackageManager {
	public void addPackage(TravelPackageDTO travelPackageDTO);
	public void editPackage(TravelPackageDTO travelPackageDTO);
	public void deletePackage(TravelPackageDTO travelPackageDTO);
	public Integer customizePackage(TravelPackageDTO customPackageDTO, UserDTO author);
	public List<TravelPackageDTO> retrieveAllAvailablePredefinedPackageDTO();
	public TravelPackageDTO retrievePackageDTOById(Integer packageID);
	public List<CustomPackageDTO> retrieveAllAvailableCustomPackageDTOByUser(UserDTO userDTO);
}
