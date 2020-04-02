package momsfood.FXMLandControllers;
import momsfood.classes.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CookProfileController {	
	
	   @FXML
	    private TextField phoneTextField;

	    @FXML
	    private TextField lastNameTextField;

	    @FXML
	    private Label countryLabel;

	    @FXML
	    private TextField emailTextField;

	    @FXML
	    private TextField confirmPasswordTextField;

	    @FXML
	    private Button addNewMealButton;

	    @FXML
	    private Button updateMealsButton;

	    @FXML
	    private ComboBox<String> countryComboBox;

	    @FXML
	    private Button updateProfileButton;

	    @FXML
	    private CheckBox mondayCheckBox;

	    @FXML
	    private CheckBox sundayCheckBox;

	    @FXML
	    private Label ordersLabel;

	    @FXML
	    private CheckBox saturdayCheckBox;

	    @FXML
	    private CheckBox wednesdayCheckBox;

	    @FXML
	    private Button logoButton;

	    @FXML
	    private TextField firstNameTextField;

	    @FXML
	    private CheckBox fridayCheckBox;

	    @FXML
	    private TextArea addressTextArea;

	    @FXML
	    private CheckBox thursdayCheckBox;

	    @FXML
	    private Label usernameLabel;

	    @FXML
	    private CheckBox tuesdayCheckBox;

	    @FXML
	    private Button availabilityUpdateButton;

	    @FXML
	    private Button logOutButton;

	    @FXML
	    private TextField newPasswordTextField;
	    
	
	//global variables for cook data
	Cook selectedCook; 
	
	/**
	 * InitData method that will receive info from previous pages
	 * @param cook
	 * @throws SQLException
	 */
    public void initData(Cook cook) throws SQLException {
    	this.selectedCook = cook;
    	openPage();
    }
	
    /**
     * OpenPage method to load all the information that is passed
     * @throws SQLException
     */
    public void openPage() throws SQLException {
		//fill cook data
		firstNameTextField.setText(selectedCook.getFirstName());
		firstNameTextField.setEditable(false);
		lastNameTextField.setText(selectedCook.getLastName());
		emailTextField.setText(selectedCook.getEmailAddress());
		phoneTextField.setText(selectedCook.getPhoneNumber());
		newPasswordTextField.setText(selectedCook.getPassword());
		addressTextArea.setText(selectedCook.getPhysicalAddress());
		showAvailability();
		//set country of cook
		ImageView coun = new ImageView(new CountryDAO().getCountryFlag(selectedCook.getCountryDetail()));
		countryLabel.setGraphic(coun);
		//load countries for selection on comboBox
		//get countries and save into list from database
		ObservableList<String> countries = (ObservableList<String>) new CountryDAO().getCountryNames();
		//load the combo box with the countries
		countryComboBox.setItems(countries);
		countryComboBox.setValue(selectedCook.getCountryDetail());
		//cook's username
		usernameLabel.setText("Hello, " + selectedCook.getFirstName() + "!");
		//cook's orders
		OrderDAO load = new OrderDAO();
		ordersLabel.setText(load.getCookOrders(selectedCook.getUserID()) + " orders; " + load.getSpecialRequests(selectedCook.getUserID()) + " requests");
		//set buttons based on activity status
		getActiveStatus();
    }
    /**
     * Initialize all the cook's information on the page after logging in
     */
	public void initialize() {}
	
	/**
	 * Method to save changes to a cook's profile when they update it
	 */
	public void updateProfile() {
		//get data that cook updated on their profile
		if(firstNameTextField.getText().trim().length() != 0) {
			selectedCook.setFirstName(firstNameTextField.getText());
			if(lastNameTextField.getText().trim().length() != 0) {
				selectedCook.setLastName(lastNameTextField.getText());
				if(emailTextField.getText().trim().length() != 0) {
					selectedCook.setEmailAddress(emailTextField.getText());
					if(newPasswordTextField.getText().trim().length() != 0 && newPasswordTextField.getText().trim().length() != 0 &&
    		    			newPasswordTextField.getText().equals(confirmPasswordTextField.getText())) {
						selectedCook.setPassword(newPasswordTextField.getText());
						if(phoneTextField.getText().trim().length() != 0) {
							selectedCook.setPhoneNumber(phoneTextField.getText());
							if(addressTextArea.getText().trim().length() != 0) {
								selectedCook.setPhysicalAddress(addressTextArea.getText());
								if(countryComboBox.getValue().trim().length() != 0) {
									selectedCook.setCountryDetail(countryComboBox.getSelectionModel().getSelectedItem());
									selectedCook.setAvailability("");
									selectedCook.setAvailability(getAvailability());
									//update database and notify user
									Alert alert = new Alert(AlertType.CONFIRMATION);
									//update database
									if(new CookDAO().updateCookProfile(selectedCook)) 
										alert.setContentText("Your profile has been updated!");
									else
										alert.setContentText("Sorry, your profile could not be updated at this time. Please try again later");

									alert.showAndWait();
								}	
								else inputValidation(countryComboBox.getEditor());
							}
							else { //Show the error
								Alert alert = new Alert(AlertType.WARNING);
								alert.setContentText("Please enter the missing field value(s)");
								alert.showAndWait();
								addressTextArea.requestFocus();
							}
						}
						else inputValidation(phoneTextField);	
					}
					else { //Show the error
						Alert alert = new Alert(AlertType.WARNING);
						alert.setContentText("Please enter matching password or confirm password to update your profile");
						alert.showAndWait();
						newPasswordTextField.requestFocus();
					}
				}
				else inputValidation(emailTextField);
			}
			else inputValidation(lastNameTextField);
		}
		else inputValidation(firstNameTextField);
	}
	
	/**
	 * Method to show error when needed by checking for null values on input fields
	 * @param field : the content text of the alert
	 */
	public void inputValidation(TextField field) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("Please enter the missing field value(s)");
		if(field.getText().trim().length() == 0) {
			alert.showAndWait();
			field.requestFocus();
		}		
	}
	
	/**
	 * Method to get user input for days of the week worked 
	 * @return arraylist of days of the week object to set availability
	 */
	public ArrayList<DayOfTheWeek> getAvailability() {
		ArrayList<DayOfTheWeek> days = new ArrayList<DayOfTheWeek>();
		if(mondayCheckBox.isSelected())
			days.add(DayOfTheWeek.MONDAY);
		if(tuesdayCheckBox.isSelected())
			days.add(DayOfTheWeek.TUESDAY);	
		if(wednesdayCheckBox.isSelected())
			days.add(DayOfTheWeek.WEDNESDAY);
		if(thursdayCheckBox.isSelected())
			days.add(DayOfTheWeek.THURSDAY);
		if(fridayCheckBox.isSelected())
			days.add(DayOfTheWeek.FRIDAY);
		if(saturdayCheckBox.isSelected())
			days.add(DayOfTheWeek.SATURDAY);
		if(sundayCheckBox.isSelected())
			days.add(DayOfTheWeek.SUNDAY);
		return days;
	}
	
	/**
	 * Method to select checkboxes based on cook availability in the database
	 */
	public void showAvailability() {
		if(selectedCook.getAvailability().trim().length() != 0) {
			if(selectedCook.getAvailability().contains(DayOfTheWeek.MONDAY.getDay()))
				mondayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.TUESDAY.getDay()))
				tuesdayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.WEDNESDAY.getDay()))
				wednesdayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.THURSDAY.getDay()))
				thursdayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.FRIDAY.getDay()))
				fridayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.SATURDAY.getDay()))
				saturdayCheckBox.setSelected(true);
			if(selectedCook.getAvailability().contains(DayOfTheWeek.SUNDAY.getDay()))
				sundayCheckBox.setSelected(true);
		}
	}
	
	/**
	 * Method to get activity or approval status of cook and disable buttons as needed
	 */
	public void getActiveStatus() {
		String query = "SELECT IsActive, IsApproved "
				+ " FROM Tbl_Cook"
				+ " WHERE CookID = " + selectedCook.getUserID(); //query to run
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
            		ResultSet.CONCUR_READ_ONLY);
			//query connector to run insert query and check if cook has been added
			ResultSet table = stmt.executeQuery(query);	
			table.first(); //go to first row to get result
			//check if user is active/approved and disallow cook to user
			if(table.getInt("IsApproved") == 0 || table.getInt("IsActive") == 0) {
				addNewMealButton.setDisable(true);
				updateMealsButton.setDisable(true);
			}
			else {
				addNewMealButton.setDisable(false);
				updateMealsButton.setDisable(false);
			}				
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.getMessage();
		}	
	}
	
	/**
	 * Method to move to the add new meal page for the cook
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void addNewMeal(ActionEvent e) throws IOException, SQLException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CookAddNewFood.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CookAddNewFoodController controller = loader.getController();   
		controller.initData(selectedCook.getUserID(), selectedCook.getFirstName(), selectedCook.getCountryDetail());
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Momsfood - Add New Meal");
		//show stage
		stage.show();
	}
	
	/**
	 * Method to move to the update meals view for the cook
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public void updateMeals(ActionEvent e) throws SQLException, Exception {		
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CookUpdateFood.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CookUpdateFoodController controller = loader.getController();   
		controller.initData(selectedCook.getUserID(), selectedCook.getFirstName(), selectedCook.getCountryDetail());
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Momsfood - Update Current Meals");
		//show stage
		stage.show();
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
    	loader.setLocation(getClass().getResource("CookProfile.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CookProfileController controller = loader.getController();   
		controller.initData(this.selectedCook);
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
