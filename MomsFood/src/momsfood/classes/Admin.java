package momsfood.classes;
import java.sql.*;
import java.time.LocalDate;

import javafx.collections.ObservableList;

public class Admin extends User {


	public Admin(int i) {
		super(i); //from superclass
	}
	
	//no arg constructor
	public Admin() {}	
	
	/**
	 * Method to display all detail for the admin as a table of comments
	 * @return a table showing all comments detail
	 * @throws SQLException
	 */
	public ObservableList<Comment> displayAllComments() throws SQLException {
		//return array list from query to select all cooks
		ObservableList<Comment> searchComments = new CommentDAO().selectComments("");
		return searchComments;
	}
	
	/**
	 * Method for admin to search for a comment by the commentID
	 * @param commentID Key that identified the comment needed
	 * @return table detail for the comment detail 
	 * @throws SQLException
	 */
	public ObservableList<Comment> searchCommentsByID(int commentID) throws SQLException {
		ObservableList<Comment> searchComments = new CommentDAO().findCommentByID(commentID);
		//return the comments for this customer
		return searchComments;
	}
	
	/**
	 * Method for admin to search for a comment by the customer name
	 * @param cust customer who admin wants to search for
	 * @return table detail for the comment detail 
	 * @throws SQLException
	 */
	public ObservableList<Comment> searchCommentsByCustomer(String cust) throws SQLException {
		ObservableList<Comment> searchComments = new CommentDAO().findCommentByCustomer(cust, cust);
		//return the comments for this customer
		return searchComments;
	}
	
	/**
	 * Method for admin to search for a comment by the date posted
	 * @param localDate date that admin wants to search for
	 * @return table detail for the comment detail 
	 * @throws SQLException
	 */
	public ObservableList<Comment> searchCommentsByDate(LocalDate localDate) throws SQLException {
		ObservableList<Comment> searchComments = new CommentDAO().findCommentsByDate(localDate);
		//return the comments by date
		return searchComments;
	}
	
	/**
	 * Method for admin to search for a comment by the comment note
	 * @param item note of what the admin wants to search comments for
	 * @return table detail for the comment detail 
	 * @throws SQLException
	 */
	public ObservableList<Comment> searchCommentsByNote(String note) throws SQLException {
		ObservableList<Comment> searchComments = new CommentDAO().findCommentsByNote(note);
		//return the comments by meal
		return searchComments;
	}
	
	/**
	 * Method to delete a cook account
	 * @param cook to delete
	 * @return indicator of whether or not cook has been deleted
	 * @throws SQLException
	 */
	public String deleteCookAcct(int cookID) throws SQLException {
		//run query and return confirmation to see if cook has been deleted
		String confirm = new CookDAO().deleteCook(cookID);
		return confirm;
	}
	
	/**
	 * Method to delete a customer account
	 * @param customer to delete
	 * @return indicator of whether or not customer has been deleted
	 * @throws SQLException
	 */
	public String deleteCustomerAcct(int custID) throws SQLException {
		String confirm = new CustomerDAO().deleteCustomer(custID);
		return confirm;
	}
	
	/**
	 * Method to delete a customer comment
	 * @param comment to delete
	 * @return indicator of whether or not customer comment has been deleted
	 * @throws SQLException
	 */
	public boolean deleteUserComment(Comment comment) throws SQLException {
		//int commentID = Integer.parseInt(comment.getCommentID());
		//run query and return confirmation to see if comment has been deleted
		boolean confirm = new CommentDAO().deleteComment(comment.getCommentID());
		return confirm;
	} 
	
	/**
	 * Method to deactivate a cook account
	 * @param cook to deactivate
	 * @return indicator of whether or not cook has been deactivated
	 * @throws SQLException
	 */
	public String deactivateCook(int cookID) throws SQLException {
		//int cookID = Integer.parseInt(cook.getUserID());
		//run query and return confirmation to see if cook has been deleted
		String confirm = new CookDAO().updateAccount(cookID);
		return confirm;
	}
	
	/**
	 * Method to deactivate a customer account
	 * @param customer to deactivate
	 * @return indicator of whether or not customer has been deactivated
	 * @throws SQLException
	 */
	public boolean updateCustomer(int custID, boolean status) throws SQLException {
		//int custID = Integer.parseInt(cust.getUserID());
		//run query and return confirmation to see if customer has been deleted
		return new CustomerDAO().updateAccount(custID, status);
	}
	
}
