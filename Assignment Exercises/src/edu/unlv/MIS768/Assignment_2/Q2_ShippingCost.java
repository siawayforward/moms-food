/* Program: Shipping Cost Calculator
 * Purpose: This application computes the shipping cost of a package based on the weight (in pounds) and dimensions
 * 			(in inches) given by the user. If a package is overweight or oversize, the user will be notified and no
 * 			calculation will be computed unless their values are within the limits. The user dimension values are 
 * 			then used to compute the dimensional weight in pounds, and this is compared to the weight value provided
 * 			by the user. Whichever weight is higher becomes the billable weight, and this is used to calculate the
 * 			shipping cost which is displayed to the user at the end.
 * Section: Main method
 * Programmer: Sia Mbatia
 * Date: February 12th, 2019
 */

package edu.unlv.MIS768.Assignment_2;

import java.util.*;
import java.text.*;

public class Q2_ShippingCost {

	public static void main(String[] args) {
		//output variable
		double[] values = null;
		//initialize package to review
		Package box = new Package();
		//create new scanner object to get inputs
		Scanner kb = new Scanner(System.in);
		//ask user for inputs
		//weight
		System.out.print("Please enter your package weight in pounds: ");
		box.setWeight(kb.nextDouble());
		while (box.validateWeight() == false || box.getWeight() <= 0) {
			//re-enter weight if invalid
			System.out.print("Please enter a valid weight for your package in pounds: ");
			box.setWeight(kb.nextDouble());
		}
		//height, length, width
		System.out.print("Please enter your package's length in inches: ");
		box.setLength(kb.nextDouble());
		while(box.getLength() <= 0)
		{
			System.out.print("Please enter a valid length for your package in inches: ");
			box.setLength(kb.nextDouble());
		}
		System.out.print("Please enter your package's width in inches: ");
		box.setWidth(kb.nextDouble());
		while(box.getWidth() <= 0)
		{
			System.out.print("Please enter a valid width for your package in inches: ");
			box.setWidth(kb.nextDouble());
		}
		System.out.print("Please enter your package's height in inches: ");
		box.setHeight(kb.nextDouble());
		while(box.getHeight() <= 0)
		{
			System.out.print("Please enter a valid height for your package in inches: ");
			box.setHeight(kb.nextDouble());
		}
		while (box.validateSize() == false)	{
			//Otherwise, oversize
			System.out.println("Oversize: Please re-enter a valid dimensions to proceed. ");
			//retrieve values again
			System.out.println("Adjusted Length in inches: ");
			box.setLength(kb.nextDouble());
			System.out.println("Adjusted Width in inches: ");
			box.setWidth(kb.nextDouble());
			System.out.println("Adjusted Height in inches: ");
			box.setHeight(kb.nextDouble());
		}
		
		//calculate and compare weights
		values = box.getShippingCost(); 
		
		//display shipping costs
		System.out.println("Package Dimensions (L x W x H): " + values[0] + " in. x " + values[1] + " in. x "+ values[2] +" in.");
		System.out.println("Package Billable Weight: " + values[3] + " pounds");
		//formatting
		Locale locale = new Locale("en", "US"); 
		NumberFormat formats = NumberFormat.getCurrencyInstance(locale);
		System.out.println("Package Shipping Cost: " + formats.format(values[4]));
			
		kb.close(); //close scanner object
	}

}
