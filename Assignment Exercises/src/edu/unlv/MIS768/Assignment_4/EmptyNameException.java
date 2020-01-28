package edu.unlv.MIS768.Assignment_4;

/**
 * Exception class to handle empty name fields
 * Date: March 29, 2019
 * @author Sia Mbatia
 *
 */
public class EmptyNameException extends NullPointerException {
	
	//define message for error of empty name exception
	public EmptyNameException() {
		super("Error: Employee name was not entered");
	}
}
