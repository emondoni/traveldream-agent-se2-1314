package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.dto.TravelPackageDTO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="successBean")
@RequestScoped
public class SuccessWebBean {
	private TravelPackageDTO justBought;
	
	@PostConstruct
	public void initializeFields() {
		this.justBought = (TravelPackageDTO)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("justBought");
	}

	public TravelPackageDTO getJustBought() {
		return justBought;
	}

	public void setJustBought(TravelPackageDTO justBought) {
		this.justBought = justBought;
	}
}
