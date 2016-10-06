package it.polimi.traveldream.web.beans.helper;

import it.polimi.traveldream.ejb.client.shared.DayOfWeek;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="dayOfWeekConverter")
public class DayOfWeekConverter extends EnumConverter {
	public DayOfWeekConverter() {
		super(DayOfWeek.class);
	}
}
