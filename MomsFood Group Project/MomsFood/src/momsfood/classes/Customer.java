package momsfood.classes;


public class Customer extends User {
	// Attributes
	private String IDName;
	private String physicalAddress;
	private String phoneNumber;
	private String emailAddress;
	private int isActive = 0;
	
	
	// No-arg Constructor
	public Customer() {}
	/**
	 * Profile display constructor for values to retrieve from database and display on customer profile
	 * @param fName string firstname of the customer
	 * @param lName string lastname of the customer
	 * @param email string email address of the customer
	 * @param phone string phone number of the customer
	 * @param address string physical address of the customer
	 * @param pw string password of the customer
	 */
	public Customer(String fName, String lName, String email, String phone, String address,String pw) {
		this.firstName = fName;
		this.lastName = lName;
		this.emailAddress = email;
		this.phoneNumber = phone;
		this.physicalAddress = address;		
		this.password = pw;
	}
	
	/**
	 * Profile display constructor for values to retrieve from database and display for admin profile
	 * @param IDName string including ID, first and last name of customer
	 * @param email string email address of the customer
	 * @param phone string phone number of the customer
	 * @param address string physical address of the customer
	 * @param pw string password of the customer
	 */
	public Customer(String IDName, String email, String phone, String address, String pw, int activity) {
		this.IDName = IDName;
		this.emailAddress = email;
		this.phoneNumber = phone;
		this.physicalAddress = address;		
		this.password = pw;
		this.isActive = activity;
	}
	
	/**
	 * Insertion constructor for values to add to database for a new customer profile
	 * @param fName string firstname of the customer
	 * @param lName string lastname of the customer
	 * @param email string email address of the customer
	 * @param phone string phone number of the customer
	 * @param address string physical address of the customer
	 * @param username: the username for the user
	 * @param pw string password of the customer
	 */
	public Customer(String fName, String lName, String email, String phone, String address, String username ,String pw) {
		this.firstName = fName;
		this.lastName = lName;
		this.emailAddress = email;
		this.phoneNumber = phone;
		this.physicalAddress = address;
		this.username = username;
		this.password = pw;
	}
	
	//getters and setters
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
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	/**
	 * The getPhysicalAddress method returns customer's physical address.
	 * @return The value in the physical address field.
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}

	/**
	 * The setPhysicalAddress method set the customer's physical address.
	 * @param physicalAddress
	 */
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	public String getIDName() {
		return IDName;
	}
	public void setIDName(String iDName) {
		IDName = iDName;
	}	
}

