package it.polimi.traveldream.web.beans.helper;

import it.polimi.traveldream.ejb.client.interfaces.BasicProductManager;
import it.polimi.traveldream.ejb.dto.BasicProductDTO;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="pickListConverter")
public class PickListConverter implements Converter {
	@EJB
	private BasicProductManager bpMgr;
	
	public PickListConverter() {
		
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return (BasicProductDTO)bpMgr.retrieveBasicProductDTOById(Integer.valueOf(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((BasicProductDTO) arg2).getProductID().toString();
	}

}
