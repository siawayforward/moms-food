package momsfood.classes;
import java.time.LocalDate;
import java.sql.Date;

public class Comment {

	// Attributes
	private int commentID;
	private String comment;
	private String custFirstName;
	private String custLastName;
	private int mealID;
	private int custID;
	


	private String mealName;
	private LocalDate date;
	
	// no-arg constructor
	public Comment() {}
	
	/**
	 * Insertion constructor for a new comment
	 * @param mealId of the meal the comment is under
	 * @param custId of the customer posting the comment
	 * @param note comment being received fromt he customer
	 * @param date the comment was posted on
	 */
	public Comment(int mealID, int custID, String note, Date date) {
		this.mealID = mealID;
		this.custID = custID;
		this.comment = note;
		this.date = date.toLocalDate();
	}
	
	
	/**
	 * Constructor to get and display comment data for admin functions
	 * @param custFirstName string: first name of the customer
	 * @param custLastName string: last name of the customer
	 * @param mealName string: name of meal
	 * @param comment string: comment note
	 * @param date date: date the comment was posted
	 */
	public Comment(int ID, String custFName, String custLName, String mealName, String comment, LocalDate date) {
		//constructor to get and display comment data for admin
		this.commentID = ID;
		this.comment = comment;
		this.custFirstName = custFName;
		this.custLastName = custLName;
		this.mealName = mealName;
		this.date = date;
	}
	
	/**
	 * Constructor to get and display comments on the meal page
	 * @param note string: comment left by the user
	 * @param date string: date the comment was posted
	 * @param custFName string: first name of the customer
	 * @param custLName string: last name of customer
	 */
	public Comment(String custFName, String custLName, String note, LocalDate date) {
		//constructor to get and display comment data on the meal page
		this.comment = note;
		this.custFirstName = custFName;
		this.custLastName = custLName;
		this.date = date;
	}

	public int getMealID() {
		return mealID;
	}

	public void setMealID(int mealID) {
		this.mealID = mealID;
	}
	
	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}
	
	//getters and setters
	public int getCommentID() {
		return commentID;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
		
	/**
	 * To string method to return the string representation of comments to display on meal screen
	 */
	@Override
	public String toString() {
		String note = "";
		note = this.getCustFirstName() + " " + this.getCustLastName() + "\t \t \t" + this.getDate() 
				+ "\n" + this.getComment();
		return note;		
	}
}
