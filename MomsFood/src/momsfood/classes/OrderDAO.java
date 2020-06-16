package momsfood.classes;
import java.sql.*;

public class OrderDAO {
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
	
	/**
	 * Method to return the number of current orders a cook has
	 * @param cookID int: to identify the cook to find orders for
	 * @return number of orders for the cook 
	 * @throws SQLException
	 */
		public int getCookOrders(int cookID) throws SQLException {
			int count = 0; //return value
			query = "SELECT COUNT(ol.OrderLineID) as Count "
					+ " FROM Tbl_OrderLine ol"
					+ " JOIN Tbl_Order o ON ol.OrderID = o.OrderID"
					+ " JOIN Tbl_Meal m ON m.MealID = ol.MealID"
					+ " WHERE o.IsConfirmed = 1 "
					+ " AND ol.MealID IN ("
					+ " SELECT mealID from Tbl_Meal where CookID = " + cookID + ")"; //query to run
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				
				// Create and execute an SQL statement that returns some data.
				stmt = conn.createStatement();
				table = stmt.executeQuery(query);
				//extract cook's order count from the result set		
				while(table.next()) 
					count = table.getInt("Count");
				//close connection
				conn.close();
			}	// Handle any errors that may have occurred.
			catch (Exception e) {
				e.printStackTrace();
			}	//return value
			return count;
		}
		
		/**
		 * Method to return the number of current special requests by customers that a cook has
		 * @param cookID int: to identify the cook to find orders for
		 * @return number of orders for the cook 
		 * @throws SQLException
		 */
		public int getSpecialRequests(int cookID) {
			int count = 0; //return value
			query = "SELECT COUNT(ol.OrderLineID) as Count "
					+ " FROM Tbl_OrderLine ol"
					+ " JOIN Tbl_Order o ON ol.OrderID = o.OrderID"
					+ " JOIN Tbl_Meal m ON m.MealID = ol.MealID"
					+ " WHERE o.IsConfirmed = 1 "
					+ " AND m.FoodCategory = 'Special Customer Request'"
					+ " AND ol.MealID IN ("
					+ " SELECT mealID from Tbl_Meal where CookID = " + cookID + ")"; //query to run
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				
				// Create and execute an SQL statement that returns some data.
				stmt = conn.createStatement();
				table = stmt.executeQuery(query);
				//extract cook's order count from the result set		
				while(table.next()) 
					count = table.getInt("Count");
				//close connection
				conn.close();
			}	// Handle any errors that may have occurred.
			catch (Exception e) {
				e.printStackTrace();
			}	//return value
			return count;
		}
		
		/**
		 * Method to insert a new order into the database 
		 * @param anOrder object containing order details to insert
		 * @param custID customer ID of order requestor
		 * @return generated orderID if inserted OR 0 IF insert didnt work
		 */
		public int insertOrder(Order anOrder, int custID) {
			int key = 0; //return value
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				query= "INSERT INTO " + MomsFoodDBConstants .ORDER_TABLE_NAME  
						+ "(CustomerID, OrderDate, DeliveryDate, IsConfirmed, IsCancelled, IsDelivered) "
						+ " VALUES ("+
						custID +", '"+
						anOrder.getOrderDate()+"', '" +
						anOrder.getDeliveryDate()+"', " +
						anOrder.getIsConfirmed()+", " +
						anOrder.getIsCancelled()+", " +
						anOrder.getIsDelivered() +")";
				//create statement and run query
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	            		ResultSet.CONCUR_READ_ONLY);
				key = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);	
				table = stmt.getGeneratedKeys();
				table.first();
				if(key != 0)
					key = table.getInt(1);
				//close connection
		        conn.close();		      
			}			
			catch (Exception e) {
				e.printStackTrace();
			}	//return value			
			  return key;
		}
		
		/**
		 * Method to insert all orderlines for a new order
		 * @param orderID ID of order
		 * @param mealID ID of meal for that orderline
		 * @param quantity of meal item requested
		 * @return confirmation of whether or not item has been inserted
		 */
		public boolean insertOrderLine(int orderID, int mealID, int quantity ) {
			boolean flag=false; //return value		
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				query= "INSERT INTO " + MomsFoodDBConstants .ORDERLINE_TABLE_NAME+ 
						" (OrderID, MealID, ItemQuantity)" + 
						" VALUES ("+
						orderID +", "+
						mealID+", "+
						quantity+")";
				stmt = conn.createStatement();
				int rows = 0;
				rows = stmt.executeUpdate(query);				
				if (rows != 0)
					flag=true;			
				//close the connection
	            conn.close();
			}			
			catch (Exception e) {
				e.printStackTrace();
			}	//return value	
			return flag;
		}
		
		/**
		 * Get the number if any of previous orders that a customer has had
		 * @param custID ID of the customer
		 * @return boolean confirmation of whether or not a customer has had this meal before
		 */
		public boolean checkCustomerHistory(int custID, int mealID) {
			boolean flag = false;		
			try {
				// Establish the connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				query= "SELECT COUNT(o.OrderID) as 'Count'"
						+ " FROM Tbl_Order o"
						+ " JOIN Tbl_OrderLine ol ON ol.OrderID = o.OrderID "
						+ " WHERE ol.MealID = " + mealID 
						+ " AND o.CustomerID = " + custID;
				int count  = 0;
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	            		ResultSet.CONCUR_READ_ONLY);
				table = stmt.executeQuery(query);
				table.next(); //move to first row
				count = table.getInt("Count");
				if (count != 0)
					flag = true;
	            conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	//return value		
			return flag;
		}
}
