package momsfood.FXMLandControllers;
import java.io.*;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import momsfood.classes.*;

public class CustomerMealRequestController {


    @FXML
    private DatePicker deliveryDatePicker;

    @FXML
    private Button profilButton;

    @FXML
    private Label countryLabel;

    @FXML
    private Button logoButton;

    @FXML
    private Button cartButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button searchFoodButton;

    @FXML
    private ComboBox<String> addFoodCountryComboBox;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox<String> cookComboBox;

    @FXML
    private Button backButton;

    @FXML
    private TextField addFoodNameTextField;

    @FXML
    private Button requestFoodButton;
    
	//global variables for cook and customer data	
	Customer cust;
	ObservableList<Cook> cooks = FXCollections.observableArrayList();
	ObservableList<String> cookNames =  FXCollections.observableArrayList();
    ObservableList<OrderLine> items = FXCollections.observableArrayList();
    
    
	/**
	 * Method to receive data from a previous page
	 * @param customer customer object being passed from previous page
	 * @param list orderline list being passed from previous page
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
    public void initData(Customer customer, ObservableList<OrderLine> list) throws FileNotFoundException, SQLException {
    	this.cust = customer;
    	if(list.size() != 0)
    		this.items = list;
    	openPage();
    }
    
    /**
     * Method to initialize page
     */
    public void initialize() {}
    
    /**
     * Method to set scene items and open page
     * @throws SQLException
     * @throws FileNotFoundException
     */
	public void openPage() throws SQLException, FileNotFoundException {
		//set cook username and number of orders once they log into their profile
		usernameLabel.setText("Hello, " + this.cust.getFirstName() + "!");
		//load countries for selection on comboBox
		//get countries and save into list from database
		ObservableList<String> countries = new CountryDAO().getCountryNames();
		//load the combo box with the countries
		addFoodCountryComboBox.setItems(countries);
		//load and display all cook names
		cooks = new CookDAO().getAllCooks();
		for(int i = 0; i < cooks.size(); i++) 
			cookNames.add(cooks.get(i).getIDName());
		cookComboBox.setItems(cookNames);
	}
	
	/**
	 * Method to add the meal item to the database after retrieving user inputs and validating them
	 */
	public void requestItem() {
		//insertion values
		String mealName="", mealDesc = "", country="";
		double price = 0;
		
		boolean flag = false; //check for errors
		//validate input values
		Alert alert = new Alert(AlertType.CONFIRMATION);
		String message = "Please";
		//If text field is null, show an error
		if(addFoodNameTextField.getText().trim().length() == 0) {
			message += " enter the food name";
			flag = true;
		}//Else store the text in meal name
		else mealName = addFoodNameTextField.getText();
		//If text field is null, show an error
		if(addFoodCountryComboBox.getValue().toString().trim().length() == 0) {
			message += " select a valid origin country for this meal";
			flag = true;
		}//Store value in variable
		else country = addFoodCountryComboBox.getValue();
		//If text field is null, show an error
		if(descriptionTextArea.getText().trim().length() == 0) {
			message += " enter a description for this meal item";
			flag = true;
		}//store description in variable
		else mealDesc = descriptionTextArea.getText();
		//Show alert message
		alert.setContentText(message);
		//check that some values are entered
		if(flag)
			alert.showAndWait();
		else {
			//get cookID
			int cookID = 0;
			for(int i = 0; i < cookNames.size(); i++) {
				if(cookNames.get(i).equals(cooks.get(i).getIDName()))
					cookID = cooks.get(i).getUserID();
			}
			//insert meal into database
			if(new MealDAO().addMeal(mealName, cookID, mealDesc, price, "Special Customer Request", country, 0, null)) {
				message = "The meal has been submitted to the cook as a request!";
				resetPage();
			}
			//Else let the user know that meal could not be submitted
			else
				message = "The new meal could not be submitted. Please try again later";
			//display appropriate message
			alert.setContentText(message);
			alert.showAndWait();
		}
	}
	
	/**
	 * Method to go back to the profile section
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void back(ActionEvent e) throws IOException, SQLException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CustomerController controller = loader.getController(); 
		controller.iniData(this.cust, items);
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
	 * Method to reset all controls to empty or null 
	 */
	void resetPage() {
		//reset all input fields
		addFoodNameTextField.setText("");
		cookComboBox.setValue("");
		deliveryDatePicker.setValue(null);
		addFoodCountryComboBox.setValue("");
		descriptionTextArea.deleteText(0, descriptionTextArea.getLength());
		addFoodNameTextField.requestFocus();
	}

	/**
	 * Method to go to the search foods page for the customer
	 * @throws IOException 
    */
	public void searchFoods(ActionEvent e) throws IOException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CustomerController controller = loader.getController(); 
		controller.iniData(this.cust, items);
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
	 * Method to go to the customer's profile page
	 * @throws IOException 
    */
	public void profile(ActionEvent e) throws IOException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CustomerProfileController controller = loader.getController();  
		controller.initData(this.cust, this.items);
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
	 * Method to the customer's cart page
	 * @throws IOException 
	 * @throws SQLException 
    */
	public void changeToOrderPage(ActionEvent e) throws IOException, SQLException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("Checkout.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		OrderController controller = loader.getController();   
		controller.initData(cust, items, new Date(0));
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
    	loader.setLocation(getClass().getResource("Homepage.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		HomepageController controller = loader.getController();   
		//controller.passCurrentOrderLine(this.items);
		controller.iniData(this.cust, items);
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
