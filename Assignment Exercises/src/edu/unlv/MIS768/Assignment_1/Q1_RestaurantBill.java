/* Program: Restaurant Bill Calculator
 * Programmer: Sia Mbatia
 * Purpose: This program computes the tax and tip on a restaurant bill. When the user enters the charge for the meal, 
 * 			the program will calculate the tax amount (8.1%), the tip amount (18%), and the total cost. The bill will 
 * 			display the meal charge, tax amount, tip amount and total bill on the screen.
 * FORMATTING NOT DONE
 * Date: January 31, 2019
 */
package edu.unlv.MIS768.Assignment_1;
import javax.swing.JOptionPane; //for user dialog box
import java.text.*; //for formatting strings
import java.util.Locale;

public class Q1_RestaurantBill {

	public static void main(String[] args) {
		//defining variables and constants
		final double TAX_RATE = 0.081;
		final double TIP_RATE = 0.18;
		double mealCharge = 0, taxAmount = 0, tipAmount = 0, totalBill;
		
		//get meal charge from user
		mealCharge = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter total meal charge", "Rebel Restaurant", 1));
		
		while(mealCharge <= 0)
			//prompt user to enter valid amount
			mealCharge = Double.parseDouble(JOptionPane.showInputDialog(null, "Please enter a valid meal charge", "Rebel Restaurant", 1));
		
		//calculate tax, tip, and total bill amounts
		taxAmount = mealCharge * TAX_RATE;
		tipAmount = mealCharge * (1 + TAX_RATE) * TIP_RATE;
		totalBill = mealCharge + taxAmount + tipAmount;
		
		//Display bill amounts
		//Formatting values
		Locale locale = new Locale("en", "US"); 
		NumberFormat formats = NumberFormat.getCurrencyInstance(locale);
		JOptionPane.showMessageDialog(null, "Dining Charges:" +
				"\nMeal Charge: \t \t " + formats.format(mealCharge) + 
				"\nTax Amount (8.1%): \t \t " + formats.format(taxAmount) + 
				"\nTip Amount (18%): \t \t " + formats.format(tipAmount) + 
				"\nTotal Bill: \t \t " + formats.format(totalBill) + 
				"\nThank you for dining with us!", "Rebel Restaurant", 1);
	}

}
