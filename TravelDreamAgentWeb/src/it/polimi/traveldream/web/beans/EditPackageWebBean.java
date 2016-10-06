package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.client.interfaces.TravelPackageManager;
import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

@ManagedBean(name="editPackBean")
@ViewScoped
public class EditPackageWebBean {
	@EJB
	private TravelPackageManager packMgr;
	@EJB
	private BasicProductManager bpMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private TravelPackageDTO pack;
	private DualListModel<ExcursionDTO> excEditModel;
	private DualListModel<FlightDTO> flightEditModel;
	private DualListModel<HotelDTO> hotelEditModel;
	
	public EditPackageWebBean() {
		
	}
	
	public boolean isNewComponentListEmpty() {
		return (excEditModel.getTarget().isEmpty() && flightEditModel.getTarget().isEmpty() && hotelEditModel.getTarget().isEmpty());
	}
	
	public UIComponent prepareEditPickLists() {
		List<ExcursionDTO> excSource = bpMgr.retrieveAllAvailableExcursionDTOList();
		List<FlightDTO> flightSource = bpMgr.retrieveAllAvailableFlightDTOList();
		List<HotelDTO> hotelSource = bpMgr.retrieveAllAvailableHotelDTOList();
		ArrayList<ExcursionDTO> excTarget = new ArrayList<ExcursionDTO>();
		ArrayList<FlightDTO> flightTarget = new ArrayList<FlightDTO>();
		ArrayList<HotelDTO> hotelTarget = new ArrayList<HotelDTO>();
				
		for(BasicProductDTO bp : pack.getComponents()) {
			if (bp instanceof ExcursionDTO) {
				excTarget.add((ExcursionDTO)bp);
				excSource.remove((ExcursionDTO)bp);
			}
			else if (bp instanceof FlightDTO) {
				flightTarget.add((FlightDTO)bp);
				flightSource.remove((FlightDTO)bp);
			}
			else if (bp instanceof HotelDTO) {
				hotelTarget.add((HotelDTO)bp);
				hotelSource.remove((HotelDTO)bp);
			}
		}
		
		this.excEditModel = new DualListModel<ExcursionDTO>(excSource, excTarget);
		this.flightEditModel = new DualListModel<FlightDTO>(flightSource, flightTarget);
		this.hotelEditModel = new DualListModel<HotelDTO>(hotelSource, hotelTarget);

		return null;
	}
	
	public List<BasicProductDTO> buildComponentList(List<ExcursionDTO> excList, List<FlightDTO> flightList, List<HotelDTO> hotelList) {
		ArrayList<BasicProductDTO> toReturn = new ArrayList<BasicProductDTO>();
		for (ExcursionDTO e : excList) toReturn.add(e);
		for (FlightDTO f : flightList) toReturn.add(f);
		for (HotelDTO h : hotelList) toReturn.add(h);
		
		return toReturn;
	}
	
	public String customizePackage() {
		List<BasicProductDTO> newComponents = buildComponentList(excEditModel.getTarget(), flightEditModel.getTarget(), hotelEditModel.getTarget());
		pack.setComponents(newComponents);
		Integer newPackageID = packMgr.customizePackage(pack, sessionMgr.getCurrentUser());
		
		return "packDetails?packId=" + newPackageID.toString() + "&faces-redirect=true";
	}
	
	public String editPackage() {
		List<BasicProductDTO> newComponents = buildComponentList(excEditModel.getTarget(), flightEditModel.getTarget(), hotelEditModel.getTarget());
		pack.setComponents(newComponents);
		packMgr.editPackage(pack);
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The package \"" + pack.getName() + "\" has been correctly edited!"));
		
		return null;
	}
	
	public String editPackageAndReload() {
		editPackage();
		return "packDetails?packId=" + pack.getPackageID().toString() +"&faces-redirect=true";
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

	public DualListModel<ExcursionDTO> getExcEditModel() {
		return excEditModel;
	}

	public void setExcEditModel(DualListModel<ExcursionDTO> excEditModel) {
		this.excEditModel = excEditModel;
	}

	public DualListModel<FlightDTO> getFlightEditModel() {
		return flightEditModel;
	}

	public void setFlightEditModel(DualListModel<FlightDTO> flightEditModel) {
		this.flightEditModel = flightEditModel;
	}

	public DualListModel<HotelDTO> getHotelEditModel() {
		return hotelEditModel;
	}

	public void setHotelEditModel(DualListModel<HotelDTO> hotelEditModel) {
		this.hotelEditModel = hotelEditModel;
	}
	
	
}
