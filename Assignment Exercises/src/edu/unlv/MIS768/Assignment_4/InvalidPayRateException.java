package edu.unlv.MIS768.Assignment_4;

import java.text.DecimalFormat;

/**
 * Exception class to handle invalid pay rate entries which are below minimum wage of $10.25
 * Date: March 29, 2019
 * @author Sia Mbatia
 *
 */
public class InvalidPayRateException extends Exception {
	
	static //format
	DecimalFormat form = new DecimalFormat("$##.00");
	//define message for error of invalid ID exception
	public InvalidPayRateException(double p) {
		super("Error: " + form.format(p) + " is not a valid hourly pay rate. Your pay should be at least $10.25 per hour.");
	}
}
