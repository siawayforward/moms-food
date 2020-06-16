package momsfood.classes;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CookDAO {
	
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
	
	/**
	 * Method to insert a new cook into the database
	 * @param Cook object which contains all cook attributes
	 * @return a confirmation indicating whether or not the cook has been added to the system
	 * @throws SQLException
	 */
	public String addNewCook(Cook aCook) throws SQLException {
		String checker = "Failed to add " + aCook.firstName + " " + aCook.lastName + " as a cook. Please try again later."; //return variable
		//query to run
		query = "INSERT INTO Tbl_Cook (FirstName, LastName, EmailAddress, PhysicalAddress, PhoneNumber, CountryName,"
				+ " CookRating, Username, Password, IsApproved, IsActive, Availability)"
				+ " VALUES ('"+ aCook.getFirstName() + "', '" 
				+ aCook.getLastName() + "' ,'" 
				+ aCook.getEmailAddress() + "', '" 
				+ aCook.getEmailAddress() + "', '" 
				+ aCook.getPhysicalAddress() + "', '" 
				+ aCook.getPhoneNumber() + "', '" 
				+ aCook.getCountryDetail() + "', '" 
				+ aCook.getCookRating() + "', '"
				+ aCook.getUsername() + "', '"
				+ aCook.getPassword() + "', " 
				+ aCook.getIsApproved() + ", "
				+ aCook.getIsActive() + ", '" 
				+ aCook.getAvailability() + "')";
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			//query connector to run insert query and check if cook has been added
			int count = stmt.executeUpdate(query);	
			//check if user was added (count != 0)
			if(count == 1)
				checker = aCook.getFirstName() + " " + aCook.getLastName() + " has been added as a cook!";
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	//return confirmation
		return checker;
	}
	
	/**
	 * Method to update a cook profile for items that are allowable. Does not include username, activity or approval status
	 * @param aCook cook to update profile of
	 * @return a boolean indicator that is true if the update was successful, and false otherwise
	 */
	public boolean updateCookProfile(Cook aCook) {
		boolean update = false;
		//query to run
		query = "UPDATE Tbl_Cook SET"
				+ " FirstName = '"+ aCook.getFirstName() + "', "
				+ " LastName = '"+ aCook.getLastName() + "', "
				+ " EmailAddress = '"+ aCook.getEmailAddress() + "', "
				+ " PhysicalAddress = '" + aCook.getPhysicalAddress() + "', "
				+ " PhoneNumber = '" + aCook.getPhoneNumber() + "', "
				+ " CountryName = '" + aCook.getCountryDetail() + "', "
				+ " Password = '" + aCook.getPassword() + "', " 
				+ " Availability = '" + aCook.getAvailability() + "'"
				+ " WHERE CookID = " + aCook.getUserID(); 

		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			//query connector to run insert query and check if cook has been added
			int count = stmt.executeUpdate(query);	
			//check if user was added (count != 0)
			if(count == 1)
				update = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	//return confirmation
		return update;
	}
	
	/**
	 * Method to update the activity or approval status of a cook
	 * @param status boolean - indicates whether or not the status has changed
	 * @param type - string indicates whether we are updating activity or approval status
	 * @param CookID int -  indicates the cook
	 * @return int the updated value for status (1 if yes, 0 if no indicating the number of records updated)
	 * @throws SQLException
	 */
	public boolean updateCookStatus(boolean status, String type, int CookID) throws SQLException {
		boolean confirmation = false; //return value
		//set query to run based on change needed
		if(status == true && type == "Approval") {
			query = "UPDATE Tbl_Cook SET IsApproved = 1 WHERE CookID = " + CookID;
		}
		else if(status ==  true && type == "Activity") {
			query = "UPDATE Tbl_Cook SET IsActive = 1 WHERE CookID = " + CookID;
		}
		else {
			query = "UPDATE Tbl_Cook SET IsActive = 0, IsApproved = 0 WHERE CookID = " + CookID;
		}
		//connect and run query
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			//query connector to run insert query and check if cook has been added
			int count = stmt.executeUpdate(query);		
			//extract the updated value to verify
			if(count != 0)
				confirmation = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	//return confirmation
		return confirmation;
	}
	
	/**
	 * Method to get the cook name to display on customer meal request combobox
	 * @return observable list of cooks first and last names
	 * @throws SQLException
	 */
	public ObservableList<Cook> getAllCooks() throws SQLException {
		//query to run
		query = "SELECT CookID, CONCAT(FirstName,' ',LastName) as CookName FROM Tbl_Cook "
				+ " ORDER BY LastName";
		ObservableList<Cook> cooks = FXCollections.observableArrayList();
		//connect and run query
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);	
			while(table.next())
				cooks.add(new Cook(table.getInt("CookID"), 
						table.getString("CookName")));
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}		
		return cooks; //return all search results
	} 
	
	
	/**
	 * Method to select a specific cook's detail
	 * @param ID cookID to search by
	 * @return the cook object with cook information
	 * @throws SQLException
	 */
	public Cook selectCookProfile(int ID) throws SQLException {
		//where statement for filters
		query = "SELECT * FROM Tbl_Cook WHERE CookID = " + ID;
		Cook selectedCook = new Cook();
		//connect and run query
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);			
			table = stmt.executeQuery(query);
			while(table.next()) {
				selectedCook = new Cook(table.getString("FirstName"),
						table.getString("LastName"),
						table.getString("EmailAddress"),
						table.getString("PhoneNumber"),
						table.getString("PhysicalAddress"),
						table.getString("Password"),
        				table.getString("CountryName"),
        				table.getString("Availability"));
			}
		        //close connection
		        conn.close();
		}	catch (Exception e) {
					e.getMessage();
		}
        return selectedCook;
	}
	
	/**
	 * Method to deactivate a cook account
	 * @param CookID account to deactivate
	 * @return confirmation string: to indicate whether or not action has been completed
	 * @throws SQLException
	 */
	public String updateAccount(int CookID) throws SQLException {
		//set query to run 
		query = "UPDATE Tbl_Cook SET IsApproved = 0, IsActive = 0 "
				+ " WHERE CookID = " + CookID;
		String confirm = "The account with CookID " + CookID + " could not be deactivated";
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//extract the count of updated values to verify
			int count = stmt.executeUpdate(query);
			if(count != 0)
				confirm = "The account with CookID " + CookID + " has been deactivated.";
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	//return value
		return confirm;
	}
	
	/**
	 * Method to delete a cook account
	 * @param CookID int: identifier for cook to delete
	 * @return confirmation string: to indicate whether or not action has been completed
	 * @throws SQLException
	 */
	public String deleteCook(int CookID) throws SQLException {
		//return value
		String confirmation = "The account for CookID " + CookID + " could not be deleted. Please try deactivating the account.";
		query = "DELETE FROM Tbl_Cook WHERE CookID = " + CookID;
		//run query and check if deletion has happened	
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//extract the count of updated values to verify
			int count = stmt.executeUpdate(query);
			if(count != 0)
				confirmation = "The account for CookID " + CookID + " has been deleted.";
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	//return value
		return confirmation;	
	}
	
	/**
	 * Method to get the cook's average rating
	 * @param CookID to know which cook
	 * @return the average cook rating
	 * @throws SQLException
	 */
	public double getAverageRating(int CookID) throws SQLException {
		//query to run
		query = "SELECT CookRating FROM Tbl_Cook WHERE CookID = " + CookID;
		double avg = 0; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery(query);
			//extract cook's rating from the result set		
			while(table.next()) 
				avg = table.getDouble("CookRating");
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		} //return value
		return avg;
	}
	
	
	/**
	 * Method to update the cook's average rating in the Cook's table from the Cook's rating table entries
	 * @param CookID to know which cook
	 * @return the number of records updated
	 * @throws SQLException
	 */
	public boolean updateAverageRating(int CookID) throws SQLException {
		//query to run
		query = "UPDATE Tbl_Cook"
					+ " SET CookRating = " 
					+ " (SELECT AVG(CookRating) FROM Tbl_CookRating WHERE CookID = " + CookID + ") "
					+ " WHERE CookID = " + CookID;
		boolean records = false; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			int count = 0;
			count = stmt.executeUpdate(query);
			if(count != 0)
				records = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		} 	//return value for number of records updated
		return records;
	}
}


