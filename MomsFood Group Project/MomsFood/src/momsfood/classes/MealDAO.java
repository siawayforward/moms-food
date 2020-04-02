package momsfood.classes;
import java.io.File;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MealDAO {
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
	
	
	/**
	 * Method to select cookMeals
	 * @param whereStatement to add condition to SQL statement
 	 * @return ResultSet containing all the cooks meals
	 */
	public ResultSet selectCookMeals(String whereStatement) {
		//query to run
		if(whereStatement.trim().length() == 0)
			query = "SELECT * FROM Tbl_Meal";
		else
			query = "SELECT * FROM Tbl_Meal " + whereStatement;
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
			
			}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return meal
		return table;
	}
	
	/**
	 * Method to select meals for a particular cook
	 * @param cookID int cook to get meals for
	 * @return an observable list of all meals for that cook
	 * @throws SQLException
	 * @throws Exception
	 */
	public ObservableList<Meal> getCookMeals(int cookID) throws SQLException, Exception {
		table = selectCookMeals(" WHERE CookID = " + cookID);
		ObservableList<Meal> meals = FXCollections.observableArrayList();
		//get meal attributes
        //Get the number of rows.
		table.last();                 // Move to last row
        int numRows = table.getRow(); // Get row number
        table.first();                // Move to first row
		for(int i = 0; i < numRows; i++) {
			//extract cook's order count from the result set and add to meal object
			Meal meal = new Meal(table.getInt("MealID"),
					table.getString("MealName"),
					table.getDouble("MealPrice"),
					table.getString("MealDescription"),
					table.getBlob("MealPicture"),				
					table.getString("FoodCategory"),
					table.getString("Country"));
			//add to list
			meals.add(meal);
			// Go to the next row in the ResultSet.
			table.next();
		}
		//close connection
		conn.close();
		return meals;
	}
	
	/**
	 * Method for a cook to update meal data
	 * @param updateMeal meal that the cook would like to update
 	 * @return boolean confirming whether or not update happened
	 */
	public boolean updateMeal(Meal updateMeal) {
		boolean result = false; //return value		
		query = //statement for query to run
				  "UPDATE Tbl_Meal"
				  + "  SET MealDescription = '" + updateMeal.getMealDescription() + "' ,"
				  + "	   MealPrice = " + updateMeal.getUnitPrice() + ","
				  + "  	   FoodCategory = '" + updateMeal.getFoodCategory() + "' ,"
				  + " 	   Country = '" + updateMeal.getCountry() + "', "
				 +  "      MealPicture = '" + updateMeal.getPictureName() + "'"
				  + "  WHERE MealID = " + updateMeal.getMealID();
		
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			int update = stmt.executeUpdate(query);
			if(update != 0)
				result = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}  //return value
		return result;
	}	
	
	/**
	 * Method to add a new meal to the database 
	 * @param mealname : name of the particular meal
	 * @param cookID : the ID of the particular cook creating the meal
	 * @param mealDescription: a description of the meal prepared by the cook
	 * @param mealPrice: the price of the meal
	 * @param foodCategory: the category that the meal best fits in
	 * @param country : the country origin of the country 
	 * @param rating : Average rating of the rating
	 * @param picture: a blob/ image of the meal 
	 * @return flag as true or false to say whether SQL statement was successful
	 */
	
	public boolean addMeal(String mealName, int cookID, String mealDescription, double mealPrice, String foodCategory, String country, double rating, String pictureName) {
		//Create boolean flag and set it to false
		boolean flag = false;
		
		try {
	    	//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//Create the SQL statement to be executed
				String sql = "Insert into Tbl_Meal "
						+ " (MealName, CookID, MealDescription, MealPrice, FoodCategory, Country, Rating, MealPicture) "
						+ " VALUES( '" + mealName + "', " + cookID + ", '" + mealDescription + "', " 
						+ mealPrice + ", '" +foodCategory + "', '" + country +"', " + rating +", '" +pictureName + "' )";
				//Create variable and run query to show if execution was successful
		    	   int row = stmt.executeUpdate(sql);
		    	   //If statement is successful or row ==1 make flag = true
		    	   if(row == 1)
		    	   {
		    		   flag = true;
		    	   }		    	
	    	}
		    	 //Catch statement
		    	    catch (SQLException e) {
		    	    	e.printStackTrace();	    	    	
			}
		//Return flag boolean
		return flag;
	}	

	/**
	 * Method to delete meal from the database 
	 * @param mealID : ID of the particular meal
	 * @return flag as true or false to say whether SQL statement was successful
	 */
	
	public boolean deleteMeal(int mealID) {
		//Create boolean flag and set it to false
		boolean flag = false;
		
		try {
	    	//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//Create the SQL statement to be executed
				String sql = "DELETE FROM Tbl_Meal "
						+ " WHERE MealID = " + mealID;   	   		
				//Create variable and run query to show if execution was successful
		    	   int row = stmt.executeUpdate(sql);
		    	 //If statement is successful or row ==1 make flag = true
		    	   if(row ==1)
		    	   {
		    		   flag = true;
		    	   }
	    	}
		    	 //Catch statement
		    	    catch (SQLException e) {
		    	    	e.printStackTrace();	    	    	
			}
		//Return flag boolean
		return flag;
	}
	
	/**
	 * Method to display meal from database 
	 * @param mealID : ID of the particular meal
	 * @return flag as true or false to say whether SQL statement was successful
	 */
	
	public Meal displayMeal(int mealID){
		//Declare an ObservableList named meals
		Meal meal = new Meal();
		try {
	    	//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//Create the SQL statement to be executed
				String sql = "SELECT * "+
						"FROM tbl_meal WHERE mealID = " +mealID ;		    	   		
				//Create variable and run query to show if execution was successful
		    	   ResultSet table = stmt.executeQuery(sql);
		          //Move to the first row
		            table.first();
		          //Create new meals
	            	meal = new Meal(table.getString("MealName"),
							table.getBlob("MealPicture"),
							table.getString("MealDescription"),
							table.getString("FoodCategory"),
							table.getString("Country"),
							table.getDouble("MealPrice"));
		            conn.close();
	    		    	    	
			} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		//Return ObservableList meals
		return meal;
	}	
	
	/**
	 * Method to search a particular meal 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	public ObservableList<Meal> mealSearch(String whereStatement)
	{
		ObservableList<Meal> meals = FXCollections.observableArrayList();
		try {
			String sql ="SELECT m.*, c.FirstName, c.LastName "
					+ " FROM tbl_meal m"
					+ " JOIN tbl_cook c ON c.cookID = m.cookID ";
	    	//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				
				//If onStatement is null
				if(whereStatement != "")
				{
					sql += whereStatement;
				}
				ResultSet table = stmt.executeQuery(sql);
				// Move to last row
	    	    table.last();         
	    	    //Get the number of rows
	            int numRows = table.getRow();
	            //Move back to the first row
	            table.first();
	            
	            //Create for loop to go through every row
	            for(int i=1; i<=numRows; i++) {
	            	//Create new meals
	            	Meal meal = new Meal(table.getInt("MealID"),
	            			table.getString("MealName"),
	            			table.getDouble("MealPrice"),
	            			table.getString("MealDescription"),
	            			table.getBlob("MealPicture"),
							table.getString("FoodCategory"),
							table.getString("Country"),
							table.getDouble("Rating"));
					//add to list
					meals.add(meal);
					// Go to the next row in the ResultSet.
					table.next();
	            }
	            conn.close();;
	    	}				
		    	 //Catch statement
		    	    catch (Exception e) {
		    	    	e.printStackTrace();	    	    	
			}	
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to search a particular mealName 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */

	public ObservableList<Meal> searchByMealName(String mealName) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String where = "WHERE m.mealname LIKE '%"+ mealName + "%'";
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = mealSearch(where);
		//Return ObservableList meals
		return meals;
	}
	/**
	 * Method to search cook Name 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> searchByCookName(String cookName) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String where =" WHERE c.FirstName LIKE '%" + cookName + "%' or c.Lastname LIKE '%"+cookName +"%' ";
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = mealSearch(where);
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to search by country
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> country(String country) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String where = "WHERE m.country LIKE '%"+ country + "%'";
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = mealSearch(where);
		
		//Return ObservableList meals
		return meals;
	}	
	
	/**
	 * Method to search by a foodCategory 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> searchByFoodCategory(String foodCategory) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String where = "WHERE m.foodcategory LIKE '%"+ foodCategory + "%'";
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = mealSearch(where);
		
		//Return ObservableList meals
		return meals;
	}	
	
	/**
	 * Method to sort a meal 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortMeal(String onStatement) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();
		try {
			
			String sql = "SELECT m.mealname , c.cookName, m.foodCategory , m.country, m.rating, m.mealPrice, m.picture "+
					" FROM tbl_meal as m JOIN tbl_cook as c on c.CookID = m.CookID";
			
	    	//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//If onStatement is null
				if(onStatement != "")
				{
				//Create the SQL statement to be executed
				sql  += onStatement;	
				}
				ResultSet table = stmt.executeQuery(sql);
				  // Move to last row
	    	    table.last();
	    	    //Get the number of rows
	            int numRows = table.getRow();
	            //Move back to the first row
	            table.first();
	            
	          //Create for loop to go through every row
	            for(int i=1; i<=numRows; i++) {
	            	//Create new meals
	             	Meal meal = new Meal(table.getInt("MealID"),
	            			table.getString("MealName"),
	            			table.getDouble("MealPrice"),
	            			table.getString("MealDescription"),
	            			table.getBlob("MealPicture"),
							table.getString("FoodCategory"),
							table.getString("Country"));
					//add to list
					meals.add(meal);
					// Go to the next row in the ResultSet.
					table.next();
	            }
	            conn.close();;
	    	}				
		    	 //Catch statement
		    	    catch (Exception e) {
		    	    	e.printStackTrace();    	    	
			}	
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by mealName 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByMealName(String mealName) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " + mealName;
		
		meals = sortMeal(orderBy);	
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by cookName 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByCookName(String cookName) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " +cookName ;
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = sortMeal(orderBy);	
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by foodCategory 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByFoodCategory(String foodCategory) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " +foodCategory;
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = sortMeal(orderBy);	
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by country 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByCountry(String country) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " +country;
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = sortMeal(orderBy);	
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by rating 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByRating(String rating) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
		
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " +rating;
		
		//meals = mealSearch method with the 'where' statement as an argument
		meals = sortMeal(orderBy);	
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by price 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return ObservableList meals
	 */
	
	public ObservableList<Meal> sortByPrice(String price) {
		//Declare an ObservableList named meals
		ObservableList<Meal> meals = FXCollections.observableArrayList();	
			
		//Declare string variable to add to the SQL statement
		String orderBy = "ORDER BY " +price;
			
		//meals = mealSearch method with the 'where' statement as an argument
		meals = sortMeal(orderBy);
		
		//Return ObservableList meals
		return meals;
	}
	
	/**
	 * Method to sort a meal by highest ratings 
	 * @param wherestatement : an addition to the current SQL Statement
	 * @return Meal with data to display on home page
	 */
	
	public Meal selectMealDetail(int mealID) {
		//String SQL query
		query = "SELECT m.MealID, m.Rating, m.MealDescription, m.MealPrice, m.MealName, m.MealPicture, CONCAT(c.FirstName,' ', c.LastName) AS CookName, c.CookRating"
				+ " FROM Tbl_Meal m Inner join tbl_cook c on "
				+ " m.cookID = c.cookid "
				+ " WHERE m.MealID = " + mealID;
		Meal meal = new Meal();	
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
            //Move back to the first row
            table.first();
            meal = new Meal(table.getString("MealName"),
            			table.getString("MealDescription"),
						table.getDouble("MealPrice"),
						table.getBlob("MealPicture"),
						table.getString("CookName"),
						table.getDouble("Rating"),
						table.getDouble("CookRating"));
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return meal
		return meal;
	}
	
	/**
	 * Method to get cookId based on MealID
	 * @param MealID: specific mealID for meal
	 * @return Meal with data to display on home page
	 */
	
	public int GetCookID(int MealID)
	{
		int cookId = 0;
		query = "SELECT CookID FROM tbl_Meal WHERE MealID = " +MealID;
		
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
            //Move back to the first row
            table.first();
            cookId = table.getInt("CookID");
           
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return cookId;
	}
	
	/**
	 * Method to select a specific food
	 * @param limit: The number of meals we would like to return
	 * @return meal object to display on home page
	 */
	public Meal SelectFood(String limit) {
		//String SQL query
		query = "SELECT Rating, MealPrice, MealName, MealPicture, MealId, c.cookid , CONCAT(c.FirstName,' ', c.LastName) AS CookName"
				+ " FROM Tbl_Meal Inner join tbl_cook c on "
				+ " Tbl_Meal.cookID = c.cookid "
				+ " ORDER BY Rating DESC "
				+ " LIMIT " + limit;
		Meal meal = new Meal();	
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
            //Move back to the first row
            table.first();
            meal = new Meal(table.getString("MealName"),
						table.getDouble("MealPrice"),
						table.getBytes("MealPicture"),
						table.getString("CookName"),
						table.getDouble("Rating"),
						table.getInt("MealId"),
						table.getInt("cookid"));											
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return meal
		return meal;
	}
	
	/**
	 * Method to update the meal's average rating in the Cook's table from the Cook's rating table entries
	 * @param mealID to know which meal
	 * @return the number of records updated
	 * @throws SQLException
	 */
	public boolean updateAverageRating(int mealID) throws SQLException {
		//query to run
		query = "UPDATE Tbl_Meal"
					+ " SET MealRating = " 
					+ " SELECT AVG(MealRating) FROM Tbl_MealRating WHERE MealID = " + mealID
					+ " WHERE CookID = " + mealID;
		boolean records = false; //return value
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
				records = true;
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		} 	//return value for number of records updated
		return records;
	}
	
	/**
	 * Get image file for a meal
	 * @param mealID int meal ID for a specific meal
	 * @return imageview object for the image to display for a meal
	 */
	public ImageView getImage(int mealID, double width, double height) {
		String filename = "";
		ImageView image = null; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_READ_ONLY);
			//Query
			query = "SELECT MealPicture from Tbl_Meal "
					+ " WHERE MealID = " + mealID;
			table = stmt.executeQuery(query);
			//get image name
			table.first();
				filename = table.getString("MealPicture");
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	
		//get image file
		File file = new File("src/mealImages/" + filename);
		Image img = new Image(file.toURI().toString(), width, height, false, true, true);
		image = new ImageView(img);
		return image;	
	}
	
	/**
	 * Query to get mealID from meal name for new order items
	 * @param mealName name of mealID to retrieve
	 * @return mealID
	 */
	public int getMealID(String mealName) {
		int mealID = 0; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			query= "SELECT MealID FROM Tbl_Meal "
					+ " WHERE MealName = '" + mealName + "'";
			//execute query
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	        		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
			//get mealID
			table.first();
			mealID = table.getInt("MealID");
			//close the connection
            conn.close();
		}			
		catch (Exception e) {
			e.printStackTrace();
		}	//return value	
		return mealID;
	}
	

}