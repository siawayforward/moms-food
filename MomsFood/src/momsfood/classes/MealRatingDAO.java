package momsfood.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MealRatingDAO {
	
	// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet table = null; 
		//query to run
		String query = "";
		
		/**
		 * Method to add a new meal rating
		 * @param MealID: The unique ID for a meal
		 * @param custID Unique ID for the customer
		 * @param MealRating :The current meal Rating
		 * @return boolean confirm
		 */
		public boolean addMealRating(int MealID, int custID, double MealRating) {
			boolean confirm = false; //return value
			query = " INSERT INTO tbl_mealrating (mealRating, MealID, CustomerID) "
					+ " VALUES (" + MealRating + ", " + MealID + ", " + custID + ")";
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				
				// Create and execute an SQL statement that returns some data.
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	            		ResultSet.CONCUR_READ_ONLY);
				int count = 0;
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
}
