package edu.unlv.MIS768.Assignment_4;

/**
 * Exception class to handle invalid hour entries (any values not between 0 and 84)
 * Date: March 29, 2019
 * @author Sia Mbatia
 *
 */
public class InvalidHoursWorkedException extends Exception {
	
	//define message for error of invalid ID exception
	public InvalidHoursWorkedException(double h) {
		super("Error: The system won't accept " + h + " hours of work. Please enter a value between 0 and 84 hours.");
	}
}
