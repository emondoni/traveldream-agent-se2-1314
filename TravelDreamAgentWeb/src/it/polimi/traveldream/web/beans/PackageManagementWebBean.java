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

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

@ManagedBean(name="packMgmtBean")
@ViewScoped
public class PackageManagementWebBean {
	@EJB
	private TravelPackageManager packMgr;
	@EJB
	private BasicProductManager bpMgr;
	//Add package section
	private TravelPackageDTO pack;
	private DualListModel<ExcursionDTO> excModel;
	private DualListModel<FlightDTO> flightModel;
	private DualListModel<HotelDTO> hotelModel;
	//Edit/delete package section
	private List<TravelPackageDTO> filteredPacks;
	private List<TravelPackageDTO> packageList;
	private TravelPackageDTO[] selectedPackages;
	
	public PackageManagementWebBean() {
		resetFields();
	}
	
	@PostConstruct
	public void populateLists() {
		populatePickLists();
		populateTableList();
	}
	
	public void populatePickLists() {
		this.excModel.setSource(bpMgr.retrieveAllAvailableExcursionDTOList());
		this.flightModel.setSource(bpMgr.retrieveAllAvailableFlightDTOList());
		this.hotelModel.setSource(bpMgr.retrieveAllAvailableHotelDTOList());
	}
	
	public void populateTableList() {
		this.packageList = packMgr.retrieveAllAvailablePredefinedPackageDTO();
	}
	
	public void resetFields() {
		this.pack = new TravelPackageDTO();
		this.excModel = new DualListModel<ExcursionDTO>(null, new ArrayList<ExcursionDTO>());
		this.flightModel = new DualListModel<FlightDTO>(null, new ArrayList<FlightDTO>());
		this.hotelModel = new DualListModel<HotelDTO>(null, new ArrayList<HotelDTO>());
	}
	
	public boolean isComponentListEmpty() {
		return (excModel.getTarget().isEmpty() && flightModel.getTarget().isEmpty() && hotelModel.getTarget().isEmpty());
	}
	
	public String addPackage() {
		List<BasicProductDTO> components = buildComponentList(excModel.getTarget(), flightModel.getTarget(), hotelModel.getTarget());
		pack.setComponents(components);
		pack.setAvailable(true);
		packMgr.addPackage(pack);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The package \"" + pack.getName() + "\" was correctly created!"));
		resetFields();
		populateLists();
		
		return null;
	}
	
	public String deleteSelectedPackages() {
		if (selectedPackages.length == 0)
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", "No packages were selected for deletion."));
		else {
			for (TravelPackageDTO tp : selectedPackages)
				packMgr.deletePackage(tp);
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", selectedPackages.length + (selectedPackages.length > 1 ? " packages were" : " package was") + " correctly deleted."));
			populateTableList();
		}
			
		return null;
	}
	
	public List<BasicProductDTO> buildComponentList(List<ExcursionDTO> excList, List<FlightDTO> flightList, List<HotelDTO> hotelList) {
		ArrayList<BasicProductDTO> toReturn = new ArrayList<BasicProductDTO>();
		for (ExcursionDTO e : excList) toReturn.add(e);
		for (FlightDTO f : flightList) toReturn.add(f);
		for (HotelDTO h : hotelList) toReturn.add(h);
		
		return toReturn;
	}

	public TravelPackageDTO getPack() {
		return pack;
	}

	public void setPack(TravelPackageDTO pack) {
		this.pack = pack;
	}

	public DualListModel<ExcursionDTO> getExcModel() {
		return excModel;
	}

	public void setExcModel(DualListModel<ExcursionDTO> excModel) {
		this.excModel = excModel;
	}

	public DualListModel<FlightDTO> getFlightModel() {
		return flightModel;
	}

	public void setFlightModel(DualListModel<FlightDTO> flightModel) {
		this.flightModel = flightModel;
	}

	public DualListModel<HotelDTO> getHotelModel() {
		return hotelModel;
	}

	public void setHotelModel(DualListModel<HotelDTO> hotelModel) {
		this.hotelModel = hotelModel;
	}

	public List<TravelPackageDTO> getFilteredPacks() {
		return filteredPacks;
	}

	public void setFilteredPacks(List<TravelPackageDTO> filteredPacks) {
		this.filteredPacks = filteredPacks;
	}

	public List<TravelPackageDTO> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<TravelPackageDTO> packageList) {
		this.packageList = packageList;
	}

	public TravelPackageDTO[] getSelectedPackages() {
		return selectedPackages;
	}

	public void setSelectedPackages(TravelPackageDTO[] selectedPackages) {
		this.selectedPackages = selectedPackages;
	}
}
