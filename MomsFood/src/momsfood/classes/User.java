package momsfood.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
	This class stores user's userID, first name, last name, email address, phone number, 
	username, and password. 
*/

public class User {
	public int userID;
	public String firstName;
	public String lastName;
	public String username;
	public String password;
	public int isActive;
	
	
	/**
	 * No-arg constructor
	 */
	
	public User() {	}
	
	/**
	 * constructor for user id 
	 */	
	public User(int ID) {
		this.userID = ID;
	}
	
	/**
	 *  Constructor to set user values
	 * @param uId user ID
	 * @param fName first name of user
	 * @param lName last name of user
	 * @param uName username
	 * @param pw password
	 */
	public User(int uId, String fName, String lName, String uName, String pw){
		this.userID = uId;
		this.firstName = fName;
		this.lastName = lName;
		this.username = uName;
		this.password = pw;
	}	
	
	/**
	 * All the getters and setters
	 */
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//Initialize result set
	ResultSet row = null;
	
	
	/**
	 * Method to confirm user exist and allow login
	 * @param type Choose either cook or customer
	 * @param table : Sam as above, what table in database
	 * @return boolean check
	 */
		public Boolean loginAccount(String type, String table) {		
			//Initialize variables
		Connection conn = null;
		boolean check = false;
		
		try {
	    	// Create a connection to the database.
		    conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
		       
		    // Create a Statement object.
		    Statement stmt = conn.createStatement();   
		    //Initialize sql Statement that will retrieve info from the database
		    String sqlStatement =
		 	          "SELECT"+ type + " firstName, lastName, username, password FROM "+ table +
		 	        		  " WHERE username = '" + this.getUsername() + "' AND password = '" + this.getPassword()+"'";
		    //Execute the query statement and store in Result Set row
		     row = stmt.executeQuery(sqlStatement);
		    //If result set is not null, make boolean check true
		    if(row != null)
		    {
		    	check = true;	  
		    }	   
		} //Catch Exception error
		catch(Exception ex) {
	          System.out.println("ERROR: " + ex.getMessage());
		}//return boolean value		
		return check;		
		}
		
		/**
		 * Method to allow customer login
		 * @return boolean Check
		 * @throws Exception
		 */		 
		public boolean customerLoginQuery() throws Exception
		{		
			//Initialize variables
			boolean check = false;
			String type = "customerID";
			String table = "tbl_customer";	
			//Call method login and if true store the info			
			if(loginAccount(type, table) == true)
			{
				row.getInt(1);
				row.getString(2);
				row.getString(3);
			}; //return boolean check
			return check;		
		}
		
		/**
		 * Method to allow cook login
		 * @return boolean Check
		 * @throws Exception
		 */
		public boolean cookLoginQuery() throws Exception
		{	//Initialize variables	
			boolean check = false;
			 String type = "cookID";
			 String table = "tbl_cook";		
			//Call method login and if true store the info		
			 if(loginAccount(type, table) == true)
				{
					row.getInt(1);
					row.getString(2);
					row.getString(3);							
				};//return boolean check
			return check;		
		}
}
		
		
