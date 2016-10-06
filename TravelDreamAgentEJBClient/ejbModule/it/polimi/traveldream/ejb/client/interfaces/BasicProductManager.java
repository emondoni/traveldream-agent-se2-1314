package it.polimi.traveldream.ejb.client.interfaces;

import java.util.List;

import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;

import javax.ejb.Local;

@Local
public interface BasicProductManager {
	public void addFlight(FlightDTO flightDTO);
	public void editFlight(FlightDTO flightDTO);
	public void addExcursion(ExcursionDTO excursionDTO);
	public void editExcursion(ExcursionDTO excursionDTO);
	public void addHotel(HotelDTO hotelDTO);
	public void editHotel(HotelDTO hotelDTO);
	public void deleteBasicProduct(BasicProductDTO basicProductDTO);
	public List<ExcursionDTO> retrieveAllAvailableExcursionDTOList();
	public List<FlightDTO> retrieveAllAvailableFlightDTOList();
	public List<HotelDTO> retrieveAllAvailableHotelDTOList();
	public BasicProductDTO retrieveBasicProductDTOById(Integer productID);
}
