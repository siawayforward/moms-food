package images;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import momsfood.classes.MomsFoodDBConstants;



public class SetCountryItems {	

	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = ""; 
	
	public SetCountryItems() {}
	

	
	public void getImage()  {

		int count = 1;
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			String drop = " DROP TABLE IF EXISTS Tbl_Country; ";
			System.out.println(drop);
			stmt.executeUpdate(drop);
			//Create table
			String create = "CREATE TABLE `momsfood`.`Tbl_Country`" + 
					"  (`CountryID` INT NOT NULL auto_increment," + 
					"  `CountryName` VARCHAR(100) NULL, " + 
					"  `FlagPicture` BLOB NULL, " + 
					"  PRIMARY KEY (`CountryID`));";			
			System.out.println(create);				
			stmt.executeUpdate(create);
			//get list
			ArrayList<String> names = getFlagNames();			
			ArrayList<String> country = getCountryNames();
			for(int i = 0; i < names.size(); i++) {
				query = "INSERT INTO Tbl_Country "
						+ "VALUES (" + count +  ", '" + country.get(i) + "', (LOAD_FILE('Y:/Spring 2019/MIS 768/Group Project/Project Files/Database/flags/" +  names.get(i) + "')));";
				System.out.println(query);
				count+= stmt.executeUpdate(query);
				System.out.println(count-1 + "updated");
			}
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return continent
			
	}
	
	public ArrayList<String> getFlagNames() {
		ArrayList<String> names = new ArrayList<String>();
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery("SELECT FlagName from Tbl_Coun");
			while(table.next()) 
				names.add(table.getString("FlagName"));
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		//return 
		return names;
	}
	
	public ArrayList<String> getCountryNames() {
		ArrayList<String> names = new ArrayList<String>();
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery("SELECT CountryName from Tbl_Coun");
			while(table.next()) 
				names.add(table.getString("CountryName"));
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		//return 
		return names;
	}
	
	/*
	UPDATE Tbl_Country 
	SET FlagPicture =
	(LOAD_FILE('Y:/Spring 2019/MIS 768/Group Project/Project Files/Database/flags/AF-32.png'))
	WHERE CountryID = 1;
	*/
}
