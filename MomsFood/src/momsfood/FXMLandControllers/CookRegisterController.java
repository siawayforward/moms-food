package momsfood.FXMLandControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import momsfood.classes.Cook;
import momsfood.classes.MomsFoodDBConstants;

public class CookRegisterController {	
	 @FXML
	    private TextField countryTextField;

	    @FXML
	    private CheckBox wedCheckbox;

	    @FXML
	    private TextField passwordTextField;

	    @FXML
	    private CheckBox tueCheckbox;

	    @FXML
	    private TextField addressTextField;

	    @FXML
	    private Button submitButton;

	    @FXML
	    private TextField phoneTextField;

	    @FXML
	    private TextField lastNameTextField;

	    @FXML
	    private CheckBox sunCheckbox;

	    @FXML
	    private TextField emailTextField;

	    @FXML
	    private TextField confirmpasswordTextField;

	    @FXML
	    private TextField firstNameTextField;

	    @FXML
	    private CheckBox satCheckbox;

	    @FXML
	    private CheckBox thuCheckbox;

	    @FXML
	    private Button backButton;

	    @FXML
	    private CheckBox monCheckbox;

	    @FXML
	    private CheckBox friCheckbox;

	    @FXML
	    private TextField usernameTextField;

	    
	    //Declare variables to be used in the application
	    String fName ="";
	    String lName ="";
	    String email="";
	    String address="";
	    String username="";
	    String password="";
	    String confirmPassword ="";
	    String phone = "";
	    String country = "";
	    String availability = "";
	    
	    /* Method to initialize 
	     */
	    public void initialize()
	    {
	    	
	    }
	    
	    /**
	     * Button Listener method that is used when user presses submit button 
	     * @param e
	     */
		public void ButtonListener(ActionEvent e) 
		{		
			//Get user input and store in appropriate text field
			fName = firstNameTextField.getText();
			lName = lastNameTextField.getText();
			email = emailTextField.getText();
			address = addressTextField.getText();
			phone = phoneTextField.getText();
			username = usernameTextField.getText();
			password = passwordTextField.getText();
			confirmPassword = confirmpasswordTextField.getText();
			country = countryTextField.getText();
			//If check box is selected, add string to availability
			if(monCheckbox.isSelected())
			{
				availability += "Mon ";
			}	//If check box is selected, add string to availability
			if(tueCheckbox.isSelected())
			{
				availability += "Tue ";
			}	//If check box is selected, add string to availability
			if(wedCheckbox.isSelected())
			{
				availability += "Wed ";
			}	//If check box is selected, add string to availability
			if(thuCheckbox.isSelected())
			{
				availability += "Thur ";
			}	//If check box is selected, add string to availability
			if(friCheckbox.isSelected())
			{
				availability += "Fri ";
			}	//If check box is selected, add string to availability
			if(satCheckbox.isSelected())
			{
				availability += "Sat ";
			}	//If check box is selected, add string to availability
			if(sunCheckbox.isSelected())
			{
				availability += "Sun ";
			}
			
			
			//Create new cook object
			Cook cook = new Cook(fName, lName, email, address, phone, username, password, country, availability);
			
				//If any of the text fields are empty show an alert
				if (fName.equals("") || lName.equals("") || email.equals("") || address.equals("")
						|| phone == "" || username.equals("") || password.equals("") || country.equals("") || availability.equals(""))
				{
				//Show the alert to ask user to fill out all text fields	
					Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle("Mom's Food");
			        alert.setHeaderText("");
			        alert.setContentText("Please fill out all the text fields and select availabilty");
			        alert.showAndWait();
				}
				
				//If password textfield and confirm passwords are different, show an alert
				else if(!password.equalsIgnoreCase(confirmPassword))
				{
					Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle("Mom's Food");
			        alert.setHeaderText("");
			        alert.setContentText("Passwords do not match");
			        alert.showAndWait();
				}
				//Else insert new customer to the database
				else
				{								
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
							//Set the connection to the database
							Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
							//Create variable to create new statement
							Statement stmt = conn.createStatement();
							//Create the SQL statement to be executed
							String sql ="INSERT INTO momsfood.tbl_cook"+
									"(FirstName,LastName, EmailAddress, PhysicalAddress, PhoneNumber, Username, Password , CountryName , Availability, IsApproved, IsActive )"+
									  " VALUES ('" + cook.getFirstName()+ "', '" + cook.getLastName() + "', '" + cook.getEmailAddress() + "', '" 
									+ cook.getPhysicalAddress() + "', '" 
									+cook.getPhoneNumber() + "', '" + cook.getUsername() +"', '" + cook.getPassword() +"','"+cook.getCountryDetail() 
									+"', '"+cook.getAvailability()+"', 0, 0);";
							
							
							//Create variable and run query to show if execution was successful
					    	   int row = stmt.executeUpdate(sql);
					    	  //If execution was successful, let the user know and send them to login page
					    	   if(row == 1)
					    	   {				    		   
					    		   Alert alert = new Alert(AlertType.INFORMATION);
							        alert.setTitle("Mom's Food");
							        alert.setHeaderText("");
							        alert.setContentText("Thank you for signing up for Mom's Food!");						        
							        alert.showAndWait();
							        
							        try {
			    				    	FXMLLoader loader = new FXMLLoader();
			    				    	// specify the file location
			    				    	loader.setLocation(getClass().getResource("Log-In.fxml"));
			    				    	// the object representing the root node of the scene, load the UI
			    				    	Parent parent = loader.load(); 
			    						// set the scene
			    						Scene scene = new Scene(parent);    	
			    						//scene.getStylesheets().add("src/MyStyles.css");	
			    						
			    				    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			    				    	// change the title of the window
			    				    	//stage.setTitle(sceneTitle);
			    				    	// set the scene for the stage
			    				    	LoginController controller = loader.getController();
			    				    	
			    				    	controller.initialize();		
			    				    	stage.setScene(scene);
			    				    	// show the stage
			    				    	stage.show();}
			    					 catch(Exception ex) {
			    						 System.out.print(ex.getMessage());	
			    					 }	
		    		   			}	
					    	   //Let the user know there was an error
					    	   else
					    	   {				    		   
					    		   Alert alert = new Alert(AlertType.INFORMATION);
							        alert.setTitle("Mom's Food");
							        alert.setHeaderText("");
							        alert.setContentText("There was an error");						        
							        alert.showAndWait();
					    	   }				    	 
				    	}
					     //Catch statement
					    	  catch (Exception f) 
							{
					    	  f.printStackTrace();	    	    	
							}			
				}		
		}
			
		/**
		 * Method used for back button, used to take user back to login page
		 */
			public void backButtonListener(ActionEvent e) throws IOException
		
			{
				  try {
				    	FXMLLoader loader = new FXMLLoader();
				    	// specify the file location
				    	loader.setLocation(getClass().getResource("Log-In.fxml"));
				    	// the object representing the root node of the scene, load the UI
				    	Parent parent = loader.load(); 
						// set the scene
						Scene scene = new Scene(parent);    	
						//scene.getStylesheets().add("src/MyStyles.css");	
						
				    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				    	// change the title of the window
				    	//stage.setTitle(sceneTitle);
				    	// set the scene for the stage
				    	LoginController controller = loader.getController();
				    	
				    	controller.initialize();		
				    	stage.setScene(scene);
				    	// show the stage
				    	stage.show();}
					 catch(Exception ex) {
						 System.out.print(ex.getMessage());	
					 }	
			}
	}


