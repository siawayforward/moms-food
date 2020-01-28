package edu.unlv.MIS768.Assignment_4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
/**
 * Joe's Automotive
 * Controller class that includes all methods for calculation of charges accrued with a customer visit to 
 * Joe's auto shop. Methods used are from the enum class Autojob and CustomerOrder class
 * Date: March 30, 2019
 * @author Sia Mbatia
 */

public class AutoJobReceiptController {

	    @FXML
	    private Pane autoPane;

	    @FXML
	    private Label radiatorFlushLabel;

	    @FXML
	    private RadioButton perc10RadioButton;
	    
	    @FXML
	    private RadioButton noDiscountRadioButton;

	    @FXML
	    private CheckBox oilChangeCheckBox;

	    @FXML
	    private RadioButton perc20RadioButton;

	    @FXML
	    private CheckBox lubeJobCheckBox;

	    @FXML
	    private Label transmissionFlushLabel;

	    @FXML
	    private CheckBox mufflerCheckBox;

	    @FXML
	    private CheckBox nonRoutineCheckBox;

	    @FXML
	    private CheckBox tireCheckBox;

	    @FXML
	    private Label nameLabel;

	    @FXML
	    private CheckBox radiatorCheckBox;

	    @FXML
	    private Label oilChangeLabel;

	    @FXML
	    private Label inspectionLabel;

	    @FXML
	    private CheckBox transmissionCheckBox;

	    @FXML
	    private Label subtotalLabel;

	    @FXML
	    private Label mufflerLabel;

	    @FXML
	    private Label dateLabel;

	    @FXML
	    private Label grandTotalLabel;

	    @FXML
	    private CheckBox inspectionCheckBox;

	    @FXML
	    private Label discountLabel;

	    @FXML
	    private Label lubeJobLabel;

	    @FXML
	    private Label tireRotationLabel;

	    @FXML
	    private Label nonroutineTotalLabel;
	    
	    @FXML
	    private Label partsChargeLabel;
	    
	    @FXML
	    private Label hoursDefLabel;

	    @FXML
	    private TextField hoursTextField;

	    @FXML
	    private Label hourChargeLabel;	    

	    @FXML
	    private TextField partFeesTextField;

	    @FXML
	    private Label partsDefLabel;
    
    public void initialize() {
    	//disable elements for non-routine services
    	partFeesTextField.setDisable(true);
    	hoursTextField.setDisable(true);
    	
    	//set all values to $0 and default discount
        noDiscountRadioButton.setSelected(true);
		oilChangeLabel.setText("$0.00");
  		lubeJobLabel.setText("$0.00");
		radiatorFlushLabel.setText("$0.00");
		transmissionFlushLabel.setText("$0.00");
		inspectionLabel.setText("$0.00");
		mufflerLabel.setText("$0.00");
		tireRotationLabel.setText("$0.00");
		partsChargeLabel.setText("$0.00");
		hourChargeLabel.setText("$0.00");
		nonroutineTotalLabel.setText("$0.00");
		
		//date
		SimpleDateFormat dates = new SimpleDateFormat("E, dd MMM yyyy");
		dateLabel.setText(dates.format(new Date()));
    }
    
    public void ServiceCheckListener() {
    	//initiate customer order
    	CustomerOrder cust = new CustomerOrder();
    	//output variables
    	double subtotal = 0, discount = 0, laborCost = 0;
    	//format options
    	DecimalFormat form = new DecimalFormat("$##.00");
    	//check if services are selected and i) add display on the label ii) set as ordered
    	if(oilChangeCheckBox.isSelected()) {
    		oilChangeLabel.setText(form.format(AutoJob.OIL_CHANGE.getPrice()));
    		cust.setOilChange(true);
    	}
    	else
    		oilChangeLabel.setText("$0.00");
    	if(lubeJobCheckBox.isSelected()) {
    		lubeJobLabel.setText(form.format(AutoJob.LUBE_JOB.getPrice()));
    		cust.setLubeJob(true);
    	}
    	else
    		lubeJobLabel.setText("$0.00");
    	if(radiatorCheckBox.isSelected()) {
    		radiatorFlushLabel.setText(form.format(AutoJob.RADIATOR_FLUSH.getPrice()));
    		cust.setRadiatorFlush(true);
    	}
    	else
    		radiatorFlushLabel.setText("$0.00");
    	if(transmissionCheckBox.isSelected()) {
    		transmissionFlushLabel.setText(form.format(AutoJob.TRANSMISSION_FLUSH.getPrice()));
    		cust.setTransmissionFlush(true);
    	}
    	else
    		transmissionFlushLabel.setText("$0.00");
    	if(inspectionCheckBox.isSelected()) {
    		inspectionLabel.setText(form.format(AutoJob.INSPECTION.getPrice()));
    		cust.setInspection(true);
    	}
    	else
    		inspectionLabel.setText("$0.00");
    	if(mufflerCheckBox.isSelected()) {
    		mufflerLabel.setText(form.format(AutoJob.MUFFLER_REPLACEMENT.getPrice()));
    		cust.setMufflerReplacement(true);
    	}
    	else
    		mufflerLabel.setText("$0.00");
    	if(tireCheckBox.isSelected()) {
    		tireRotationLabel.setText(form.format(AutoJob.TIRE_ROTATION.getPrice()));
    		cust.setTireRotation(true);
    	}
    	else
    		tireRotationLabel.setText("$0.00");
    	//non routine output 
    	if(nonRoutineCheckBox.isSelected()) {
    		//enable nodes for non routine services
        	partFeesTextField.setDisable(false);
        	hoursTextField.setDisable(false);
    		//define variables for hour fees and totals
    		final double HOUR_FEE = 20;   
    		//part fees
    		if(partFeesTextField.getText().trim().length() != 0) {
    			partsChargeLabel.setText(form.format(Double.parseDouble(partFeesTextField.getText())));
    			cust.setPartPrice(Double.parseDouble(partFeesTextField.getText()));
    		}     
    		else
    			partsChargeLabel.setText("$0.00");
    		//hour charge
    		if(hoursTextField.getText().trim().length() != 0) {
    			laborCost = Double.parseDouble(hoursTextField.getText()) * HOUR_FEE;
    			hourChargeLabel.setText(form.format(laborCost));
    			cust.setLaborPrice(laborCost);
    		}
    		else
    			hourChargeLabel.setText("$0.00");
    		//set total non-routine
    		nonroutineTotalLabel.setText(form.format(cust.getLaborPrice() + cust.getPartPrice()));
    	}
    	else {
    		nonroutineTotalLabel.setText("$0.00");
    		partsChargeLabel.setText("$0.00");
    		hourChargeLabel.setText("$0.00");
    		partFeesTextField.setText("");
    		hoursTextField.setText("");
        	partFeesTextField.setDisable(true);
        	hoursTextField.setDisable(true);
    	}
    	//get values before discounts are entered
    	cust.setDiscount(1);
    	subtotal = cust.getTotal();
    	subtotalLabel.setText(form.format(subtotal));
    	//discounts
    	if(perc10RadioButton.isSelected()) {
    		discount = subtotal * 0.10 * -1;
    		cust.setDiscount(.90);    		
    	}
    	else if(perc20RadioButton.isSelected()) {
    		discount = subtotal * 0.20 * -1;
    		cust.setDiscount(.80);
    	}	
    	//display discount
    	discountLabel.setText(form.format(discount));
    	//get and display the grandtotal
    	grandTotalLabel.setText(form.format(cust.getTotal()));    	
    }     
}

