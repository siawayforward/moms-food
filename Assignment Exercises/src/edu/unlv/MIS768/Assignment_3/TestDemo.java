/* Program: Test Comparison
 * Purpose: This program gets score data from two tests and compares them to see if they are equal after checking for
 * 			valid number of test questions, questions missed, and computing the score for the exam.
 * Section: Demo class
 * Programmer: Sia Mbatia
 * Date: February 24, 2019
 */

package edu.unlv.MIS768.Assignment_3;
import java.util.*;

public class TestDemo {

	public static void main(String[] args) {
		//variables for input values
		int questions1, questions2;
		
		//scanner object for inputs
		Scanner kb = new Scanner(System.in);
		
		//ask user to give first test details
		System.out.print("Please enter the number of questions for the first test: ");
		questions1 = kb.nextInt();
		System.out.print("Please enter the number of questions missed for the first test: ");
		Test test1 = new Test(questions1); //new test object
		test1.setNumMissed(kb.nextInt()); 
		System.out.println(test1.toString()); //display results
		
		System.out.println();
		//ask user to give second test details
		System.out.print("Please enter the number of questions for the second test: ");
		questions2 = kb.nextInt();
		System.out.print("Please enter the number of questions missed for the second test: ");
		Test test2 = new Test(questions2); //new test object
		test2.setNumMissed(kb.nextInt()); 
		System.out.println(test2.toString()); //display results
		
		System.out.println();
		//compare the two tests
		if(test1.equals(test2))
			System.out.println("The scores of the two tests are the same");
		else
			System.out.println("The scores of the two tests are not the same");
		
		kb.close(); //close scanner object
	}
}
