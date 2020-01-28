package images;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import momsfood.classes.MomsFoodDBConstants;

public class DisplayImage {
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = ""; 


	
	
	public ImageView getImage(int mealID) {
		String filename = "";
		ImageView image = null; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//Query
			query = "SELECT MealPicture from Tbl_Meal "
					+ " WHERE MealID = " + mealID;
			System.out.println(query);
			table = stmt.executeQuery(query);
			//get image name
			table.first();
				filename = table.getString("MealPicture");
				System.out.println(filename);
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("src/mealImages/" + filename);
		//get image file
		File file = new File("src/mealImages/" + filename);
		Image img = new Image(file.toURI().toString());
		image = new ImageView(img);
		return image;	
	}
	
	
}
