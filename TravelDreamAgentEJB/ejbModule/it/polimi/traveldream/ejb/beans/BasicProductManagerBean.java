package it.polimi.traveldream.ejb.beans;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.entities.BasicProduct;
import it.polimi.traveldream.ejb.entities.Excursion;
import it.polimi.traveldream.ejb.entities.Flight;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.TravelPackage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class BasicProductManagerBean
 */
@Stateless
@Local(BasicProductManager.class)
public class BasicProductManagerBean implements BasicProductManager {
	@PersistenceContext
	private EntityManager em;
	
    public BasicProductManagerBean() {

    }
    
    @RolesAllowed({Category._EMPLOYEE})
    public void addExcursion(ExcursionDTO excursionDTO) {
        Excursion toAdd = new Excursion(excursionDTO);
        em.persist(toAdd);
    }
    
    @RolesAllowed({Category._EMPLOYEE})
    public void addFlight(FlightDTO flightDTO) {
    	Flight toAdd = new Flight(flightDTO);
        em.persist(toAdd);
    }

    @RolesAllowed({Category._EMPLOYEE})
    public void addHotel(HotelDTO hotelDTO) {
    	Hotel toAdd = new Hotel(hotelDTO);
        em.persist(toAdd);
    }
    
    @RolesAllowed({Category._EMPLOYEE})
    public void editHotel(HotelDTO hotelDTO) {
        Hotel toEdit = new Hotel(hotelDTO);
        em.merge(toEdit);
    }

    @RolesAllowed({Category._EMPLOYEE})
    public void editExcursion(ExcursionDTO excursionDTO) {
    	Excursion toEdit = new Excursion(excursionDTO);
        em.merge(toEdit);
    }
    
    @RolesAllowed({Category._EMPLOYEE})
    public void editFlight(FlightDTO flightDTO) {
    	Flight toEdit = new Flight(flightDTO);
        em.merge(toEdit);
    }
    
    @RolesAllowed({Category._EMPLOYEE})
    public void deleteBasicProduct(BasicProductDTO basicProductDTO) {
        BasicProduct toDelete = retrieveBasicProductById(basicProductDTO.getProductID());
        List<TravelPackage> toDisable = em.createNamedQuery(TravelPackage.containingProduct, TravelPackage.class).setParameter("bp", toDelete).getResultList();
        for (TravelPackage tp : toDisable) {
        	tp.setAvailable(false);
        	em.persist(tp);
        }
        
        toDelete.setAvailable(false);
        em.merge(toDelete);
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public BasicProductDTO retrieveBasicProductDTOById(Integer productID) {
    	return ConversionHelper.bpToDTO(retrieveBasicProductById(productID));
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public BasicProduct retrieveBasicProductById(Integer productID) {
    	return em.createNamedQuery(BasicProduct.byId, BasicProduct.class).setParameter("productID", productID).getSingleResult();
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<Excursion> retrieveAllAvailableExcursions() {
    	return em.createNamedQuery(Excursion.allAvailable, Excursion.class).getResultList();
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<Flight> retrieveAllAvailableFlights() {
    	return em.createNamedQuery(Flight.allAvailable, Flight.class).getResultList();
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<Hotel> retrieveAllAvailableHotels() {
    	return em.createNamedQuery(Hotel.allAvailable, Hotel.class).getResultList();
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<ExcursionDTO> retrieveAllAvailableExcursionDTOList() {
    	return listToDTO(retrieveAllAvailableExcursions(), ExcursionDTO.class);
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<FlightDTO> retrieveAllAvailableFlightDTOList() {
    	return listToDTO(retrieveAllAvailableFlights(), FlightDTO.class);
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public List<HotelDTO> retrieveAllAvailableHotelDTOList() {
    	return listToDTO(retrieveAllAvailableHotels(), HotelDTO.class);
    }
    
    @RolesAllowed({Category._EMPLOYEE, Category._CUSTOMER})
    public <T extends BasicProductDTO, S extends BasicProduct> List<T> listToDTO(List<S> list, Class<T> DTOType) {
    	List<T> toReturn = new ArrayList<T>();
    	for(S bp : list)
    		toReturn.add(DTOType.cast(ConversionHelper.bpToDTO(bp)));
    	
    	return toReturn;
    }    
}
