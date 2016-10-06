package it.polimi.traveldream.ejb.beans;

import it.polimi.traveldream.ejb.dto.BasicProductDTO;
import it.polimi.traveldream.ejb.dto.CustomPackageDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDTO;
import it.polimi.traveldream.ejb.dto.ExcursionDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.FlightDTO;
import it.polimi.traveldream.ejb.dto.FlightDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.HotelDTO;
import it.polimi.traveldream.ejb.dto.HotelDateAssignmentDTO;
import it.polimi.traveldream.ejb.dto.PhotoDTO;
import it.polimi.traveldream.ejb.dto.PurchaseDTO;
import it.polimi.traveldream.ejb.dto.TravelPackageDTO;
import it.polimi.traveldream.ejb.dto.UserDTO;
import it.polimi.traveldream.ejb.entities.BasicProduct;
import it.polimi.traveldream.ejb.entities.CustomPackage;
import it.polimi.traveldream.ejb.entities.Excursion;
import it.polimi.traveldream.ejb.entities.ExcursionDateAssignment;
import it.polimi.traveldream.ejb.entities.Flight;
import it.polimi.traveldream.ejb.entities.FlightDateAssignment;
import it.polimi.traveldream.ejb.entities.Hotel;
import it.polimi.traveldream.ejb.entities.HotelDateAssignment;
import it.polimi.traveldream.ejb.entities.Photo;
import it.polimi.traveldream.ejb.entities.Purchase;
import it.polimi.traveldream.ejb.entities.TravelPackage;
import it.polimi.traveldream.ejb.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ConversionHelper {
	public static List<BasicProductDTO> componentsListToDTO(List<BasicProduct> list) {
		List<BasicProductDTO> toReturn = new ArrayList<BasicProductDTO>();
		for (BasicProduct bp : list)
			toReturn.add(bpToDTO(bp));
		
		return toReturn;
	}
	
	public static List<BasicProduct> componentsListToEntity(List<BasicProductDTO> list) {
		List<BasicProduct> toReturn = new ArrayList<BasicProduct>();
		for (BasicProductDTO bp : list)
			toReturn.add(bpToEntity(bp));
		
		return toReturn;
	}
	
	public static <T extends TravelPackageDTO, S extends TravelPackage> List<T> packageListToDTO(List<S> list, Class<T> type) {
		ArrayList<T> toReturn = new ArrayList<T>();
		if (list != null)
			for (S tp : list)
				toReturn.add(type.cast(packToDTO(tp)));
		
		return toReturn;
	}
	
	public static CustomPackageDTO customFromOriginal(TravelPackageDTO originalModified) {
		CustomPackageDTO toReturn = new CustomPackageDTO();
		toReturn.setName(originalModified.getName());
		toReturn.setDescription(originalModified.getDescription());
		toReturn.setComponents(originalModified.getComponents());
		toReturn.setPhotos(originalModified.getPhotos());
		
		return toReturn;
	}
	
	public static TravelPackageDTO packToDTO(TravelPackage tp) {
		if (tp instanceof CustomPackage) return customToDTO((CustomPackage) tp);
		else return predefinedToDTO(tp);
	}
	
	public static TravelPackageDTO predefinedToDTO(TravelPackage tp) {
		TravelPackageDTO toReturn = new TravelPackageDTO();
		toReturn.setPackageID(tp.getPackageID());
		toReturn.setName(tp.getName());
		toReturn.setPrice(tp.getPrice());
		toReturn.setDescription(tp.getDescription());
		toReturn.setComponents(componentsListToDTO(tp.getComponents()));
		toReturn.setPhotos(photosToDTO(tp.getPhotos()));
		toReturn.setAvailable(tp.getAvailable());
		
		return toReturn;
	}
	
	public static CustomPackageDTO customToDTO(CustomPackage cp) {
		CustomPackageDTO toReturn = new CustomPackageDTO();
		toReturn.setPackageID(cp.getPackageID());
		toReturn.setName(cp.getName());
		toReturn.setPrice(cp.getPrice());
		toReturn.setDescription(cp.getDescription());
		toReturn.setComponents(componentsListToDTO(cp.getComponents()));
		toReturn.setPhotos(photosToDTO(cp.getPhotos()));
		toReturn.setAvailable(cp.getAvailable());
		toReturn.setBasedOn(predefinedToDTO(cp.getBasedOn()));
		toReturn.setAuthor(userToDTO(cp.getAuthor()));
		
		return toReturn;
	} 
	
	public static UserDTO userToDTO(User user) {
    	UserDTO toReturn = new UserDTO();
    	toReturn.setUsername(user.getUsername());
    	toReturn.setPassword(user.getPassword());
    	toReturn.setName(user.getName());
    	toReturn.setSurname(user.getSurname());
    	toReturn.setDateOfBirth(user.getDateOfBirth());
    	toReturn.setHomeAddress(user.getHomeAddress());
    	toReturn.seteMailAddress(user.getEMailAddress());
    	toReturn.setCategory(user.getCategories().get(0));
    	
    	return toReturn;
    }
	
	public static List<UserDTO> usersToDTO(List<User> list) {
    	List<UserDTO> toReturn = new ArrayList<UserDTO>();
    	for (User u : list) {
    		toReturn.add(userToDTO(u));
    	}
    	
    	return toReturn;
    }
	
	public static List<Photo> photosToEntity(List<PhotoDTO> list) {
		List<Photo> toReturn = new ArrayList<Photo>();
		if (list != null)
			for (PhotoDTO p : list)
				toReturn.add(new Photo(p));
		
		return toReturn;
	}
	
	public static List<PhotoDTO> photosToDTO(List<Photo> list) {
		List<PhotoDTO> toReturn = new ArrayList<PhotoDTO>();
		if (list != null)
			for (Photo p : list)
				toReturn.add(photoToDTO(p));
		
		return toReturn;
	}
	
	public static BasicProductDTO bpToDTO(BasicProduct basicProduct) {
    	if (basicProduct instanceof Excursion) return excursionToDTO((Excursion)basicProduct);
    	else if (basicProduct instanceof Flight) return flightToDTO((Flight)basicProduct);
    	else if (basicProduct instanceof Hotel)	return hotelToDTO((Hotel)basicProduct);
    	
    	return null;
    }
	
	public static BasicProduct bpToEntity(BasicProductDTO basicProductDTO) {
		if (basicProductDTO instanceof ExcursionDTO) return new Excursion((ExcursionDTO) basicProductDTO);
		else if (basicProductDTO instanceof FlightDTO) return new Flight((FlightDTO) basicProductDTO);
		else if (basicProductDTO instanceof HotelDTO) return new Hotel((HotelDTO) basicProductDTO);
		
		return null;
	}
	
	public static ExcursionDTO excursionToDTO(Excursion excursion) {
    	ExcursionDTO toReturn = new ExcursionDTO();
    	toReturn.setProductID(excursion.getProductID());
    	toReturn.setPrice(excursion.getPrice());
    	toReturn.setDescription(excursion.getDescription());
//    	toReturn.setPhotos(excursion.getPhotos());
    	toReturn.setAvailable(excursion.getAvailable());
    	toReturn.setName(excursion.getName());
    	toReturn.setDeparturePlace(excursion.getDeparturePlace());
    	toReturn.setDepartureTime(excursion.getDepartureTime());
    	toReturn.setArrivalPlace(excursion.getArrivalPlace());
    	toReturn.setArrivalTime(excursion.getArrivalTime());
    	toReturn.setVisits(excursion.getVisits());
    	toReturn.setFrequency(excursion.getFrequency());
    	
    	return toReturn;
    }
    
    public static FlightDTO flightToDTO(Flight flight) {
    	FlightDTO toReturn = new FlightDTO();
    	toReturn.setProductID(flight.getProductID());
    	toReturn.setPrice(flight.getPrice());
    	toReturn.setDescription(flight.getDescription());
//    	toReturn.setPhotos(flight.getPhotos());
    	toReturn.setAvailable(flight.getAvailable());
    	toReturn.setCallSign(flight.getCallSign());
    	toReturn.setDeparturePlace(flight.getDeparturePlace());
    	toReturn.setDepartureTime(flight.getDepartureTime());
    	toReturn.setArrivalPlace(flight.getArrivalPlace());
    	toReturn.setArrivalTime(flight.getArrivalTime());
    	toReturn.setFrequency(flight.getFrequency());
    	
    	return toReturn;
    }

    public static HotelDTO hotelToDTO(Hotel hotel) {
    	HotelDTO toReturn = new HotelDTO();
    	toReturn.setProductID(hotel.getProductID());
    	toReturn.setPrice(hotel.getPrice());
    	toReturn.setDescription(hotel.getDescription());
//    	toReturn.setPhotos(hotel.getPhotos());
    	toReturn.setAvailable(hotel.getAvailable());
    	toReturn.setName(hotel.getName());
    	toReturn.setAddress(hotel.getAddress());
    	toReturn.setCheckInDays(hotel.getCheckInDays());
    	toReturn.setCheckInTime(hotel.getCheckInTime());
    	toReturn.setCheckOutDays(hotel.getCheckOutDays());
    	toReturn.setCheckOutTime(hotel.getCheckOutTime());
    	
    	return toReturn;	
    }
    
    public static PhotoDTO photoToDTO(Photo photo) {
    	PhotoDTO toReturn = new PhotoDTO();
    	toReturn.setPhotoID(photo.getPhotoID());
    	toReturn.setFilename(photo.getFilename());
    	toReturn.setPhoto(photo.getPhoto());
    	
    	return toReturn;
    }

	public static List<PurchaseDTO> purchaseListToDTO(List<Purchase> list) {
		ArrayList<PurchaseDTO> toReturn = new ArrayList<PurchaseDTO>();
		for (Purchase p : list)
			toReturn.add(purchaseToDTO(p));
		
		return toReturn;
	}

	public static PurchaseDTO purchaseToDTO(Purchase purchase) {
		PurchaseDTO toReturn = new PurchaseDTO();
		toReturn.setPurchaseID(purchase.getPurchaseID());
		toReturn.setBuyer(userToDTO(purchase.getBuyer()));
		toReturn.setBoughtPackage(packToDTO(purchase.getBoughtPackage()));
		toReturn.setExcursionDateAssignments(excursionAssignmentListToDTO(purchase.getExcursionDateAssignments()));
		toReturn.setFlightDateAssignments(flightAssignmentListToDTO(purchase.getFlightDateAssignments()));
		toReturn.setHotelDateAssignments(hotelAssignmentListToDTO(purchase.getHotelDateAssignments()));
		
		return toReturn;
	}

	public static List<ExcursionDateAssignmentDTO> excursionAssignmentListToDTO(List<ExcursionDateAssignment> list) {
		ArrayList<ExcursionDateAssignmentDTO> toReturn = new ArrayList<ExcursionDateAssignmentDTO>();
		for (ExcursionDateAssignment da : list)
			toReturn.add(excursionAssignmentToDTO(da));
		
		return toReturn;
	}
	
	public static List<ExcursionDateAssignment> excursionAssignmentDTOListToEntity(List<ExcursionDateAssignmentDTO> list) {
		ArrayList<ExcursionDateAssignment> toReturn = new ArrayList<ExcursionDateAssignment>();
		for (ExcursionDateAssignmentDTO da : list)
			toReturn.add(new ExcursionDateAssignment(da));
		
		return toReturn;
	}
	
	public static ExcursionDateAssignmentDTO excursionAssignmentToDTO(ExcursionDateAssignment da) {
		ExcursionDateAssignmentDTO toReturn = new ExcursionDateAssignmentDTO();
		toReturn.setExcursion((ExcursionDTO)bpToDTO(da.getExcursion()));
		toReturn.setDate(da.getDate());
		
		return toReturn;
	}

	public static List<FlightDateAssignmentDTO> flightAssignmentListToDTO(List<FlightDateAssignment> list) {
		ArrayList<FlightDateAssignmentDTO> toReturn = new ArrayList<FlightDateAssignmentDTO>();
		for (FlightDateAssignment da : list)
			toReturn.add(flightAssignmentToDTO(da));
		
		return toReturn;
	}
	
	public static List<FlightDateAssignment> flightAssignmentDTOListToEntity(List<FlightDateAssignmentDTO> list) {
		ArrayList<FlightDateAssignment> toReturn = new ArrayList<FlightDateAssignment>();
		for (FlightDateAssignmentDTO da : list)
			toReturn.add(new FlightDateAssignment(da));
		
		return toReturn;
	}
	
	public static FlightDateAssignmentDTO flightAssignmentToDTO(FlightDateAssignment da) {
		FlightDateAssignmentDTO toReturn = new FlightDateAssignmentDTO();
		toReturn.setFlight((FlightDTO)bpToDTO(da.getFlight()));
		toReturn.setDate(da.getDate());
		
		return toReturn;
	}

	public static List<HotelDateAssignmentDTO> hotelAssignmentListToDTO(List<HotelDateAssignment> list) {
		ArrayList<HotelDateAssignmentDTO> toReturn = new ArrayList<HotelDateAssignmentDTO>();
		for (HotelDateAssignment da : list)
			toReturn.add(hotelAssignmentToDTO(da));
		
		return toReturn;
	}
	
	public static List<HotelDateAssignment> hotelAssignmentDTOListToEntity(List<HotelDateAssignmentDTO> list) {
		ArrayList<HotelDateAssignment> toReturn = new ArrayList<HotelDateAssignment>();
		for (HotelDateAssignmentDTO da : list)
			toReturn.add(new HotelDateAssignment(da));
		
		return toReturn;
	}
	
	public static HotelDateAssignmentDTO hotelAssignmentToDTO(HotelDateAssignment da) {
		HotelDateAssignmentDTO toReturn = new HotelDateAssignmentDTO();
		toReturn.setHotel((HotelDTO)bpToDTO(da.getHotel()));
		toReturn.setArrivalDate(da.getArrivalDate());
		toReturn.setDepartureDate(da.getDepartureDate());
		
		return toReturn;
	}
	
	public static java.sql.Date toSQLDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}
