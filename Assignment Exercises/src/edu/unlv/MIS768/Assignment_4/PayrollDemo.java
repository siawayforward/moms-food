package edu.unlv.MIS768.Assignment_4;

import java.text.DecimalFormat;
import java.util.Scanner;
/**
 * Class to demonstrate the creation of a payroll record for an employee to display name, id, payrate, hours, and
 * calculate the gross pay for that employee
 * Date: March 29, 2019
 * @author Sia Mbatia
 *
 */
public class PayrollDemo {

	public static void main(String[] args) {
		//create scanner object
		Scanner kb = new Scanner(System.in);
		//input variables
		String name = "";
		int empID = 0;
		
		//instantiate a new payroll calculation
		try {
			//get user id and name
			System.out.print("What is your name? ");
			name = kb.nextLine(); 
			System.out.print("Please provide your employee ID: ");
			empID = kb.nextInt();
			Payroll emp = new Payroll(name, empID);
			//get and set number of hours worked and pay rate
			System.out.print("Please enter the number of hours worked this week: ");
			try {
				emp.setHoursWorked(kb.nextDouble());
				
				System.out.print("Please provide your current hourly pay rate: ");
				try {
					emp.setPayRate(kb.nextDouble());
					
					//calculate the employee's gross pay and display employee detail
					System.out.println(); //extra space
					DecimalFormat form = new DecimalFormat("$##.00");
					System.out.print("Employee Name: " + emp.getName() + "\n"
							+ "Employee ID: " + emp.getIdNumber() + "\n"
							+ "Week Detail: \n---------------- \n" + 
								emp.getHoursWorked() + " hours worked at " + form.format(emp.getPayRate()) + " per hour \n"
							+ "Gross Pay: " + form.format(emp.getGrossPay()));	
					
				} catch (InvalidPayRateException e) {
					// get error message
					System.out.print(e.getMessage());
				}				
			} catch (InvalidHoursWorkedException e) {
				// get error message
				System.out.print(e.getMessage());
			}				
		} catch (EmptyNameException e) {
			// get error message
			System.out.print(e.getMessage());
			
		} catch (InvalidEmployeeIDException e) {
			// get error message
			System.out.print(e.getMessage());
		}
		kb.close(); //close scanner object
	}
}
