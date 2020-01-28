package edu.unlv.MIS768.Assignment_4;
/**
 * Exception class to handle invalid employee ID entries
 * Date: March 29, 2019
 * @author Sia Mbatia
 *
 */
public class InvalidEmployeeIDException extends Exception {
	
	//define message for error of invalid ID exception
	public InvalidEmployeeIDException(int i) {
		super("Error: " + i + " is not a valid employee ID");
	}
}
