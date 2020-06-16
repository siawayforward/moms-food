package momsfood.classes;

public enum DayOfTheWeek {
	//days of the week for cook eligibility
	
	SUNDAY("Sun"),
	MONDAY("Mon"), 
	TUESDAY("Tue"), 
	WEDNESDAY("Wed"), 
	THURSDAY("Thu"), 
	FRIDAY("Fri"), 
	SATURDAY("Sat");
	
	//Variable
	String day;
	
	//Constructor
	DayOfTheWeek(String dow){
		this.day = dow;
	}

	public String getDay() {
		return day;
	}	
}
