package it.polimi.traveldream.ejb.client.interfaces;

import java.util.List;

import it.polimi.traveldream.ejb.dto.PurchaseDTO;

public interface PurchaseManager {
	public void addPurchase(PurchaseDTO purchaseDTO);
	public List<PurchaseDTO> retrievePurchaseDTOListByUser(String username);
}
