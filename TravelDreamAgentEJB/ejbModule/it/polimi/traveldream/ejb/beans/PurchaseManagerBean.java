package it.polimi.traveldream.ejb.beans;

import it.polimi.traveldream.ejb.client.interfaces.PurchaseManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;
import it.polimi.traveldream.ejb.entities.Purchase;
import it.polimi.traveldream.ejb.entities.TravelPackage;
import it.polimi.traveldream.ejb.entities.User;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(PurchaseManager.class)
public class PurchaseManagerBean implements PurchaseManager {
	@PersistenceContext
	private EntityManager em;

	@Override
	@RolesAllowed({Category._CUSTOMER})
	public void addPurchase(PurchaseDTO purchaseDTO) {
		User buyer = em.createNamedQuery(User.byUsername, User.class).setParameter("username", purchaseDTO.getBuyer().getUsername()).getSingleResult();
		TravelPackage boughtPackage = em.createNamedQuery(TravelPackage.byId, TravelPackage.class).setParameter("packageID", purchaseDTO.getBoughtPackage().getPackageID()).getSingleResult();
		Purchase toPersist = new Purchase(purchaseDTO);
		toPersist.setBuyer(buyer);
		toPersist.setBoughtPackage(boughtPackage);
		
		em.persist(toPersist);
	}
	
	@Override
	@RolesAllowed({Category._CUSTOMER})
	public List<PurchaseDTO> retrievePurchaseDTOListByUser(String username) {
		return ConversionHelper.purchaseListToDTO(retrievePurchasesByUser(username));
	}
	
	@RolesAllowed({Category._CUSTOMER})
	public List<Purchase> retrievePurchasesByUser(String username) {
		return em.createNamedQuery(Purchase.byUsername, Purchase.class).setParameter("username", username).getResultList();
	}

}
