/* Program: Income Tax Calculator
 * Programmer: Sia Mbatia
 * Purpose: This class identifies an individual's tax bracket from user input, and calculates the income tax owed by the person.
 * 			Section: Employee class with attributes and methods to identify tax bracket based on income, and calculate taxes owed
 * Date: January 31, 2019
 */

package edu.unlv.MIS768.Assignment_1;

public class Individual_Q2 {
	//define employee attributes and tax brackets
	String name;
	double taxableIncome;
	double incomeTax; //returned value from calculation
	
	//tax brackets
	final double TAX_BR_1 = 0;
	final double TAX_BR_2 = 9325;
	final double TAX_BR_3 = 37950;
	final double TAX_BR_4 = 91900;
	final double TAX_BR_5 = 191650;
	final double TAX_BR_6 = 416700;
	final double TAX_BR_7 = 418400;
	
	//tax bracket rates
	final double TB1_RATE = 0.1;
	final double TB2_RATE = 0.15;
	final double TB3_RATE = 0.25;
	final double TB4_RATE = 0.28;
	final double TB5_RATE = 0.33;
	final double TB6_RATE = 0.35;
	final double TB7_RATE = 0.396;

	//constructors for employee class
	//default employee constructor
	public Individual_Q2() {
				taxableIncome = 0;
				incomeTax = 0;
			}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//method to identify highest tax bracket based on income
	public double calcIncomeTax(double taxableIncome) {
		//identify tax brackets and calculate tax based on bracket
		if(taxableIncome > TAX_BR_1 && taxableIncome <= TAX_BR_2) //0 to 9,325
			incomeTax = taxableIncome * TB1_RATE;		
		else if(taxableIncome > TAX_BR_2 && taxableIncome <= TAX_BR_3) //9,325 to 37,950
			incomeTax = TAX_BR_2 * TB1_RATE + (taxableIncome - TAX_BR_2)* TB2_RATE;		
		else if(taxableIncome > TAX_BR_3 && taxableIncome <= TAX_BR_4) //37,950 to 91,900
			incomeTax = TAX_BR_2 * TB1_RATE + (TAX_BR_3 - TAX_BR_2) * TB2_RATE + (taxableIncome - TAX_BR_3) * TB3_RATE;
		else if(taxableIncome > TAX_BR_4 && taxableIncome <= TAX_BR_5) //91,900 to 191,650
			incomeTax = TAX_BR_2 * TB1_RATE + (TAX_BR_3 - TAX_BR_2) * TB2_RATE + (TAX_BR_4 - TAX_BR_3) * TB3_RATE 
						+ (taxableIncome - TAX_BR_4) * TB4_RATE;
		else if(taxableIncome > TAX_BR_5 && taxableIncome <= TAX_BR_6) //191,650 to 416,700
			incomeTax = TAX_BR_2 * TB1_RATE + (TAX_BR_3 - TAX_BR_2) * TB2_RATE + (TAX_BR_4 - TAX_BR_3) * TB3_RATE + 
						(TAX_BR_5 - TAX_BR_4) * TB4_RATE + (taxableIncome - TAX_BR_5) * TB5_RATE;
		else if(taxableIncome > TAX_BR_6 && taxableIncome <= TAX_BR_7) //416,700 to 418,400
			incomeTax = TAX_BR_2 * TB1_RATE + (TAX_BR_3 - TAX_BR_2) * TB2_RATE + (TAX_BR_4 - TAX_BR_3) * TB3_RATE + 
						(TAX_BR_5 - TAX_BR_4) * TB4_RATE + (TAX_BR_6 - TAX_BR_5) * TB5_RATE + (taxableIncome - TAX_BR_6) * TB6_RATE;
		else if(taxableIncome > TAX_BR_7) //over 418,400
			incomeTax = TAX_BR_2 * TB1_RATE + (TAX_BR_3 - TAX_BR_2) * TB2_RATE + (TAX_BR_4 - TAX_BR_3) * TB3_RATE + 
						(TAX_BR_5 - TAX_BR_4) * TB4_RATE + (TAX_BR_6 - TAX_BR_5) * TB5_RATE + (TAX_BR_7 - TAX_BR_6) * TB6_RATE + 
						(taxableIncome - TAX_BR_7) * TB7_RATE;
		else
			incomeTax = 0;
			
		return incomeTax;
	}
}
