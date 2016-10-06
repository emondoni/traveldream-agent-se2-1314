package it.polimi.traveldream.web.beans;

import java.util.List;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.client.shared.BasicProductType;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="bpMgmtBean")
@ViewScoped
public class BasicProductManagementWebBean {
	@EJB
	private BasicProductManager bpMgr;
	//Fields related to the "Add BP" function
	private BasicProductType type;
	private FlightDTO flight;
	private ExcursionDTO excursion;
	private HotelDTO hotel;
	//Fields related to the "Edit/Delete BP" functions
	private BasicProductType listType;
	private List<ExcursionDTO> excursionList;
	private List<FlightDTO> flightList;
	private List<HotelDTO> hotelList;
	private ExcursionDTO[] selectedExcursions;
	private FlightDTO[] selectedFlights;
	private HotelDTO[] selectedHotels;
	private ExcursionDTO selectedExcursion;
	private FlightDTO selectedFlight;
	private HotelDTO selectedHotel;
	private List<ExcursionDTO> filteredExcursions;
	private List<FlightDTO> filteredFlights;
	private List<HotelDTO> filteredHotels;
	
	public BasicProductManagementWebBean() {
		
	}

	public boolean isThisExcursion() {
		return (type != null && type.equals(BasicProductType.EXCURSION));
	}
	
	public boolean isThisFlight() {
		return (type != null && type.equals(BasicProductType.FLIGHT));
	}
	
	public boolean isThisHotel() {
		return (type != null && type.equals(BasicProductType.HOTEL));
	}
	
	public boolean isListExcursion() {
		return (listType != null && listType.equals(BasicProductType.EXCURSION));
	}
	
	public boolean isListFlight() {
		return (listType != null && listType.equals(BasicProductType.FLIGHT));
	}
	
	public boolean isListHotel() {
		return (listType != null && listType.equals(BasicProductType.HOTEL));
	}
	
	@PostConstruct
	public void renewFields() {
		renewExcursions();
		renewFlights();
		renewHotels();
	}
	
	public void renewExcursions() {
		this.excursion = new ExcursionDTO();
		this.excursionList = bpMgr.retrieveAllAvailableExcursionDTOList();
	}
	
	public void renewFlights() {
		this.flight = new FlightDTO();
		this.flightList = bpMgr.retrieveAllAvailableFlightDTOList();
	}
	
	public void renewHotels() {
		this.hotel = new HotelDTO();
		this.hotelList = bpMgr.retrieveAllAvailableHotelDTOList();
	}
	
	public String addExcursion() {
		excursion.setAvailable(true);
		bpMgr.addExcursion(excursion);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The excursion \"" + excursion.getName() + "\" was properly added to the database!"));
		renewExcursions();
		return null;
	}
	
	public String addFlight() {
		flight.setAvailable(true);
		bpMgr.addFlight(flight);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Flight " + flight.getCallSign() + " was properly added to the database!"));
		renewFlights();
		return null;
	}
	
	public String addHotel() {
		hotel.setAvailable(true);
		bpMgr.addHotel(hotel);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The hotel \"" + hotel.getName() + "\" was properly added to the database!"));
		renewHotels();
		return null;
	}
	
	public String editExcursion() {
		bpMgr.editExcursion(selectedExcursion);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The excursion \"" + selectedExcursion.getName() + "\" was correctly edited!"));
		return null;
	}
	
	public String editFlight() {
		bpMgr.editFlight(selectedFlight);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Flight " + selectedFlight.getCallSign() + " was correctly edited!"));
		return null;
	}
	
	public String editHotel() {
		bpMgr.editHotel(selectedHotel);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The hotel \"" + selectedHotel.getName() + "\" was correctly edited!"));
		return null;
	}
	
	public void deleteSelectedExcursions() {
		if (selectedExcursions.length == 0)
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", "No excursion was selected for deletion."));
		else {
			for (ExcursionDTO e : selectedExcursions)
				bpMgr.deleteBasicProduct(e);
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", selectedExcursions.length + (selectedExcursions.length > 1 ? " excursion was" : " excursions were") + " correctly deleted."));
			renewExcursions();
		}
	}
	
	public void deleteSelectedFlights() {
		if (selectedFlights.length == 0)
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", "No flight was selected for deletion."));
		else {
			for (FlightDTO f : selectedFlights)
				bpMgr.deleteBasicProduct(f);
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", selectedFlights.length + (selectedFlights.length > 1 ? " flight was" : " flights were") + " correctly deleted."));
			renewFlights();
		}
	}
	
	public void deleteSelectedHotels() {
		if (selectedHotels.length == 0)
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", "No hotel was selected for deletion."));
		else {
			for (HotelDTO h : selectedHotels)
				bpMgr.deleteBasicProduct(h);
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", selectedHotels.length + (selectedHotels.length > 1 ? " hotel was" : " hotels were") + " correctly deleted."));
			renewHotels();
		}
	}
	
	public List<ExcursionDTO> getExcursionList() {
		return excursionList;
	}

	public void setExcursionList(List<ExcursionDTO> excursionList) {
		this.excursionList = excursionList;
	}

	public List<FlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<FlightDTO> flightList) {
		this.flightList = flightList;
	}

	public List<HotelDTO> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelDTO> hotelList) {
		this.hotelList = hotelList;
	}

	public ExcursionDTO[] getSelectedExcursions() {
		return selectedExcursions;
	}

	public void setSelectedExcursions(ExcursionDTO[] selectedExcursions) {
		this.selectedExcursions = selectedExcursions;
	}

	public FlightDTO[] getSelectedFlights() {
		return selectedFlights;
	}

	public void setSelectedFlights(FlightDTO[] selectedFlights) {
		this.selectedFlights = selectedFlights;
	}

	public HotelDTO[] getSelectedHotels() {
		return selectedHotels;
	}

	public void setSelectedHotels(HotelDTO[] selectedHotels) {
		this.selectedHotels = selectedHotels;
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

	public BasicProductType getListType() {
		return listType;
	}

	public void setListType(BasicProductType listType) {
		this.listType = listType;
	}

	public BasicProductType getType() {
		return type;
	}

	public void setType(BasicProductType type) {
		this.type = type;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	public ExcursionDTO getExcursion() {
		return excursion;
	}

	public void setExcursion(ExcursionDTO excursion) {
		this.excursion = excursion;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public List<ExcursionDTO> getFilteredExcursions() {
		return filteredExcursions;
	}

	public void setFilteredExcursions(List<ExcursionDTO> filteredExcursions) {
		this.filteredExcursions = filteredExcursions;
	}

	public List<FlightDTO> getFilteredFlights() {
		return filteredFlights;
	}

	public void setFilteredFlights(List<FlightDTO> filteredFlights) {
		this.filteredFlights = filteredFlights;
	}

	public List<HotelDTO> getFilteredHotels() {
		return filteredHotels;
	}

	public void setFilteredHotels(List<HotelDTO> filteredHotels) {
		this.filteredHotels = filteredHotels;
	}
	
	
}
