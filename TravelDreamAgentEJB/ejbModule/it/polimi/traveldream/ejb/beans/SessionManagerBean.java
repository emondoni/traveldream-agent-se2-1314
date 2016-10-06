package it.polimi.traveldream.ejb.beans;

import it.polimi.traveldream.ejb.client.interfaces.SessionManager;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

@Stateless
public class SessionManagerBean implements SessionManager {
	@Resource
	private EJBContext context;
	
	@Override
	public String getCurrentUsername() {
		return context.getCallerPrincipal().getName();
	}
}
