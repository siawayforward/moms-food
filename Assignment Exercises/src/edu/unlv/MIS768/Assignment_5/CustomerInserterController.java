package edu.unlv.MIS768.Assignment_5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * Controller class for the customer inserter GUI. This controls all actions and validation required during insertion of a new record into the customer table
 * @author Sia Mbatia
 * Date: April 9th 2019
 */
public class CustomerInserterController {

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField custNumberTextField;
    
    @FXML
    private Button addButton;

    @FXML
    private Button resetButton;
    
    public void initialize() {
    	custNumberTextField.requestFocus();
    }
    /**
     * Method to reset all values for entries to null and send focus to the first input textfield
     */
    public void resetButtonListener() {
		//clear all user entries  
		custNumberTextField.setText("");
		nameTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
		stateTextField.setText("");
		zipTextField.setText("");
		//send focus to the first input item
		custNumberTextField.requestFocus();
    }
    
    /**
     * Method to add a new customer to the database. First, the input information is validated to make sure that all needed items (especially
     * the customer number), are entered. Then the user can confirm whether or not they would like to add a customer before entering it into
     * the database.
     */
    public void addButtonListener() {
		//array to store values for inputs 
		String[] inputs = new String[6];
		//check that all fields are entered and store OR display an error message
		if(inputValidation(custNumberTextField).trim().length() == 0) {
			inputs[0] = custNumberTextField.getText();			
			if(inputValidation(nameTextField).trim().length() == 0) {
				inputs[1] = nameTextField.getText();
				if(inputValidation(addressTextField).trim().length() == 0) {
					inputs[2] = addressTextField.getText();
					if(inputValidation(cityTextField).trim().length() == 0) {
						inputs[3] = cityTextField.getText();
						if(inputValidation(stateTextField).trim().length() == 0) {
							inputs[4] = stateTextField.getText().toUpperCase();
							if(inputValidation(zipTextField).trim().length() == 0) {
								inputs[5] = zipTextField.getText(); 
								//ask user if they are sure they want to insert 
								int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to add Customer #" + inputs[0] + "?", 
										"KCDL Customer Portal", JOptionPane.YES_NO_OPTION);
								//insert the values if they say yes
								//define output message
								int newID = 0; //update checker
						    	String confirmation = "The customer with ID " + inputs[0] + " could not be added.";
								if(reply == JOptionPane.YES_OPTION) {
									newID = insertStatement(inputs);		    	
							    	//update confirmation statement
									if(newID != 0) {
										confirmation = "The customer with ID " + inputs[0] + " has been added.";
										resetButtonListener();
									}	
									//display note to user about whether or not the values have been added
									JOptionPane.showMessageDialog(null, confirmation, "KCDL Customer Portal", 1);
									custNumberTextField.requestFocus();
								} else JOptionPane.showMessageDialog(null, confirmation, "KCDL Customer Portal", 1);	
							} else JOptionPane.showMessageDialog(null, inputValidation(zipTextField), "KCDL Customer Portal", 1);  
						} else JOptionPane.showMessageDialog(null, inputValidation(stateTextField), "KCDL Customer Portal", 1);  						
					} else JOptionPane.showMessageDialog(null, inputValidation(cityTextField), "KCDL Customer Portal", 1);  
				} else JOptionPane.showMessageDialog(null, inputValidation(addressTextField), "KCDL Customer Portal", 1);  
			} else JOptionPane.showMessageDialog(null, inputValidation(nameTextField), "KCDL Customer Portal", 1);  					
		} else JOptionPane.showMessageDialog(null, inputValidation(custNumberTextField), "KCDL Customer Portal", 1);  		
    }
    /**
     * Method to validate input provided by user before inserting it into the database
     * @param field the textfield that is being inspected
     * @return a message indicating whether there is an error or null string value if there is no input error
     */
    public String inputValidation(TextField field) {
    	//check which field is empty/invalid and return a user message
    	String errMessage = "";    			
	    if(field.getText().isEmpty()) {
	    	//get the field that's empty
	    	int index = field.getId().indexOf("Text");
	    	String item = field.getId().substring(0,index);
	    	errMessage =  "The customer's " + item +" is empty";
	    	if(field.getId() == "custNumberTextField")
	    		errMessage = "The customer number cannot be empty";
	    	field.requestFocus();
	    }    
    	//check state and zipcode entries before extracting 
    	if (field.getId() == "stateTextField" && field.getText().length() > 2) {
    		errMessage =  "Please provide the correct two-letter state abbreviation";
    		field.requestFocus();
    	}
    	if(field.getId() == "zipTextField" && field.getText().length() > 5) {
    		errMessage = "Please only enter the first five digits of the zip code";
    		field.requestFocus();
    	}    	
    	return errMessage;
    }
    /**
     * Method to insert the customer into the database
     * @param custDetail an array including all values to be inserted into the database
     * @return the row count indicating the number of records that were inserted
     */
    public int insertStatement(String[] custDetail) {
    	//define database execution items
		int newID = 0;
    	final String DB_COFFEE_URL = "jdbc:mysql://localhost:3306/coffeeDB";
    	final String USERNAME = "root";
    	final String PASSWORD = "";
    	String query = "INSERT INTO customer VALUES ('"
    			+ custDetail[0] + "', '"	//id
    			+ custDetail[1] + "', '"	//name
    			+ custDetail[2] + "', '"	//address
    			+ custDetail[3] + "', '"	//city
    			+ custDetail[4].charAt(0) + custDetail[4].charAt(1) + "', "	//state 
    			+ custDetail[5].substring(0,5) + ")";		//zip code

    	try {
    		// Create a connection to the database.
    		Connection conn = DriverManager.getConnection(DB_COFFEE_URL, USERNAME, PASSWORD);
    		// Create statement for query execution
    		Statement stmt = conn.createStatement();
    		//get the number of items entered from running query
    		newID = stmt.executeUpdate(query);
    		//close the connection
    		conn.close();
    	}
    	catch (Exception ex) {
    		System.out.println("ERROR: " + ex.getMessage());
    	}    	
    	return newID;	
    }
}

