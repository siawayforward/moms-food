package momsfood.FXMLandControllers;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import momsfood.classes.*;

public class AdminProfileController {
	
    @FXML
    private TextField countryTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button previousButton;

    @FXML
    private CheckBox approvedCheckBox;

    @FXML
    private Button logoButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button searchCommentsButton;

    @FXML
    private TextField availabilityTextField;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Label matchesCountLabel;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private Label cookRatingLabel;

    @FXML
    private Label cookRatingLbl;
    
    @FXML
    private Label availLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Button searchCooksButton;
    
    @FXML
    private RadioButton cooksRadioButton;
    
    @FXML
    private RadioButton customersRadioButton;
    
    //Global results from query and admin user
    Connection conn = null;
    Statement stmt = null;
    ResultSet table = null;
    Admin admin = new Admin();
    Alert alert = new Alert(AlertType.CONFIRMATION);
    //Initialize variables
	String nameSearcher = "";
	int IDSearcher = 0;
    
    public void initialize() {
    	resetPage();
    }
    
    public void resetPage() {
    	//disable editing some user fields
    	nameTextField.setEditable(false);
    	emailTextField.setEditable(false);
    	addressTextField.setEditable(false);
    	phoneNumberTextField.setEditable(false);
    	countryTextField.setEditable(false);
    	availabilityTextField.setEditable(false);
    	//remove text from all fields
    	nameTextField.setText("");
    	emailTextField.setText("");
    	addressTextField.setText("");
    	phoneNumberTextField.setText("");
    	countryTextField.setText("");
    	availabilityTextField.setText("");
    	activeCheckBox.setSelected(false);
    	approvedCheckBox.setSelected(false);
    	searchTextField.setText("");
    }
    
    /**
     * Methods to display the values from the result set based on query run
     * @param table result set object to extract values from
     * @throws SQLException when there is a database error
     */
    public void displayCook(Cook cook) throws SQLException {
			//define the cook attributes at the current line
			if(cooksRadioButton.isSelected()) {
				nameTextField.setText(cook.getIDName());
				countryTextField.setText(cook.getCountryDetail());
				availabilityTextField.setText(cook.getAvailability());
				DecimalFormat form = new DecimalFormat("#.00");
				cookRatingLabel.setText(form.format(cook.getCookRating()));
				if(cook.getIsApproved() == 1)
					approvedCheckBox.setSelected(true);
				if(cook.getIsActive() == 1)
					activeCheckBox.setSelected(true);
				emailTextField.setText(cook.getEmailAddress());
				addressTextField.setText(cook.getPhysicalAddress());
				phoneNumberTextField.setText(cook.getPhoneNumber());
			}
    }
    
    public void displayCustomer(Customer cust) throws SQLException {
			//define customer attributes at the current line
			if(customersRadioButton.isSelected()) {
				nameTextField.setText(cust.getIDName());				
				emailTextField.setText(cust.getEmailAddress());
				addressTextField.setText(cust.getPhysicalAddress());
				phoneNumberTextField.setText(cust.getPhoneNumber());				
				if(cust.getIsActive() == 1)
					activeCheckBox.setSelected(true);
			}
    }
    

    /**
     * Methods to determine which records to search based on admin's needs and search criteria
     * @throws SQLException when there is a database error
     */
	public void searchPeopleListener() throws SQLException {
		//alert the user to select something
		Alert warn = new Alert(AlertType.WARNING);
		warn.setContentText("Please select a user type for records to search");
		if(searchTextField.getText().trim().length() != 0 && (cooksRadioButton.isSelected() || customersRadioButton.isSelected())) {
			//get search value entered by admin
			try {
				IDSearcher = Integer.parseInt(searchTextField.getText());
				if(IDSearcher > 0) {
					//flag = true;
				}
			} catch (NumberFormatException e) {
				//if a string, save as a name search
				nameSearcher = searchTextField.getText().trim();
			}
			//check which radio button was clicked and call method to extract data from database and display
			if(cooksRadioButton.isSelected()) {
				Cook cook = getCook(IDSearcher, nameSearcher, nameSearcher);
				displayCook(cook);
				countryTextField.setVisible(true);
				availabilityTextField.setVisible(true);
				approvedCheckBox.setVisible(true);
				cookRatingLabel.setVisible(true);
				cookRatingLbl.setVisible(true);
				countryLabel.setVisible(true);
				availLabel.setVisible(true);
			}		
			else if(customersRadioButton.isSelected()) {
				//remove the cook attribute boxes
				countryLabel.setVisible(false);
				availLabel.setVisible(false);
				countryTextField.setVisible(false);
				availabilityTextField.setVisible(false);
				approvedCheckBox.setVisible(false);
				cookRatingLabel.setVisible(false);
				cookRatingLbl.setVisible(false);
				Customer cust = getCustomer(IDSearcher, nameSearcher, nameSearcher);
				displayCustomer(cust);
			}
		}
		else
			warn.showAndWait();	
	}
	
	/**
	 * Method to return the cook from a database result set
	 * @return cook object
	 * @throws SQLException
	 */
	public Cook returnCook() throws SQLException {
		Cook cook = null;
		try
		{
			cook = new Cook(table.getInt("CookID") + " - " + table.getString("FirstName") + " " + table.getString("LastName"),
					table.getString("CountryName"),
					table.getString("Availability"),
					table.getString("EmailAddress"),
					table.getString("PhysicalAddress"),
					table.getString("PhoneNumber"),
					table.getDouble("CookRating"),
					table.getInt("IsApproved"),
					table.getInt("IsActive"));
		}
		catch(SQLException e)
		{
			System.out.print("");
		}
		
		return cook;
	}
	
	/**
	 * Method to return the customer from a database result set
	 * @return customer object
	 * @throws SQLException
	 */
	public Customer returnCustomer() throws SQLException {
		//get customer object
		Customer cust = null;
		try
		{
			cust = new Customer(table.getInt("CustomerID") + " - " + table.getString("FirstName") + " " + table.getString("LastName"),
					table.getString("EmailAddress"),
					table.getString("PhoneNumber"),
					table.getString("Password"),
					table.getString("PhysicalAddress"),
					table.getInt("IsActive"));
			System.out.println(cust.getIDName());
		}
		catch(SQLException e)
		{
			System.out.print("");
		}	
		
		return cust;
	}
	
	/**
	 * Method to get search results for cooks by their name
	 * @param firstName string: first name of cook
	 * @param lastName string: last name of cook
	 * @return arraylist of all cooks matching search. Includes (0)first and (1)last names, 
	 * 		(2)email, (3)physical address, (4)phone number, (5)country, (6)cook rating, 
	 * 		(7)approval and (8)activity status, and (9)availability
	 * @throws SQLException
	 */
	public Cook getCook(int ID, String firstName, String lastName) throws SQLException {
		//query to run
		String query = "SELECT * FROM Tbl_Cook "
				+ " WHERE (FirstName LIKE '%" + firstName + "%' OR LastName LIKE '%" + lastName + "%' OR CookID = " + ID +")"
				+ " ORDER BY LastName";
		Cook cook = new Cook(); //return variable
		//connect and run query
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			table = stmt.executeQuery(query);	
			table.first();
			//return cook
			cook = returnCook();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}		
		return cook; //return all search results
	} 
	
	/**
	 * Method to get previous cook
	 * @return cook
	 * @throws SQLException
	 */
	public Cook getPreviousCook() throws SQLException {
		
			if(table.previous())
				table.previous();
			else
				table.first();	
		
		return returnCook();
	}
	
	/**
	 * Method to get next cook
	 * @return cook
	 * @throws SQLException
	 */
	public Cook getNextCook() throws SQLException {
		
		if(table.next())
				table.next();
			else
				table.last();
	
		return returnCook();
	}
	
	/**
	 * Method to search customer table by customer name or ID
	 * @param ID of the customer
	 * @param firstName of customer: string
	 * @param lastName of customer: string
	 * @throws SQLException
	 */
	public Customer getCustomer(int ID, String firstName, String lastName) throws SQLException {
		//where condition for filter
		String where =   " WHERE (FirstName LIKE '%" + firstName + "%' OR LastName LIKE '%" + lastName + "%' OR CustomerID = " + ID +")";
		//get query results
		table = new CustomerDAO().searchCustomers(where);
		table.first();
		return returnCustomer();
	}
	
	/**
	 * Method to get previous customer
	 * @return customer
	 * @throws SQLException
	 */
	public Customer getPreviousCustomer() throws SQLException {
		
		if(table.previous())
				table.previous();
			else
				table.first();
		
		return returnCustomer();
	}
	
	/**
	 * Method to get next customer
	 * @return customer
	 * @throws SQLException
	 */
	public Customer getNextCustomer() throws SQLException {
		
			if(table.next())
				table.next();
			else			
				table.last();
			
		
		return returnCustomer();
	}
	
	/**
	 * Method to go to the previous record from the search results
	 * @throws SQLException when there is a database error
	 */
	public void previousRecord() throws SQLException {
		try
		{
			if(cooksRadioButton.isSelected())
				displayCook(getPreviousCook());
			if(customersRadioButton.isSelected())
				displayCustomer(getPreviousCustomer());
		}
		catch(NullPointerException e)
		{
			System.out.print("");
		}
		
	}
	
	/**
	 * Method to go to the next record from the search results
	 * @throws SQLException when there is a database error
	 */
	public void nextRecord() throws SQLException {
		try 
		{
			if(cooksRadioButton.isSelected())
				displayCook(getNextCook());
			if(customersRadioButton.isSelected())
				displayCustomer(getNextCustomer());
		}
		
		catch (NullPointerException e)
		{
			System.out.print("");
		}
	}
	
	/**
	 * Method to move to the comments page for the admin's view
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void goToComments(ActionEvent e) throws IOException, SQLException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("AdminSearchComments.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		AdminSearchCommentsController controller = loader.getController();
    	//call method
		controller.initialize();   
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Mom's Food Admin View");
		//show stage
		stage.show();
	}
	
	/**
	 * Method to save any changes made to deactivate the customer OR deactivate/change approval of the cook
	 * @throws SQLException when there is a database error
	 */
	public void saveChanges() throws SQLException {
		String confirm = "Update didn't happen. Please try again"; //alert text
		//update active or approval status of cooks/customers
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Admin User Updates");    	
    	
		if(cooksRadioButton.isSelected()) {
			CookDAO update = new CookDAO();
			//give admin alert if both changes have been made
			if(update.updateCookStatus(approvedCheckBox.isSelected(), "Approval", table.getInt("CookID")) && 
					update.updateCookStatus(activeCheckBox.isSelected(), "Activity", table.getInt("CookID"))) {
		    	confirm = "Profile updated!";
			}			
		}

		if(customersRadioButton.isSelected() && admin.updateCustomer(table.getInt("CustomerID"), activeCheckBox.isSelected())) {
			confirm = "Profile updated!";
		}
			
		//display confirmation
		alert.setContentText(confirm);
		alert.showAndWait();
	}
	
	/**
	 * Method to delete a user account and notify the admin user of the action status
	 * @throws SQLException when there is a database error
	 */
	public void deleteAccount() throws SQLException {
		String confirm = "Update didn't happen. Please try again"; //alert text
		if(cooksRadioButton.isSelected())
			confirm = admin.deleteCookAcct(table.getInt("CookID"));
		if(customersRadioButton.isSelected())
			confirm = admin.deleteCustomerAcct(table.getInt("CustomerID"));
    	alert.setContentText(confirm);
		alert.showAndWait();
		resetPage();
	}
	
	/**
	 * Method to Log out and go back to the log in page
	 * @throws IOException 
    */
	public void logOut(ActionEvent e) throws IOException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("Log-In.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		LoginController controller = loader.getController(); 
		controller.initialize();
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Mom's Food");
		//show stage
		stage.show();
	}
	/**
    * Method to move back to the home page. 
	 * @throws IOException 
	 * @throws SQLException 
    */
	public void goToHomePage(ActionEvent e) throws IOException, SQLException {		
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("AdminProfile.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		AdminProfileController controller = loader.getController(); 
		controller.initialize();
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Mom's Food");
		//show stage
		stage.show();	
	} 
}
