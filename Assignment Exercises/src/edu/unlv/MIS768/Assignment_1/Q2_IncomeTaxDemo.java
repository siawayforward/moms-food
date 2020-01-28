/* Program: Income Tax Calculator
 * Programmer: Sia Mbatia
 * Purpose: This program computes the income tax based on taxable income and tax bracket for an individual.
 * 			The program also checks to make sure that a valid income amount is entered before computing the
 * 			tax
 * Date: January 31, 2019
 */

package edu.unlv.MIS768.Assignment_1;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner; //for keyboard input

public class Q2_IncomeTaxDemo {

	public static void main(String[] args) {
		// define variables
		double income, taxOwed;
		
		//instantiate person to calculate taxes for
		Individual_Q2 person = new Individual_Q2();
		
		//get income from user input
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("What is your name?"); //name
		person.setName(keyboard.nextLine());
		System.out.println("Please provide your taxable income"); //income
		income = keyboard.nextDouble();
		
		//For formatting values
		Locale locale = new Locale("en", "US"); 
		NumberFormat formats = NumberFormat.getCurrencyInstance(locale);
		
		//check income validity
		if(income < 0) 
			System.out.println("Income tax owed was not calculated. Please provide a valid income amount"); //notify user
		else
		{
			//with correct input, calculate taxes
			taxOwed = person.calcIncomeTax(income);
					
			//display name and tax amount to user
			System.out.println("Dear " + person.getName()+", \n" + 
					"You owe "+ formats.format(taxOwed) + " as tax on your income of " + 
					formats.format(income) + ".");
		}
		
		//close keyboard scanner
		keyboard.close();
	}

}
