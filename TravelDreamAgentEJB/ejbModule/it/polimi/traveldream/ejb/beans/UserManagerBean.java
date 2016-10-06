package it.polimi.traveldream.ejb.beans;

import it.polimi.traveldream.ejb.client.interfaces.UserManager;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.dto.UserDTO;
import it.polimi.traveldream.ejb.entities.CustomPackage;
import it.polimi.traveldream.ejb.entities.Purchase;
import it.polimi.traveldream.ejb.entities.User;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class UserManagerBean
 */
@Stateless
public class UserManagerBean implements UserManager {
	@PersistenceContext
	private EntityManager em;
	
    public UserManagerBean() {
    	
    }
    
    @Override
	public void addUser(UserDTO userDTO) {
    	em.persist(new User(userDTO));
    }
    
    @Override
    @RolesAllowed({Category._ADMINISTRATOR, Category._EMPLOYEE, Category._CUSTOMER})
	public void editUser(UserDTO userDTO) {
    	em.merge(new User(userDTO));
    }
    
    @Override
    @RolesAllowed({Category._ADMINISTRATOR, Category._EMPLOYEE, Category._CUSTOMER})
    public void editUserNoPassword(UserDTO userDTO) {
    	User user = new User(userDTO);
    	String passwordToRetain = retrieveUserByUsername(user.getUsername()).getPassword();
    	user.setPassword(passwordToRetain);
    	
    	em.merge(user);
    }
    
    @Override
    @RolesAllowed({Category._ADMINISTRATOR})
	public void deleteUser(String username) {
    	User toRemove = retrieveUserByUsername(username);
    	if (toRemove.getCategories().contains(Category.CUSTOMER)) {
    		List<Purchase> customerPurchases = em.createNamedQuery(Purchase.byUsername, Purchase.class).setParameter("username", username).getResultList();
    		List<CustomPackage> customerPackages = em.createNamedQuery(CustomPackage.allByUsername, CustomPackage.class).setParameter("author", username).getResultList();
    		
    		for (Purchase p : customerPurchases) {
    			p.setBuyer(null);
    			em.persist(p);
    		}
    		for (CustomPackage cp : customerPackages) {
    			cp.setAuthor(null);
    			cp.setAvailable(false);
    			em.persist(cp);
    		}
    	}
    		
    	em.remove(toRemove);
    }
    
    @Override
    public boolean usernameTaken(String username) {
    	Long result = em.createNamedQuery(User.usernameTaken, Long.class).setParameter("username", username).getSingleResult();
    	return (result.equals(Long.valueOf("1")));
    }
    
    @Override
    public UserDTO retrieveUserDTOByUsername(String username) {
    	return ConversionHelper.userToDTO(retrieveUserByUsername(username));
    }
    
    @Override
    @RolesAllowed({Category._ADMINISTRATOR})
    public List<UserDTO> retrieveAllUsersDTO() {
    	return ConversionHelper.usersToDTO(retrieveAllUsers());
    }
    
    public User retrieveUserByUsername(String username) {
    	try {
        	return em.createNamedQuery(User.byUsername, User.class).setParameter("username", username).getSingleResult();
    	} catch(NoResultException e) {
    		return null;
    	}
    }
    
    @RolesAllowed({Category._ADMINISTRATOR})
    public List<User> retrieveAllUsers() {
    	return em.createNamedQuery(User.all, User.class).getResultList();
    }
}
