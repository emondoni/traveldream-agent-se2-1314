package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.ejb.client.interfaces.PurchaseManager;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="buyListBean")
@ViewScoped
public class PersonalPurchaseManagementWebBean {
	@EJB
	private PurchaseManager buyMgr;
	@ManagedProperty("#{sessionMgr}")
	private SessionManagerWebBean sessionMgr;
	private List<PurchaseDTO> purchaseList;
	private PurchaseDTO selectedPurchase;
	
	public PersonalPurchaseManagementWebBean() {
		
	}
	
	@PostConstruct
	public void initializeFields() {
		this.purchaseList = buyMgr.retrievePurchaseDTOListByUser(sessionMgr.getCurrentUser().getUsername());
	}

	public SessionManagerWebBean getSessionMgr() {
		return sessionMgr;
	}

	public void setSessionMgr(SessionManagerWebBean sessionMgr) {
		this.sessionMgr = sessionMgr;
	}

	public List<PurchaseDTO> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<PurchaseDTO> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public PurchaseDTO getSelectedPurchase() {
		return selectedPurchase;
	}

	public void setSelectedPurchase(PurchaseDTO selectedPurchase) {
		this.selectedPurchase = selectedPurchase;
	}
}
