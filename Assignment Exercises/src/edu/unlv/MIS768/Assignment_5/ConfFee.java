package edu.unlv.MIS768.Assignment_5;
/**
 * Enum class to store the fees and types of events for the conference registration to be used with confRegistration class
 * @author Sia Mbatia
 * Date: April 9th, 2019
 */
public enum ConfFee {
	
	GENERAL_ADMISSION (895.00),
	STUDENT_ADMISSION (495.00),
	OPENING_DINNER(30.00),
	E_COMMERCE(295.00),
	FUTURE_OF_WEB(295.00),
	ADVANCED_JAVA(395.00),
	NETWORK_SECURITY(395.00);
	
	private double fee;
	
	ConfFee (double confFee){
		this.fee = confFee;
	}	

	double getFee(){
		return this.fee;
	}
}
