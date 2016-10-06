package it.polimi.traveldream.ejb.client.shared;

public enum DayOfWeek {
	MONDAY("Monday", "Mon", java.util.Calendar.MONDAY),
	TUESDAY("Tuesday", "Tue", java.util.Calendar.TUESDAY),
	WEDNESDAY("Wednesday", "Wed", java.util.Calendar.WEDNESDAY),
	THURSDAY("Thursday", "Thu", java.util.Calendar.THURSDAY),
	FRIDAY("Friday", "Fri", java.util.Calendar.FRIDAY),
	SATURDAY("Saturday", "Sat", java.util.Calendar.SATURDAY),
	SUNDAY("Sunday", "Sun", java.util.Calendar.SUNDAY);
	
	private String label;
	private String shortLabel;
	private Integer conversion;
	
	private DayOfWeek(String label, String shortLabel, Integer conversion) {
		this.setLabel(label);
		this.shortLabel = shortLabel;
		this.conversion = conversion;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getShortLabel() {
		return shortLabel;
	}

	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	public Integer getConversion() {
		return conversion;
	}
	public void setConversion(Integer conversion) {
		this.conversion = conversion;
	}
}
