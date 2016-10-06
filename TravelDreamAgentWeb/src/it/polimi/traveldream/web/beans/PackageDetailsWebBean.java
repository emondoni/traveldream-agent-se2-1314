package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.client.interfaces.TravelPackageManager;
import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="packDetBean")
@ViewScoped
public class PackageDetailsWebBean {
	@EJB
	private TravelPackageManager packMgr;
	@EJB
	private BasicProductManager bpMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private TravelPackageDTO pack;
	private List<ExcursionDTO> excursions;
	private List<FlightDTO> flights;
	private List<HotelDTO> hotels;
	private ExcursionDTO selectedExcursion;
	private FlightDTO selectedFlight;
	private HotelDTO selectedHotel;
	
	public PackageDetailsWebBean() {
		this.excursions = new ArrayList<ExcursionDTO>();
		this.flights = new ArrayList<FlightDTO>();
		this.hotels = new ArrayList<HotelDTO>();
	}
	
	@PostConstruct
	public void initializeFields() {
		Integer packageID = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("packId"));
		this.pack = packMgr.retrievePackageDTOById(packageID);

		for(BasicProductDTO bp : pack.getComponents()) 
			if (bp instanceof ExcursionDTO) excursions.add((ExcursionDTO)bp);
			else if (bp instanceof FlightDTO) flights.add((FlightDTO)bp);
			else if (bp instanceof HotelDTO) hotels.add((HotelDTO)bp);
	}
	
	public boolean isCustomPackage() {
		return (pack instanceof CustomPackageDTO);
	}
	
	public String buyPackage() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("packToBuy", pack);
		return "purchase?faces-redirect=true";
	}

	public SessionManagerWebBean getSessionMgr() {
		return sessionMgr;
	}

	public void setSessionMgr(SessionManagerWebBean sessionMgr) {
		this.sessionMgr = sessionMgr;
	}

	public TravelPackageDTO getPack() {
		return pack;
	}

	public void setPack(TravelPackageDTO pack) {
		this.pack = pack;
	}

	public List<ExcursionDTO> getExcursions() {
		return excursions;
	}

	public void setExcursions(List<ExcursionDTO> excursions) {
		this.excursions = excursions;
	}

	public List<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightDTO> flights) {
		this.flights = flights;
	}

	public List<HotelDTO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelDTO> hotels) {
		this.hotels = hotels;
	}

	public ExcursionDTO getSelectedExcursion() {
		return selectedExcursion;
	}

	public void setSelectedExcursion(ExcursionDTO selectedExcursion) {
		this.selectedExcursion = selectedExcursion;
	}

	public FlightDTO getSelectedFlight() {
		return selectedFlight;
	}

	public void setSelectedFlight(FlightDTO selectedFlight) {
		this.selectedFlight = selectedFlight;
	}

	public HotelDTO getSelectedHotel() {
		return selectedHotel;
	}

	public void setSelectedHotel(HotelDTO selectedHotel) {
		this.selectedHotel = selectedHotel;
	}
	
}
