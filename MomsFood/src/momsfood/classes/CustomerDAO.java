package momsfood.classes;

import java.sql.*;

public class CustomerDAO {
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
			
	/**
	 * Method to get customers from a name search
	 * @param firstName string: customer first name
	 * @param lastName string: customer last name
	 * @return arraylist of customers: includes first and last name, email address, physical address, and phone number
	 * @throws SQLException
	 */
	public ResultSet searchCustomers(String whereStatement) throws SQLException {
		//query to run
		query = "SELECT * FROM Tbl_Customer";		
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			if(whereStatement.trim().length() == 0)
				table = stmt.executeQuery(query);
			else
				table = stmt.executeQuery(query + whereStatement);
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}			
		return table; //return all search results
	}
	
	/**
	 * Method to search customer table by a specific ID for the profile data
	 * @param ID of the customer
	 * @return table with customers with names that match the search
	 * @throws SQLException
	 */
	public Customer getCustomerProfile (int ID) throws SQLException {
		//where condition for filter
		String where =   " WHERE CustomerID = " + ID ;
		//get query results
		ResultSet customer = searchCustomers(where);
		Customer cust = null;
		if(customer.next()) {
			cust = new Customer(table.getString("FirstName"),
					table.getString("LastName"),
					table.getString("EmailAddress"),
					table.getString("PhoneNumber"),
					table.getString("PhysicalAddress"),
					table.getString("Password"));
		}
		return cust;
	}
	
	/**
	 * Method to update customer table by a specific ID for the profile data
	 * @param customer object with values to update
	 * @return confirmation of whether the updates happened
	 * @throws SQLException
	 */
	public boolean updateCustomerProfile(Customer cust) throws SQLException {
		//where condition for filter
		query = "UPDATE Tbl_Customer SET "
				+ " FirstName = '" + cust.getFirstName() + "', "
				+ " LastName = '" + cust.getLastName() + "', "
				+ " EmailAddress = '" + cust.getEmailAddress() + "', "		
				+ " PhoneNumber = '" + cust.getPhoneNumber() + "', "
				+ " PhysicalAddress = '" + cust.getPhysicalAddress() + "', "
				+ " Password = '" + cust.getPassword() + "'"
				+ " WHERE CustomerID = " + cust.getUserID();
		boolean confirm = false; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			int count  = 0;
			count = stmt.executeUpdate(query);
			if(count != 0)
				confirm = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	
		return confirm;
	}
	
	/**
	 * Method to deactivate a customer account
	 * @param CustID account to deactivate
	 * @return confirmation string: to indicate whether or not action has been completed
	 * @throws SQLException
	 */
	public boolean updateAccount(int CustID, boolean status) throws SQLException {
		//set query to run 
		if(status)
			query = "UPDATE Tbl_Customer SET IsActive = 1 "
				+ " WHERE CustomerID = " + CustID;
		else
			query = "UPDATE Tbl_Customer SET IsActive = 0 "
					+ " WHERE CustomerID = " + CustID;
		boolean confirm = false;
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//get number of records updated
			int result = 0;
			result = stmt.executeUpdate(query);	
			if(result != 0)
				confirm = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return value
		return confirm;
	}
	
	/**
	 * Method to delete a customer account
	 * @param CustID int: identifier for customer to delete
	 * @return confirmation string: to indicate whether or not action has been completed
	 * @throws SQLException
	 */
	public String deleteCustomer(int CustID) throws SQLException {
		//return value
		String confirmation = "The account for CustomerID " + CustID + " could not be deleted. Please try deactivating the account for now.";
		query = "DELETE FROM Tbl_Customer WHERE CustomerID = " + CustID; 	//query to run
		//run queries and get count to check if deletion happened
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//get number of records updated
			int result = stmt.executeUpdate(query);	
			if(result != 0)
				confirmation = "The account for CustomerID " + CustID + " has been deleted.";
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return value
		return confirmation;	
	}
}
