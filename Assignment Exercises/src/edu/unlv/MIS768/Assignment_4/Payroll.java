package edu.unlv.MIS768.Assignment_4;
/**
   The Payroll class stores data about an employee's pay
   for the Payroll Class programming challenge.
   Date: March 29, 2019
   Creator: Dr. Hu
   Modified by: Sia Mbatia
*/

public class Payroll {
   private String name;          // Employee name
   private int idNumber;         // ID number
   private double payRate;       // Hourly pay rate
   private double hoursWorked;   // Number of hours worked

   /**
      The constructor initializes an object with the
      employee's name and ID number.
      @param n The employee's name.
      @param i The employee's ID number.
 * @throws InvalidEmployeeIDException if an ID of 0 or less is entered
 * @throws EmptyNameException if name is not provided
   */

   public Payroll(String n, int i) throws EmptyNameException , InvalidEmployeeIDException  {
	   if(n.trim().isEmpty())
		   throw new EmptyNameException();
	   if(i <= 0)
		   throw new InvalidEmployeeIDException(i);
      name = n;
      idNumber = i;
   }

   /**
      The setName sets the employee's name.
      @param n The employee's name.
 * @throws EmptyNameException if name is not provided
   */

   public void setName(String n) throws EmptyNameException   {
	   if(n.trim().isEmpty())
		   throw new EmptyNameException();
      name = n;
   }

   /**
      The setIdNumber sets the employee's ID number.
      @param i The employee's ID number.
 * @throws InvalidEmployeeIDException if an ID of 0 or less is entered
   */
   
   public void setIdNumber(int i) throws InvalidEmployeeIDException   {
	   if(i <= 0)
		   throw new InvalidEmployeeIDException(i);
      idNumber = i;
   }

   /**
      The setPayRate sets the employee's pay rate.
      @param p The employee's pay rate.
 * @throws InvalidPayRateException if dollar amount less than minimum wage of $10.25/hour is entered
   */
   
   public void setPayRate(double p) throws InvalidPayRateException   {
	   if(p < 10.25)
		   throw new InvalidPayRateException(p);
      payRate = p;
   }

   /**
      The setHoursWorked sets the number of hours worked.
      @param h The number of hours worked.
 * @throws InvalidHoursWorkedException if hours worked are not between 0 and 84 for the week
   */

   public void setHoursWorked(double h) throws InvalidHoursWorkedException   {
	   if(h < 0 || h > 84)
		   throw new InvalidHoursWorkedException(h);
      hoursWorked = h;
   }

   /**
      The getName returns the employee's name.
      @return The employee's name.
   */

   public String getName()   {
      return name;
   }

   /**
      The getIdNumber returns the employee's ID number.
      @return The employee's ID number.
   */
   
   public int getIdNumber()   {
      return idNumber;
   }

   /**
      The getPayRate returns the employee's pay rate.
      @return The employee's pay rate.
   */

   public double getPayRate()   {
      return payRate;
   }

   /**
      The getHoursWorked returns the hours worked by the
      employee.
      @return The hours worked.
   */


   public double getHoursWorked()   {
      return hoursWorked;
   }

   /**
      The getGrossPay returns the employee's gross pay.
      @return The employee's gross pay.
   */

   public double getGrossPay()   {
      return hoursWorked * payRate;
   }
}
