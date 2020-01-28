package edu.unlv.MIS768.Assignment_5;

/**
 * ConfRegistration class allows for tracking events attended and registration type for a
 * conference attendee. Methods include getting the total cost for attendance for optional
 * and mandatory fee events.
 * @author Sia Mbatia
 * Date: April 8th 2019
 */
public class ConfRegistration {
	//attributes - events
	private boolean openingDinner = false;
	private boolean eCommerce = false;
	private boolean futureOfWeb = false;
	private boolean advancedJava = false;
	private boolean networkSecurity = false;
	
	//default no arg constructor
	public ConfRegistration() {}
	
	//getters and setters
	public boolean isOpeningDinner() {
		return openingDinner;
	}
	public void setOpeningDinner(boolean openingDinner) {
		this.openingDinner = openingDinner;
	}
	public boolean iseCommerce() {
		return eCommerce;
	}
	public void seteCommerce(boolean eCommerce) {
		this.eCommerce = eCommerce;
	}
	public boolean isFutureOfWeb() {
		return futureOfWeb;
	}
	public void setFutureOfWeb(boolean futureOfWeb) {
		this.futureOfWeb = futureOfWeb;
	}
	public boolean isAdvancedJava() {
		return advancedJava;
	}
	public void setAdvancedJava(boolean advancedJava) {
		this.advancedJava = advancedJava;
	}
	public boolean isNetworkSecurity() {
		return networkSecurity;
	}
	public void setNetworkSecurity(boolean networkSecurity) {
		this.networkSecurity = networkSecurity;
	}	
	/**
	 * Method to calculate total event fees based on user selections and attendee type
	 * @param student: boolean value that indicates whether the registering attendee is a student or not
	 * 		in order to determine the appropriate registration fee
	 * @return the total cost of attendance for the participant
	 */
	public double getTotal(boolean student) {
		double total = 0;
		//type of event participant
		if(student)
			total += ConfFee.STUDENT_ADMISSION.getFee();
		else 
			total += ConfFee.GENERAL_ADMISSION.getFee();
		//other events
		if(openingDinner)
			total += ConfFee.OPENING_DINNER.getFee();
		if(eCommerce)
			total += ConfFee.E_COMMERCE.getFee();
		if(futureOfWeb)
			total += ConfFee.FUTURE_OF_WEB.getFee();
		if(advancedJava)
			total += ConfFee.ADVANCED_JAVA.getFee();
		if(networkSecurity)
			total += ConfFee.NETWORK_SECURITY.getFee();
		//return value
		return total;
	}
}
