package momsfood.classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CommentDAO {

	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
	
	/**
	 * Method to return comment data for customers who have reviewed meals
	 * @param whereStatement string provides a filter if needed for the comments needed
	 * @return the list of comments being selected based on user parameters, 
			includes customer first and last name, meal name, comment, and date written
	 * @throws SQLException
	 */
	public ObservableList<Comment> selectComments(String whereStatement) throws SQLException {
		//return variable
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		//query to run
		query = "SELECT co.CommentID, cu.FirstName, cu.LastName, m.MealName, co.Comment, co.CommentDate"
				+ " FROM Tbl_Comment co"
				+ " LEFT JOIN Tbl_Customer cu ON cu.CustomerID = co.CustomerID"
				+ " LEFT JOIN Tbl_Meal m ON m.MealID = co.MealID ";	
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			//run query and get result
			if(whereStatement == null)
				table = stmt.executeQuery(query);
			else
				table = stmt.executeQuery(query + whereStatement);
			//extract values from the result and insert into customer object
            //Get the number of rows.
			table.last();                 // Move to last row
            int numRows = table.getRow(); // Get row number
            table.first();                // Move to first row
			for(int i = 1; i <= numRows; i++) {
				Comment note = new Comment(table.getInt("CommentID"),
						table.getString("FirstName").substring(0,1) + ".",
						table.getString("LastName"),
						table.getString("MealName"),
						table.getString("Comment"),
						table.getDate("CommentDate").toLocalDate());
				//add customer comment arraylist to the super array list
				comments.add(note);
				//go to the next row
				table.next();
			}
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		return comments; //return all search results
	}
	
	/**
	 * Method to get the comments of a customer from a commentID search
	 * @param commentID identifier for comment
	 * @return arraylist of comments: includes customer first and last name, meal name, comment, and date written
	 * @throws SQLException
	 */
	public ObservableList<Comment> findCommentByID(int commentID) throws SQLException {
		//return variable
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		//where statement filter to apply to query
		String where = " WHERE co.CommentID = " + commentID;
		//run query and get result based on filter
		comments = selectComments(where);
		return comments; //return all search results
	}	
	
	/**
	 * Method to get the comments of a customer from a name search
	 * @param firstName string: customer first name
	 * @param lastName string: customer last name
	 * @return arraylist of comments: includes customer first and last name, meal name, comment, and date written
	 * @throws SQLException
	 */
	public ObservableList<Comment> findCommentByCustomer(String firstName, String lastName) throws SQLException {
		//return variable
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		//where statement filter to apply to query
		String where = " WHERE (cu.FirstName LIKE '%" + firstName + "%' OR cu.LastName LIKE '%" + lastName + "%')"
						+ " ORDER BY co.CommentDate";
		//run query and get result based on filter
		comments = selectComments(where);
		return comments; //return all search results
	}
	
	/**
	 * Method to return comments by a specified date
	 * @param localDate date needed
	 * @return array list of comments: includes customer first and last name, meal name, comment, and date written
	 * @throws SQLException
	 */
	public ObservableList<Comment> findCommentsByDate(LocalDate localDate) throws SQLException {
		//return variable
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		//where statement filter to apply to query
		String where = " WHERE co.CommentDate = '" + localDate + "'"
						+ " ORDER BY cu.LastName, cu.FirstName";
		//run query and get result based on filter
		comments = selectComments(where);
		return comments; //return all search results
	}
	
	/**
	 * Method to return comments by a specified meal item
	 * @param commentNote to search by
	 * @return array list of comments: includes customer first and last name, meal name, comment, and date written
	 * @throws SQLException
	 */
	public ObservableList<Comment> findCommentsByNote(String commentNote) throws SQLException {
		//return variable
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		//where statement filter to apply to query
		String where = " WHERE co.Comment LIKE '%" + commentNote + "%'"
						+ " ORDER BY co.CommentDate";
		//run query and get result based on filter
		comments = selectComments(where);
		return comments; //return all search results
	}
	
	/**
	 * Get the comments for a particular meal to see the comment history
	 * @param mealID meal to get comments for
	 * @return array of comments such that comment (in order) = first name of customer, last name of customer, comment note,  date comment was posted
	 */
	public ArrayList<Comment> getMealComments(int mealID) {
		query = "SELECT cu.FirstName, cu.LastName, co.Comment, co.CommentDate"
				+ " FROM Tbl_Comment co"
				+ " LEFT JOIN Tbl_Customer cu ON cu.CustomerID = co.CustomerID"
				+ " LEFT JOIN Tbl_Meal m ON m.MealID = co.MealID "
				+ " WHERE m.MealID = " + mealID;
		ArrayList<Comment> comments = new ArrayList<Comment>(); //return variable
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);
			//extract values from the result and insert into customer object
	        //Get the number of rows.
			table.last();                 // Move to last row
	        int numRows = table.getRow(); // Get row number
	        table.first();                // Move to first row
	        //get the comments
			for(int i = 1; i <= numRows; i++) {
				comments.add(new Comment(table.getString("FirstName"),
						table.getString("LastName"),
						table.getString("Comment"),
						table.getDate("CommentDate").toLocalDate()));
				//add customer comment arraylist to the super array list
				//go to the next row
				table.next();
			}
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		return comments; //return all search results
	}
	
	/**
	 * Method to delete a comment
	 * @param CommentID int: identifier for comment to delete
	 * @return confirmation string: to indicate whether or not action has been completed
	 * @throws SQLException
	 */
	public boolean deleteComment(int commentID) throws SQLException {
		//return value
		boolean confirmation = false;
		query = "DELETE FROM Tbl_Comment WHERE CommentID = " + commentID;
		//run query and check if deletion has happened
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//run query and get result
			int count = stmt.executeUpdate(query);
			//close connection
			conn.close();
			if(count != 0) //check if update happened
				confirmation = true;
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		return confirmation;	
	}
	
	/**
	 * Insertion of a new comment into the database
	 * @param comment object including the meal, customer, date, and note
	 * @return confirmation boolean value of whether or not the update happened.
	 */
	public boolean addComment(Comment comment) {
		boolean confirm = false; //return variable
		query = "INSERT INTO Tbl_Comment (Comment, CustomerID, MealID, CommentDate) "
				+ " VALUES ('" + comment.getComment() + "', "
				+ comment.getCustID() + ", "
				+ comment.getMealID() + ", "
				+ comment.getDate() + ")";
		//run query and check if deletion has happened
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			//run query and get result
			int count = stmt.executeUpdate(query);
			//close connection
			conn.close();
			if(count != 0) //check if update happened
				confirm = true;
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		return confirm;
	}
}
