package momsfood.classes;
import java.sql.SQLException;
import java.util.*;


public class Cook extends User{
	//attributes
	private String countryDetail;
	private String physicalAddress;
	private String phoneNumber;
	private String emailAddress;
	private double cookRating;
	private int isApproved = 0;
	private int isActive = 0;
	private String availability = " ";
	private String IDName = "";
	
	//Constructors
	//No arg constructor
	public Cook() {}
	
	/**
	 * Constructor with just cookID
	 * @param cookID int
	 */
	public Cook(int cookID) {
		super(cookID);
	}
	
	public Cook(int cookID, String IDName) {
		this.userID = cookID;
		this.IDName = IDName;
	}

	//
	/**
	 * Insertion constructor (adding a new cook to the database)
	 * @param fName string: first name of the cook
	 * @param lName string: last name of the cook
	 * @param uName string: username of the cook's account
	 * @param pw string: password of the cook's account
	 * @param countryData Country: country information of the cook
	 * @param address string: physical address of the cook
	 * @param number string: phone number of the cook
	 * @param email string: email address of the cook
	 * @param rating double: average rating of the cook
	 * @param activity int: whether or not the cook is activated/inactive for a period
	 * @param approval int: whether or not the cook is approved to work for moms food
	 * @param workDays arrayList: days that the cooks is available to work weekly
	 * @throws SQLException if insertion has issues
	 */
	public Cook(String fName, String lName, String uName, String pw, String countryData, String address, String number, 
			String email, double rating, int activity, int approval, String workDays) throws SQLException {		
		//get all cook attributes
		//super(fName, lName, uName, pw, activity); //from superclass
		this.countryDetail = countryData;
		this.physicalAddress = address;
		this.phoneNumber = number;
		this.emailAddress = email;
		this.cookRating = rating;
		this.isApproved = approval;
		this.availability = workDays;
	}
	
	/**
	 * Retrieval constructor to select cook(s) from the database to display on table views
	 * @param fName string: first name of the cook
	 * @param lName string: last name of the cook
	 * @param countryData Country: country information of the cook
	 * @param address string: physical address of the cook
	 * @param number string: phone number of the cook
	 * @param email string: email address of the cook
	 * @param rating double: average rating of the cook
	 * @param activity int: whether or not the cook is activated/inactive for a period
	 * @param approval int: whether or not the cook is approved to work for moms food
	 * @param workDays String: days that the cooks is available to work weekly
	 * @throws SQLException
	 */
	public Cook(String IDName, String countryData, String workDays, String email, String address, String number, 
			 double rating, int approval, int activity) throws SQLException {		
		//get all cook attributes
		this.IDName = IDName;
		this.countryDetail = countryData;
		this.availability = workDays;
		this.countryDetail = countryData;
		this.physicalAddress = address;
		this.phoneNumber = number;
		this.emailAddress = email;
		this.cookRating = rating;
		this.isActive = activity;
		this.isApproved = approval;
	
	}

	public Cook(String fName, String lName, String email, String number, String address, String pw,
			String country, String workDays) {
		this.password = pw;
		this.firstName = fName;
		this.lastName = lName;
		this.countryDetail = country;
		this.physicalAddress = address;
		this.phoneNumber = number;
		this.emailAddress = email;
		this.availability = workDays;
	}	

	public Cook(String fName, String lName, String email, String number, String address,String username, String pw,
			String country, String workDays) {
		this.username = username;
		this.password = pw;
		this.firstName = fName;
		this.lastName = lName;
		this.countryDetail = country;
		this.physicalAddress = address;
		this.phoneNumber = number;
		this.emailAddress = email;
		this.availability = workDays;
	}

	//getters and setters
	public String getCountryDetail() {
		return countryDetail;
	}

	public void setCountryDetail(String countryData) {
		this.countryDetail = countryData;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String address) {
		this.physicalAddress = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public int getIsActive() {
		return isActive;
	}
	

	public int getIsApproved() {
		return isApproved;
	}
	
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}	
	
	public void setCookRating(double cookRating) {
		this.cookRating = cookRating;
	}

	public String getIDName() {
		return IDName;
	}

	public void setIDName(String iDName) {
		IDName = iDName;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public double getCookRating() throws SQLException {
		updateCookRating();
		return cookRating;
	}
	
	public void updateCookRating() throws SQLException {
		//substitute for set method
		//int cookID = Integer.parseInt(this.getUserID());
		if(new CookDAO().updateAverageRating(this.getUserID()))
			this.cookRating = new CookDAO().getAverageRating(this.getUserID());
	}
	
	public String getAvailability() {
		//indicates status of registration for adding a new cook (from constructor)
		return availability;
	}
	
	/**
	 *  Method to set the cook's default availability as a string
	 * @param days
	 */
	public void setAvailability(ArrayList<DayOfTheWeek> days) {
		for(int i = 0; i < days.size(); i++) {
			if(! this.availability.contains(days.get(i).day))
				this.availability += days.get(i).day + " ";
		}
	}
	
	/**
	 * Method for cook to update their availability from normal schedule. 
	 * @param unavailDate to indicate the day(s) that the cook is not available for that week
	 * @return updated schedule with missing day given
	 */
	
	public String updateAvailability(ArrayList<DayOfTheWeek> unavailable) {		
		//get current unavailability
		String unavail = "";
		//get all unavailable dates if any
		if(unavailable.size() != 0) {
			for(int i = 0; i < unavailable.size(); i++) {
				unavail += unavailable.get(i).day + " "; //show the unavailable day					
			}			
		}	
		this.availability += "\nNot available this week: " + unavail;
		return availability;
	}
	
}
