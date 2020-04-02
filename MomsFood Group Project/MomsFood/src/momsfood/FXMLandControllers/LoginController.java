package momsfood.FXMLandControllers;
import momsfood.classes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import momsfood.classes.MomsFoodDBConstants;
import momsfood.classes.User;

public class LoginController {

    @FXML
    private Button createAccountButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private RadioButton customerRadioButoon;

    @FXML
    private RadioButton adminRadioButton;

    @FXML
    private RadioButton cookRadioButton;
    @FXML
    private ToggleGroup Toggle;
    

    
    // Set variables to be used in the page
    String username = "";
	String password = "";
	User user = new User();
	ResultSet access;
	String[] array = new String[3];
	ResultSet row = null;
	int custID = 0;
	
	/* An initalize method
	 */
	public void initialize()
	{
		
	}
	
	/* A method to take the user to the register scene 
	 * @Param : ActionEvent  
	 */
	
	public void createAccountButtonListener(ActionEvent e) throws Exception {
			//If customer radio button is selected
		if(customerRadioButoon.isSelected())
		{
		 try {
		    	FXMLLoader loader = new FXMLLoader();
		    	// specify the file location
		    	loader.setLocation(getClass().getResource("Register.fxml"));
		    	// the object representing the root node of the scene, load the UI
		    	Parent parent = loader.load(); 
				// set the scene
				Scene scene = new Scene(parent);    	
				//scene.getStylesheets().add("src/MyStyles.css");	
				
		    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    	// change the title of the window
		    	//stage.setTitle(sceneTitle);
		    	// set the scene for the stage
		    	RegisterController controller = loader.getController();
		    	
		    	controller.initialize();		
		    	stage.setScene(scene);
		    	// show the stage
		    	stage.show();}
			 catch(Exception ex) {
				 System.out.print(ex.getMessage());	
			 }
		}
		//If cook radio button is selected
		else if(cookRadioButton.isSelected())
		{
			try {
			    FXMLLoader loader = new FXMLLoader();
			    // specify the file location
			    loader.setLocation(getClass().getResource("CookRegister.fxml"));
			    // the object representing the root node of the scene, load the UI
			    Parent parent = loader.load(); 
				// set the scene
				Scene scene = new Scene(parent);    	
				//scene.getStylesheets().add("src/MyStyles.css");	
				
			    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			    // change the title of the window
			    //stage.setTitle(sceneTitle);
			    // set the scene for the stage
			    CookRegisterController controller = loader.getController();
			    
			    controller.initialize();		
			    stage.setScene(scene);
			    // show the stage
			    stage.show();}
				catch(Exception ex) {
				 System.out.print(ex.getMessage());	
				 }
		}
		//If admin radio button is selected
		else if(adminRadioButton.isSelected()) {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("");
		        alert.setContentText("Access denied");						        
		        alert.showAndWait();
		}
	}
	
	/* A method to take the user to the forgotPassword scene
	 * @Param : ActionEvent  
	 */
		
	public void forgotPasswordButtonListener(ActionEvent e) throws Exception {
		//If admin radio button is selected
		if(adminRadioButton.isSelected()) {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("");
		        alert.setContentText("Access denied");						        
		        alert.showAndWait();
		}
		else { //let user change password otherwise
			 try {
				 	//Create new FMXL loader
			    	FXMLLoader loader = new FXMLLoader();
			    	// specify the file location
			    	loader.setLocation(getClass().getResource("ForgotPassword.fxml"));
			    	// the object representing the root node of the scene, load the UI
			    	Parent parent = loader.load(); 
					// Create a new scene object
					Scene scene = new Scene(parent);    	
				
			    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();		    	
			    	// Create new forgetpasswordcontroller object
			    	ForgotPasswordController controller = loader.getController();		    	
			    	controller.initialize();	
			    	// set the scene for the stage
			    	stage.setScene(scene);
			    	// show the stage
			    	stage.show();}
				 catch(Exception ex) {
					 System.out.print(ex.getMessage());	
				 }	
		}			
	}
	
	/* A method to take the user to the register scene 
	 * @Param : ActionEvent  
	 */
		
	public void loginButtonListener(ActionEvent e) throws Exception {
		//Initalize a boolean check as false
		boolean check = false;
		//If customer radio button is selected
		if(customerRadioButoon.isSelected())
		{
		//Get user name and password from the user
			username = usernameTextField.getText();
			password = passwordTextField.getText();	
			//Call the customerLoginQuery and set to variable check
			check = customerLoginQuery(username, password);
			//If user name or password are null, show an alert
			if(username.equals("") || password.equals("")) 
			{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("Username/Password");
		        alert.setContentText("Please insert username and password");
		        alert.showAndWait();
			}
			
			//Else if the result from method "check" is false show an alert
			else if(!check)
			{				 
				 Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Mom's Food");
			        alert.setHeaderText("");
			        alert.setContentText("Username and password not found");						        
			        alert.showAndWait();
			}
			else 
			{
			     //If result from method check is true send the user to the login page	
				//Also send over customer object to the next page
				Customer cust = new CustomerDAO().getCustomerProfile(this.custID);	
				//Set userID to new customer cust
				cust.setUserID(this.custID);
				 {					 
					 try {
						 //Create new FMXL loader
					    	FXMLLoader loader = new FXMLLoader();
					    	// specify the file location
					    	loader.setLocation(getClass().getResource("HomePage.fxml"));
					    	// the object representing the root node of the scene, load the UI
					    	Parent parent = loader.load(); 
							// set the scene
							Scene scene = new Scene(parent);    	
												
					    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					    	// change the title of the window
					    	//stage.setTitle(sceneTitle);
					    	// set the scene for the stage
					    	HomepageController controller = loader.getController();		
					    	ObservableList<OrderLine> list = FXCollections.observableArrayList();
					    	controller.iniData(cust, list);		
					    	stage.setScene(scene);
					    	// show the stage
					    	stage.show();}
						 catch(Exception ex) {
							 System.out.print(ex.getMessage());				        
				 }						 
			}	
		}
	}
		//else if cook radio button is selected
		else if(cookRadioButton.isSelected())
		{
			//Get user name and password from the user
			username = usernameTextField.getText();
			password = passwordTextField.getText();	
			//Call the cookLoginQuery and set to variable check
			check = cookLoginQuery(username, password);
			//If user name or password are null, show an alert
			if(username.equals("") || password.equals("")) 
			{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("Username/Password");
		        alert.setContentText("Please insert username and password");
		        alert.showAndWait();
			}			
			//Else if the result from method "check" is false show an alert
			else if(!check)
			{				 
				 Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Mom's Food");
			        alert.setHeaderText("");
			        alert.setContentText("Username and password not found");						        
			        alert.showAndWait();
			}
			else 
			{
					 try {
						//Create new FMXL loader
				    	FXMLLoader loader = new FXMLLoader();
				    	// specify the file location
				    	loader.setLocation(getClass().getResource("CookProfile.fxml"));
				    	// the object representing the root node of the scene, load the UI
				    	Parent parent = loader.load(); 
						// set the scene
						Scene scene = new Scene(parent);    	
						//scene.getStylesheets().add("src/MyStyles.css");	
						
				    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				    	// change the title of the window
				    	//stage.setTitle(sceneTitle);
				    	// set the scene for the stage
				    	CookProfileController controller = loader.getController();
				    	Cook cook = new CookDAO().selectCookProfile(row.getInt("CookID"));
				    	cook.setUserID(row.getInt("CookID"));
				    	controller.initData(cook);		
				    	stage.setScene(scene);
				    	// show the stage
				    	stage.show();}
					 catch(Exception ex) {
						 System.out.print(ex.getMessage());					 
					 }					 
				 }	
			}	
		//when admin is logging in
		else if(adminRadioButton.isSelected())
		{
			//Get user name and password from the user
			username = usernameTextField.getText();
			password = passwordTextField.getText();			
			check = adminLoginQuery(username, password);
			//If user name or password are null, show an alert
			if(username.equals("") || password.equals("")) 
			{
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("Username/Password");
		        alert.setContentText("Please insert username and password");
		        alert.showAndWait();
			}			
			//Else if the result from method "check" is false show an alert
			else if(!check)
			{				 
				 Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Mom's Food");
			        alert.setHeaderText("");
			        alert.setContentText("Username and password not found");						        
			        alert.showAndWait();
			}
			else 
			{
					 try {
						//Create new FMXL loader
				    	FXMLLoader loader = new FXMLLoader();
				    	// specify the file location
				    	loader.setLocation(getClass().getResource("AdminProfile.fxml"));
				    	// the object representing the root node of the scene, load the UI
				    	Parent parent = loader.load(); 
						// set the scene
						Scene scene = new Scene(parent);    	
											
				    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();				    	
				    	// Create new adminprofilecontroller object
				    	AdminProfileController controller = loader.getController();
				    	//Load intitalize method in Admin Profile Controller
				    	controller.initialize();	
				    	// set the scene for the stage
				    	stage.setScene(scene);
				    	// show the stage
				    	stage.show();}
					 catch(Exception ex) {
						 System.out.print(ex.getMessage());					 
					 }					 
				 }	
			}
		}		
	
	
	/* Method that helps determine if the user name and password combination for customer exist in our database
	 * @param : username: the user name unique for every user
	 * @param password: the password associated with the unique user name for every customer
	 */
	
	public boolean customerLoginQuery(String username, String password) throws Exception
	{		
		//Initalize boolean check as false
		boolean check = false;
		try {			
				Class.forName("com.mysql.cj.jdbc.Driver");
				//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//Create the SQL statement to be executed
				String sql ="SELECT CustomerID, FirstName, LastName, Username, password  FROM tbl_customer" +
						" WHERE username = '" +username +"' AND password = '"+ password+ "';";				
				
				//Create variable and run query to show if execution was successful
		    	   row = stmt.executeQuery(sql);	   	  			    	  
		    	   //If row has next, make boolean check = true and get customerID
		    	   if(row.next())
		    	   {				        
				        check = true;
				        custID = row.getInt("CustomerID");				        			        
				   }		   
		    	   //Else make sure that boolean check = false
		    	   else
		    	   {
		    		   check = false;
		    	   }
			}
				   	 //Catch statement and print message
					    catch (Exception f) 
					{
					    System.out.print(f.getMessage());;	    	    	
					}			
				//Return boolean check
				return check;
	}	
	
	/* Method that helps determine if the user name and password combination for cook exist in our database
	 * @param : username: the user name unique for every user
	 * @param password: the password associated with the unique user name for every cook
	 */
	public boolean cookLoginQuery(String username, String password)
	{
		//Initalize boolean check as false
		boolean check = false;
		try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				//Set the connection to the database
				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
				//Create variable to create new statement
				Statement stmt = conn.createStatement();
				//Create the SQL statement to be executed
				String sql ="SELECT CookID, FirstName, LastName, EmailAddress, PhoneNumber , PhysicalAddress, Username, password  FROM tbl_cook" +
						" WHERE username = '" +username +"' AND password = '"+ password+ "';";
				
				//Create variable and run query to show if execution was successful
		    	   row = stmt.executeQuery(sql);	   	  			    	  
		    	   
		    	   //If row has next, make boolean check = true 
		    	   if(row.next())
		    	   {				        
				        check = true;				        					        
				   }	
		    	   //Else make sure that boolean check = false
		    	   else
		    	   {
		    		   check = false;
		    	   }
			}
				   	 //Catch statement and print error message
					    catch (Exception f) 
					{
					    	System.out.print(f.getMessage());;	    	    	
					}	
				//Return boolean check
				return check;
	}
	
	/* Method that helps determine if the user name and password combination for cook exist in our database
	 * @param : username: the user name unique for every user
	 * @param password: the password associated with the unique user name for every cook
	 * Throws FileNotFound Exception
	 */
	
	public boolean adminLoginQuery(String username, String password) throws FileNotFoundException
	{
		//Initalize boolean method check as false and String variables "u" and "p" as null
		boolean check = false;		
		String  u = "";
		String p = "";
		//Create new file object
		File file = new File("src/Admin.txt");
		//Create new scanner object
		Scanner input = new Scanner(file);
		//Store next line in the appropriate string variables
		u = input.nextLine();
		p = input.nextLine();
		
		// If variables equal the user input make boolean "check" true
		if(u.equals(username) && p.equals(password))
		{
			check = true;
		}
		//Close the scanner object
		input.close();
		//Return boolean check
		return check;			
	}	
}


