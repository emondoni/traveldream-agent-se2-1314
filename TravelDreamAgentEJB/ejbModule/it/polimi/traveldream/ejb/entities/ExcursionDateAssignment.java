package it.polimi.traveldream.ejb.entities;

import it.polimi.traveldream.ejb.beans.ConversionHelper;
import it.polimi.traveldream.ejb.dto.ExcursionDateAssignmentDTO;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

/**
 * Entity implementation class for Entity: ExcursionDateAssignment
 *
 */

@Embeddable
public class ExcursionDateAssignment implements Serializable {
	@JoinColumn(name="EXCURSION_ID", referencedColumnName="PRODUCT_ID")
	private Excursion excursion;
//	@Temporal(TemporalType.DATE)
	private java.sql.Date date;
	private static final long serialVersionUID = 1L;

	public ExcursionDateAssignment() {
		super();
	}   
	
	public ExcursionDateAssignment(ExcursionDateAssignmentDTO daDTO) {
		this.excursion = (Excursion)ConversionHelper.bpToEntity(daDTO.getExcursion());
		this.date = ConversionHelper.toSQLDate(daDTO.getDate());
	}
	  
	public Excursion getExcursion() {
		return this.excursion;
	}

	public void setExcursion(Excursion excursion) {
		this.excursion = excursion;
	}   
	public java.sql.Date getDate() {
		return this.date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}
   
}
