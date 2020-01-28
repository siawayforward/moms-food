/* Program: Test Comparison
 * Purpose: This program gets score data from two tests and compares them to see if they are equal after checking for
 * 			valid number of test questions, questions missed, and computing the score for the exam.
 * Section: Class defining attributes and methods of a test object
 * Programmer: Sia Mbatia
 * Date: February 24, 2019
 */

package edu.unlv.MIS768.Assignment_3;
import java.text.DecimalFormat;

public class Test {
	//class attributes
	int numQuestions, numMissed;	
	
	//constructor
	public Test(int questions) {
		if(questions > 0)
			this.numQuestions = questions;
		else 
			this.numQuestions = 0;
	}
	
	//getters and setters
	public int getNumMissed() {
		return numMissed;
	}

	public void setNumMissed(int numMissed) {
		if(numMissed >= 0 || numMissed <= numQuestions)
			this.numMissed = numMissed;
		else
			numMissed = 0;
	}

	public int getNumQuestions() {
		return numQuestions;
	}
	
	public double getPointsEach() {
		double points = 0; //return variable
		if(numQuestions > 0)
			points = 100.0/numQuestions; 
		return points;
	}
	
	public double getScore() {
		//return variable
		double score = (numQuestions - numMissed)*1.0/numQuestions; 
		return score;
	}
	
	public boolean equals(Test test2) {
		if(this.getScore() == test2.getScore())
			return true;
		else 
			return false;
	}
	
	public String toString() {
		String result = ""; //return variable
		//formatting
		DecimalFormat perc = new DecimalFormat("##.##%"); 
		DecimalFormat num = new DecimalFormat("##.##"); 
		if(this.getNumMissed() == 1)
			result += "This test includes " + this.getNumQuestions() + " questions. \n" +
					"Each question is worth " + num.format(this.getPointsEach()) + " points. \n" + 
					"The test taker missed " + this.getNumMissed() + " question, " +
					"and therefore scored " + perc.format(getScore()) + " on this test.";
		else
			result += "This test includes " + this.getNumQuestions() + " questions. \n" +
					"Each question is worth " + num.format(this.getPointsEach()) + " points. \n" + 
					"The test taker missed " + this.getNumMissed() + " questions, " +
					"and therefore scored " + perc.format(getScore()) + " on this test.";
		return result;				
	}	
}
