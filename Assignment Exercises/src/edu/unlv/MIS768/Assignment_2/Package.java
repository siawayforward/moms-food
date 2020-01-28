/* Program: Shipping Cost Calculator
 * Purpose: This application computes the shipping cost of a package based on the weight (in pounds) and dimensions
 * 			(in inches) given by the user. If a package is overweight or oversize, the user will be notified and no
 * 			calculation will be computed unless their values are within the limits. The user dimension values are 
 * 			then used to compute the dimensional weight in pounds, and this is compared to the weight value provided
 * 			by the user. Whichever weight is higher becomes the billable weight, and this is used to calculate the
 * 			shipping cost which is displayed to the user at the end.
 * Section: Class that defines a package's attributes and methods
 * Programmer: Sia Mbatia
 * Date: February 12th, 2019
 */

package edu.unlv.MIS768.Assignment_2;

public class Package {
	//class attributes and constants
	double cost, weight, height, width, length;
	final double OVERWEIGHT = 150;
	final double OVERSIZE = 110;
	final double UNIT_COST = 2.5;
	final double WT_CONVERTER = 166;

	//class methods
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getWeight() {
		return weight;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getLength() {
		return length;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setLength(double length) {
		this.length = length;
	}
	 
	
	/**Method to calculate and check linear inches and verify weight entered
	 * @return indicates true if the package is not overweight and oversize and false if it is either
	 */
	public boolean validateSize() {
		//calculate linear inches
		double linearDim = length + width + height;
		
		if(linearDim > OVERSIZE)
			return false;
		else
			return true;
	}
	/**
	 * Method to check whether or not the weight entered for the package is within the required limit
	 * @return boolean indicator to show whether the weight is over (false) or within (true) the limit
	 */
	public boolean validateWeight() {
		//compare given weight to limit
		if (weight > OVERWEIGHT)
			return false;
		else 
			return true;
	}
	
	public double[] getShippingCost() {
		//define output array and variables
		double dimWeight = 0;
		//calculate the dimensional weight
		dimWeight = (int)Math.ceil(length) * (int)Math.ceil(width) * (int)Math.ceil(height);
		dimWeight = (int)Math.ceil(dimWeight/ WT_CONVERTER);
		
		//Compare given weight to dimension weight
		double billableWt = 0;
		if(dimWeight > weight)
			billableWt = dimWeight; //if dim weight is higher
		else 
			billableWt = weight; //if given weight is higher/equal
		//calculate cost based on billable weight
		cost = billableWt * UNIT_COST;
		
		//fill array
		if(dimWeight > weight)
		{
			double[] dimCosts = {(int)Math.ceil(length), (int)Math.ceil(width), (int)Math.ceil(height), dimWeight, cost};
			return dimCosts;
		}
		else
		{
			double[] costs = {length, width, height, weight, cost};
			return costs;
		}
	}
}
