package it.polimi.traveldream.ejb.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.polimi.traveldream.ejb.client.interfaces.TravelPackageManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;
import it.polimi.traveldream.ejb.dto.UserDTO;
import it.polimi.traveldream.ejb.entities.CustomPackage;
import it.polimi.traveldream.ejb.entities.TravelPackage;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TravelPackageManagerBean implements TravelPackageManager {
	@PersistenceContext
	private EntityManager em;
	
	public TravelPackageManagerBean() {
		
	}

	@Override
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public void addPackage(TravelPackageDTO travelPackageDTO) {
		TravelPackage toAdd = null;
		if (travelPackageDTO instanceof CustomPackageDTO)
			System.err.println("Persisting CustomPackage objects requires calling the customizePackage method.");
		else {
			toAdd = new TravelPackage(travelPackageDTO);
			toAdd.setAvailable(true);
			em.persist(toAdd);
		}
	}

	@Override
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public void editPackage(TravelPackageDTO travelPackageDTO) {
		if (travelPackageDTO instanceof CustomPackageDTO) {
			travelPackageDTO.setPrice(((CustomPackageDTO)travelPackageDTO).componentPriceSum());
			em.merge(new CustomPackage((CustomPackageDTO) travelPackageDTO));
		}
		else
			em.merge(new TravelPackage(travelPackageDTO));
	}

	@Override
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public void deletePackage(TravelPackageDTO travelPackageDTO) {
		TravelPackage toDelete = retrievePackageById(travelPackageDTO.getPackageID());
		toDelete.setAvailable(false);
		if (travelPackageDTO instanceof CustomPackageDTO) em.merge((CustomPackage) toDelete);
		else em.merge(toDelete);
	}
	
	@Override
	@RolesAllowed({Category._CUSTOMER})
	public Integer customizePackage(TravelPackageDTO originalModifiedDTO, UserDTO author) {
		CustomPackageDTO toAdd = ConversionHelper.customFromOriginal(originalModifiedDTO);
		toAdd.setAuthor(author);
		toAdd.setBasedOn(retrievePackageDTOById(originalModifiedDTO.getPackageID()));
		toAdd.setAvailable(true);
		toAdd.setPrice(toAdd.componentPriceSum());
		em.persist(new CustomPackage(toAdd));
		Integer createdID = retrieveLastCustomizedPackageID(author.getUsername());
		
		return createdID;
	}

	@Override
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public List<TravelPackageDTO> retrieveAllAvailablePredefinedPackageDTO() {
		return ConversionHelper.packageListToDTO(retrieveAllAvailablePredefinedPackages(), TravelPackageDTO.class);
	}
	
	@Override
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public TravelPackageDTO retrievePackageDTOById(Integer productID) {
		return ConversionHelper.packToDTO(retrievePackageById(productID));
	}
	
	@Override
	@RolesAllowed({Category._CUSTOMER})
	public List<CustomPackageDTO> retrieveAllAvailableCustomPackageDTOByUser(UserDTO userDTO) {
		return ConversionHelper.packageListToDTO(retrieveAllAvailableCustomPackagesByUser(userDTO.getUsername()), CustomPackageDTO.class);
	}
	
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public List<TravelPackage> retrieveAllAvailablePredefinedPackages() {
		List<TravelPackage> toReturn = new ArrayList<TravelPackage>();
		toReturn = em.createNamedQuery(TravelPackage.allAvailable, TravelPackage.class).getResultList();
		Iterator<TravelPackage> iterator = toReturn.iterator();
		while(iterator.hasNext())
			if(iterator.next() instanceof CustomPackage)
				iterator.remove();
		
		return toReturn;
	}
	
	@RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
	public TravelPackage retrievePackageById(Integer packageID) {
		return em.createNamedQuery(TravelPackage.byId, TravelPackage.class).setParameter("packageID", packageID).getSingleResult(); 
	}
	
	@RolesAllowed({Category._CUSTOMER})
	public List<CustomPackage> retrieveAllAvailableCustomPackagesByUser(String username) {
		return em.createNamedQuery(CustomPackage.byUsername, CustomPackage.class).setParameter("author", username).getResultList();
	}
	
	@RolesAllowed({Category._CUSTOMER})
	public Integer retrieveLastCustomizedPackageID(String username) {
		return em.createNamedQuery(CustomPackage.lastCustomized, Integer.class).setParameter("author", username).getSingleResult();
	}
}
