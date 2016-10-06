package it.polimi.traveldream.ejb.client.shared;

public enum Category {
	ADMINISTRATOR("Administrator"), EMPLOYEE("Employee"), CUSTOMER("Customer");
	
	private String label;
	
	public static final String _ADMINISTRATOR = "ADMINISTRATOR";
	public static final String _EMPLOYEE = "EMPLOYEE";
	public static final String _CUSTOMER = "CUSTOMER";
	
	private Category(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
