package momsfood.FXMLandControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import momsfood.classes.MomsFoodDBConstants;

public class ForgotPasswordController {

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button backButton;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField usernameTextField;
    
    //Variables to be used 
    String username = "";
    String password = "";
    String confirmPassword = "";
    ResultSet row = null;
    int customerID = 0;
    
    /** 
     * Method to initialize page
     */
    public void initialize()
    {
    	
    }
    
    /**
     *  Method to confirm the user inputs and check various items 
     */    
    
    public void confirmButtonListener(ActionEvent e) throws IOException, SQLException
    {
    	//Get user input and store them in the appropriate variables
    	username = usernameTextField.getText();
    	password = passwordTextField.getText();
    	confirmPassword = confirmPasswordTextField.getText();
    	 
    	//If any of the text fields are null show an error
    	if(username.equals("")|| password.equals("")|| confirmPassword.equals(""))
    	{
    		Alert alert = new Alert(AlertType.INFORMATION);
   	        alert.setTitle("Mom's Food");
   	        alert.setHeaderText("Empty TextField");
   	        alert.setContentText("Please fill out all the text fields");
   	        alert.showAndWait();
    	}    	
    	//If password and confirm password are not equal show an error
    	else if(!password.equals(confirmPassword))
    	{
    		Alert alert = new Alert(AlertType.INFORMATION);
   	        alert.setTitle("Mom's Food");
   	        alert.setHeaderText("Mismatching Passwords");
   	        alert.setContentText("Passwords do not match");
   	        alert.showAndWait();
    	}    	
    	
    	else    	
    	{   	    
    		//Get ID from confirmUsernameExist method
    		int id =confirmUsernameExist(username);
    		
    		try {
    	    	//Set the connection to the database
    				Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
    				//Create variable to create new statement
    				Statement stmt = conn.createStatement();
    				//Create the SQL statement to be executed
    				String sql = "UPDATE tbl_customer "+
    		    			"SET password = '"+ password + 
    		    			"' WHERE CustomerID = " + id+ ";";	   			
    				
    				//Create variable and run query to show if execution was successful
    		    	   int row = stmt.executeUpdate(sql);    	
    		    	   //If row is equal to one, say the password has been updated and send them to login page
    		    	   if (row == 1)
    		    	   {
	    		    		 Alert alert = new Alert(AlertType.INFORMATION);
	    		   	        alert.setTitle("Mom's Food");
	    		   	        alert.setHeaderText("");
	    		   	        alert.setContentText("Password has been updated!");
	    		   	        alert.showAndWait();
	    		   	        
	    		   	     try {
	    		   	    	 //Create new FXML loader object
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
	    				    	//Call initialize method
	    				    	controller.initialize();
	    				    	//Set the scene
	    				    	stage.setScene(scene);
	    				    	// show the stage
	    				    	stage.show();
	    				    }
	    				//Catch the exception error	 
	    		   	     catch(Exception ex) 
	    		   	     {
	    						 System.out.print(ex.getMessage());	
	    					 }	
    		   			}	
    		    	  
    		    	   else
    		    		//Show an alert that user name does not exist in the database
    		    	   {
    		    		   
    		    		Alert alert = new Alert(AlertType.INFORMATION);
       		   	        alert.setTitle("Mom's Food");
       		   	        alert.setHeaderText("");
       		   	        alert.setContentText("Username does not exist, please try again or create an account");
       		   	        alert.showAndWait();
    		    	   }
    		      }  
    	    	
    		    	 //Catch statement
    		    	    catch (SQLException e1) {
    		    	    	e1.printStackTrace();	    	    	
    			}		    			
	    	}  
    	}
    

    	/**
    	 *  Method to send the user back to the login page 
    	 */
    
	    public void backButtonListener(ActionEvent e) throws IOException
	    {
	    	  try {
	    		  //Creat new FXML loader object
			    	FXMLLoader loader = new FXMLLoader();
			    	// specify the file location
			    	loader.setLocation(getClass().getResource("Log-In.fxml"));
			    	// the object representing the root node of the scene, load the UI
			    	Parent parent = loader.load(); 
					// set the scene
					Scene scene = new Scene(parent);    	
					//scene.getStylesheets().add("src/MyStyles.css");	
					
			    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			    	//Create new controller  	
			    	LoginController controller = loader.getController();
			    	//Call initialize method from login controller
			    	controller.initialize();
			    	// set the scene for the stage
			    	stage.setScene(scene);
			    	// show the stage
			    	stage.show();}
				 catch(Exception ex) {
					 System.out.print(ex.getMessage());	
				 }	
	    }
	    
	    /**
	     *  Method to confirm that the username the user inputed does exist in database
	     * @param username: The username for a particular user to allow access
	     */
    	    
	    public Integer confirmUsernameExist(String username)
	    {
	    	
	    //Get user name
    	this.username = username;	
    	
    	//Declare variable customerID and set it to 0    	
    	int customerID = 0;
    			try {
    		    	//Set the connection to the database
    					Connection conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
    					//Create variable to create new statement
    					Statement stmt = conn.createStatement();
    					//Create the SQL statement to be executed
    					String sql = "Select customerID FROM tbl_customer Where username = '"+username +"';";		    	   		
    					//Create variable and run query to show if execution was successful
    			    	  row = stmt.executeQuery(sql);
    			    	 //If statement is successful or row ==1 make flag = true
    			    	   while(row.next())
    			    	   {
    			    		    customerID = row.getInt("CustomerID");    			    		  
    			    	   }
    		    	}
    			    	 //Catch statement
    			    	    catch (SQLException e) {
    			    	    	e.printStackTrace();	    	    	
    				}
    			//Return flag boolean
    			return customerID;
    }
}
