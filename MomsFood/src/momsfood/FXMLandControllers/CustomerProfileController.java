package momsfood.FXMLandControllers;

import momsfood.classes.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CustomerProfileController {
	  
    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button commentButton;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextArea addressTextArea;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private Button updateProfileButton; 
    
    @FXML
    private Button logoButton;     
    
    @FXML
    private Button cartButton; 
    
    @FXML
    private Button searchFoodButton;
    
    @FXML
    private Button profileButton; 
    
    @FXML
    private Button logOutButton; 
    
    
    //global variables
    Customer cust = new Customer();
    ObservableList<OrderLine> items = FXCollections.observableArrayList();
    
	//method to get data from log in scene
    public void initData(Customer customer, ObservableList<OrderLine> list) {
    	this.cust = customer;
    	//If the list size is not zero, make items = list
    	if(list.size() != 0)
    		this.items = list;
    	//Call open page method
    	openPage();
    }
    
    /**
     * Method to open page and initialize values on page
     */
    public void openPage() {
    	//set fields for customer
       	firstNameTextField.setText(cust.getFirstName());
       	firstNameTextField.setEditable(false);
    	lastNameTextField.setText(cust.getLastName());
    	addressTextArea.setText(cust.getPhysicalAddress());
    	emailTextField.setText(cust.getEmailAddress());
    	phoneTextField.setText(cust.getPhoneNumber());
    	passwordTextField.setText(cust.getPassword());
    	//set customer name
    	usernameLabel.setText("Hello, " + cust.getFirstName() + "!");
    }

    /**
     * Method to initialize page
     */
    public void initialize() { }
    
    /**
     * Method to validate user inputs and update the profile data
     * @param e
     * @throws SQLException
     */
    public void updateProfile(ActionEvent e) throws SQLException {
    	String confirm = "Please add a valid ";
    	//set customer values
    	if(lastNameTextField.getText().trim().length() != 0) {
    		cust.setLastName(lastNameTextField.getText());
    		if(addressTextArea.getText().trim().length() != 0) {
        		cust.setPhysicalAddress(addressTextArea.getText());
        		if(emailTextField.getText().trim().length() != 0) {
        			cust.setEmailAddress(emailTextField.getText());
            			if(phoneTextField.getText().trim().length() != 0) {
            				cust.setPhoneNumber(phoneTextField.getText());
            				if(passwordTextField.getText().trim().length() != 0 && passwordTextField.getText().trim().length() != 0 &&
            		    			passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
            		    		cust.setPassword(passwordTextField.getText());
            					//update profile
            		    		//insert values into database
            		        	if(new CustomerDAO().updateCustomerProfile(cust)) 
            		        		confirm = "Your profile has been updated!";
            		        	else
            		        		confirm = "Sorry, we couldn't update your profile at this time. Please try again later";
            		    		Alert done = new Alert(AlertType.CONFIRMATION);
            		    		done.setContentText(confirm);
            		    		done.showAndWait();
            				}
            		    	else if(passwordTextField.getText().trim().length() != 0 && confirmPasswordTextField.getText().trim().length() == 0) {
            		    		cust.setPassword(passwordTextField.getText());
            		    		//update profile
            		    		//insert values into database
            		        	if(new CustomerDAO().updateCustomerProfile(cust)) 
            		        		confirm = "Your profile has been updated!";
            		        	else
            		        		confirm = "Sorry, we couldn't update your profile at this time. Please try again later";
            		    		Alert done = new Alert(AlertType.CONFIRMATION);
            		    		done.setContentText(confirm);
            		    		done.showAndWait();
            		    	}
            		    	else { //validate entry and return error if needed
            		    		confirm += " password to match or enter a new password";
            		    		Alert alert = new Alert(AlertType.WARNING);
            		    		alert.setContentText(confirm);
            		    		alert.showAndWait();
            		    		passwordTextField.requestFocus();
            		    	}    	
            			} else { //validate entry and return error if needed
            	    		confirm += " phone number";
            	    		inputValidation(phoneTextField, null, confirm);
            	    	}
            		} else { //validate entry and return error if needed
               		confirm += " email address";
               		inputValidation(emailTextField, null, confirm);
            	} 
    		} else { //validate entry and return error if needed
        		confirm += " physical address";
        		inputValidation(null, addressTextArea, confirm);
        	}
    	} else { //validate entry and return error if needed
    		confirm += " last name";
    		inputValidation(lastNameTextField, null, confirm);
    	}    	
    }
    
    /**
     * Method to validate inputs for the user and showing error where necessary
     * @param field textfield to review values
     * @param area text area to review values
     * @param confirm message to display to user if error is there
     */
    public void inputValidation(TextField field, TextArea area, String confirm) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText(confirm);
    	if(field.getText().length() == 0 || area.getText().length() == 0 )	{
    		alert.showAndWait();
    		field.requestFocus();
    	}
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
	public void changeToOrderPage(ActionEvent e)  {
		Scene scene = null;
		try {
			//FXML Loader defined
	    	FXMLLoader loader = new FXMLLoader();
	    	//set the scene to load
	    	loader.setLocation(getClass().getResource("Checkout.fxml"));
	    	//find the controller class and use the initData method
	    	//set the scene/load UI
	    	Parent parent = loader.load();
			// set the scene
			scene = new Scene(parent);
	    	//new SideMenuController instance
			OrderController controller = loader.getController();
			controller.initData(cust, items, new Date(0));
		} catch(Exception ex) {}
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
