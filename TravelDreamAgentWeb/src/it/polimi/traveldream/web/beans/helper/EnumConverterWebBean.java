package it.polimi.traveldream.web.beans.helper;

import it.polimi.traveldream.ejb.client.shared.BasicProductType;
import it.polimi.traveldream.ejb.client.shared.Category;
import it.polimi.traveldream.ejb.client.shared.DayOfWeek;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="enumConverter")
@ApplicationScoped
public class EnumConverterWebBean {
	
	public Category[] getCategories() {
		return Category.values();
	}
	
	public Category[] getCategoriesForAdmin() {
		Category[] toReturn = {Category.ADMINISTRATOR, Category.EMPLOYEE};
		return toReturn;
	}
	
	public BasicProductType[] getTypes() {
		return BasicProductType.values();
	}
	
	public DayOfWeek[] getDaysOfWeek() {
		return DayOfWeek.values();
	}
}
