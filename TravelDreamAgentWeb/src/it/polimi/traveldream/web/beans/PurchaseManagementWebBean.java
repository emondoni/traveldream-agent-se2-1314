package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.client.interfaces.TravelPackageManager;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.FlightDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.HotelDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;
import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="buyMgmtBean")
@ViewScoped
public class PurchaseManagementWebBean {
	@EJB
	private TravelPackageManager packMgr;
	@EJB
	private BasicProductManager bpMgr;
	private PurchaseDTO purchase;
	
	public PurchaseManagementWebBean() {
		this.purchase = new PurchaseDTO();
	}
	
	@PostConstruct
	public void initializeFields() {
		purchase.setBoughtPackage((TravelPackageDTO)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("packToBuy"));
		
		ArrayList<ExcursionDateAssignmentDTO> eda = new ArrayList<ExcursionDateAssignmentDTO>();
		ArrayList<FlightDateAssignmentDTO> fda = new ArrayList<FlightDateAssignmentDTO>();
		ArrayList<HotelDateAssignmentDTO> hda = new ArrayList<HotelDateAssignmentDTO>();
		
		for (BasicProductDTO bp : purchase.getBoughtPackage().getComponents()) {
			if (bp instanceof ExcursionDTO) eda.add(new ExcursionDateAssignmentDTO((ExcursionDTO)bp));
			else if (bp instanceof FlightDTO) fda.add(new FlightDateAssignmentDTO((FlightDTO)bp));
			else if (bp instanceof HotelDTO) hda.add(new HotelDateAssignmentDTO((HotelDTO)bp));
		}
		
		purchase.setExcursionDateAssignments(eda);
		purchase.setFlightDateAssignments(fda);
		purchase.setHotelDateAssignments(hda);
	}
	
	public String proceedToCheckout() {
		if (coherentDateAssignments()) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("purchaseDTO", purchase);
			return "billing?faces-redirect=true";
		}
		else return null;
	}
	
	public boolean coherentDateAssignments() {
		Calendar calendar = Calendar.getInstance();
		for (ExcursionDateAssignmentDTO eda : purchase.getExcursionDateAssignments()) {
			calendar.setTime(eda.getDate());
			if (!eda.getExcursion().getFrequencyToInteger().contains(Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK))) && !eda.getExcursion().getFrequencyToInteger().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid date assignment on excursion \"" + eda.getExcursion().getName() + "\"!"));
				return false;
			}
		}
		for (FlightDateAssignmentDTO fda : purchase.getFlightDateAssignments()) {
			calendar.setTime(fda.getDate());
			if (!fda.getFlight().getFrequencyToInteger().contains(Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK))) && !fda.getFlight().getFrequencyToInteger().isEmpty()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid date assignment on flight " + fda.getFlight().getCallSign() + "!"));
				return false;
			}
		}
		for (HotelDateAssignmentDTO hda : purchase.getHotelDateAssignments()) {
			if (hda.getDepartureDate().compareTo(hda.getArrivalDate()) <= 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid date assignment for hotel \"" + hda.getHotel().getName() + "\": check-in must happen before check-out!"));
				return false;
			}
			calendar.setTime(hda.getArrivalDate());
			if (!hda.getHotel().getCheckInDaysToInteger().contains(Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK))) && !hda.getHotel().getCheckInDaysToInteger().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid date assignment on the check-in date for hotel \"" + hda.getHotel().getName() + "\"!"));
				return false;
			}
			calendar.setTime(hda.getDepartureDate());
			if (!hda.getHotel().getCheckOutDaysToInteger().contains(Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK))) && !hda.getHotel().getCheckOutDaysToInteger().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Invalid date assignment on the check-out date for hotel \"" + hda.getHotel().getName() + "\"!"));
				return false;
			}
		}
		
		return true;
	}
		
	public PurchaseDTO getPurchase() {
		return purchase;
	}

	public void setPurchase(PurchaseDTO purchase) {
		this.purchase = purchase;
	}
}
