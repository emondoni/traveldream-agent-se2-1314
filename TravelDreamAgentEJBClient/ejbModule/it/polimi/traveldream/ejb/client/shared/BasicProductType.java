package it.polimi.traveldream.ejb.client.shared;

public enum BasicProductType {
	EXCURSION("Excursion"), FLIGHT("Flight"), HOTEL("Hotel");
	
	private String label;
	
	private BasicProductType(String label) {
		this.label = label;
	}
	
	public static final String _EXCURSION = "EXCURSION";
	public static final String _FLIGHT = "FLIGHT";
	public static final String _HOTEL = "HOTEL";

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}
