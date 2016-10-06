package it.polimi.traveldream.ejb.dto;

import java.util.Date;

import javax.validation.constraints.*;

public class ExcursionDateAssignmentDTO {
	@NotNull
	private ExcursionDTO excursion;
	@NotNull(message="A date must be chosen for this excursion!")
	@Future(message="You must choose a future date for this excursion!")
	private Date date;
	
	public ExcursionDateAssignmentDTO() {
		super();
	}
	
	public ExcursionDateAssignmentDTO(ExcursionDTO excursion) {
		this.excursion = excursion;
	}
	
	public ExcursionDTO getExcursion() {
		return excursion;
	}
	public void setExcursion(ExcursionDTO excursion) {
		this.excursion = excursion;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
