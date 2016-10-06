package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.TravelPackageManager;
import it.polimi.traveldream.ejb.dto.CustomPackageDTO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="custMgmtBean")
@ViewScoped
public class CustomPackageManagementWebBean {
	@EJB
	private TravelPackageManager packMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private List<CustomPackageDTO> packageList;
	private CustomPackageDTO[] selectedPackages;	
	private List<CustomPackageDTO> filteredList;
	
	public CustomPackageManagementWebBean() {
		
	}

	@PostConstruct
	public void initializeFields() {
		refreshList();
	}
	
	public void refreshList() {
		this.packageList = packMgr.retrieveAllAvailableCustomPackageDTOByUser(sessionMgr.getCurrentUser());
	}
	
	public String deleteSelectedPackages() {
		if (selectedPackages.length == 0)
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Oops.", "No packages were selected for deletion."));
		else {
			for (CustomPackageDTO cp : selectedPackages)
				packMgr.deletePackage(cp);
			refreshList();
		}
		
		return null;
	}
	
	public SessionManagerWebBean getSessionMgr() {
		return sessionMgr;
	}

	public void setSessionMgr(SessionManagerWebBean sessionMgr) {
		this.sessionMgr = sessionMgr;
	}

	public List<CustomPackageDTO> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<CustomPackageDTO> packageList) {
		this.packageList = packageList;
	}

	public CustomPackageDTO[] getSelectedPackages() {
		return selectedPackages;
	}

	public void setSelectedPackages(CustomPackageDTO[] selectedPackages) {
		this.selectedPackages = selectedPackages;
	}

	public List<CustomPackageDTO> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<CustomPackageDTO> filteredList) {
		this.filteredList = filteredList;
	}
}
