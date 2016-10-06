package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.PurchaseManager;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="billMgmtBean")
@ViewScoped
public class BillingManagementWebBean {
	@EJB
	private PurchaseManager buyMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private PurchaseDTO purchase;
	
	public BillingManagementWebBean() {
		
	}
	
	@PostConstruct
	public void initializeFields() {
		this.purchase = (PurchaseDTO)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("purchaseDTO");
	}
	
	public String bankBlackBox() {
		if ((new Random()).nextBoolean()) {
			addPurchase();
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("justBought", purchase.getBoughtPackage());
			return "success?faces-redirect=true";
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "There has been an error while processing your billing data. Please try again."));
			return null;
		}
	}
	
	public void addPurchase() {
		purchase.setBuyer(sessionMgr.getCurrentUser());
		buyMgr.addPurchase(purchase);
	}
	
	public String goBack() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("packToBuy", purchase.getBoughtPackage());
		return "purchase?faces-redirect=true";
	}

	public SessionManagerWebBean getSessionMgr() {
		return sessionMgr;
	}

	public void setSessionMgr(SessionManagerWebBean sessionMgr) {
		this.sessionMgr = sessionMgr;
	}

	public PurchaseDTO getPurchase() {
		return purchase;
	}

	public void setPurchase(PurchaseDTO purchase) {
		this.purchase = purchase;
	}
	
}
