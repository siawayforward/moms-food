package edu.unlv.MIS768.Assignment_5;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * Controller class for a conference registration application
 * @author Sia Mbatia
 * Date: April 8th 2019
 */
public class ConferenceRegistrationController {

    @FXML
    private CheckBox ecommerceCheckBox;

    @FXML
    private CheckBox advancedJavaCheckBox;

    @FXML
    private CheckBox dinnerCheckBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private CheckBox planCheckBox;

    @FXML
    private CheckBox networkCheckBox;

    @FXML
    private TextArea chargesTextArea;

    @FXML
    private CheckBox futureWebCheckBox;
    
    public void initialize() {
    	chargesTextArea.setText("");
    	nameTextField.setText("");
    	nameTextField.requestFocus();
    }
    
    /**
     * Method to get user selections for conference attendance events, registration type (general or student),
     * and whether or not they would like to attend the opening dinner and keynote speech. This method then
     * uses the ConfRegistration class objects to store this information and calculate and display the total
     * for the registration fees.
     */
    public void checkBoxListener() {
    	//initialize a registration event
    	ConfRegistration event = new ConfRegistration();
    	
    	//get user selections
    	event.setOpeningDinner(dinnerCheckBox.isSelected());
    	event.seteCommerce(ecommerceCheckBox.isSelected());
    	event.setFutureOfWeb(futureWebCheckBox.isSelected());
    	event.setAdvancedJava(advancedJavaCheckBox.isSelected());
    	event.setNetworkSecurity(networkCheckBox.isSelected());
    	
    	//calculate total and display items
    	DecimalFormat form = new DecimalFormat("$#,###.00");
        //date
    	SimpleDateFormat dates = new SimpleDateFormat("E, dd MMM yyyy");
    	String charges = "";
    	charges += "Conference Charges: " + " \t \t " + dates.format(new Date()) + "\n";
        charges += "Name: \t" + nameTextField.getText() + "\n"; 
        if(planCheckBox.isSelected())
        	charges += "Admission Type: \tStudent \n";
        else
        	charges += "Admission Type: \tGeneral \n";
    	charges += "Total Charge: \t" + form.format(event.getTotal(planCheckBox.isSelected()));
    	//display items
    	chargesTextArea.setText(charges);
    }
    
    /**
     * Method to reset all checkboxes to unchecked and charges area to empty when a user wants to restart a registration
     */
    public void buttonListener() {
    	//reset all items to empty/unchecked
    	initialize();
    	planCheckBox.setSelected(false);
    	dinnerCheckBox.setSelected(false);
    	ecommerceCheckBox.setSelected(false);
    	futureWebCheckBox.setSelected(false);
    	advancedJavaCheckBox.setSelected(false);
    	networkCheckBox.setSelected(false);
    }
}

