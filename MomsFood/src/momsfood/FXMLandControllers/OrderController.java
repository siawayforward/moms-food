package momsfood.FXMLandControllers;

import java.io.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.*;
import java.util.*;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import momsfood.classes.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderController {

	 @FXML
	private Button discountButton;
	
    @FXML
    private Button profilButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button logoButton;

    @FXML
    private Button cartButton;

    @FXML
    private TableView<OrderLine> tableView;

    @FXML
    private Label subtotalLabel;

    @FXML
    private TableColumn<OrderLine, Double> subtotalColumn;

    @FXML
    private TableColumn<OrderLine, String> cookColumn;

    @FXML
    private Label taxLabel;

    @FXML
    private TableColumn<OrderLine, String> foodColumn;

    @FXML
    private Label totalLabel;
    
    @FXML
    private Button deleteItemButton;

    @FXML
    private TableColumn<OrderLine, Integer> quantityColumn;

    @FXML
    private Label discountLabel;

    @FXML
    private Button searchFoodButton;

    @FXML
    private TableColumn<OrderLine, Double> priceColumn;

    @FXML
    private Label deliveryLabel;
    @FXML
    private Button printReceiptButton;
    @FXML
    private DatePicker datePicker;
    
    String mealName;		
	String cookName;	
	
	//Create and initalize variables
	Order anOrder=new Order();
	Customer aCustm=new Customer();
	CookDAO objectOfCookDAO=new CookDAO();
	MealDAO objectOfMealDAO=new MealDAO();
	ObservableList<OrderLine> tableOfOrder=FXCollections.observableArrayList();
	Double FINAL_DELIVERY = 3.99;
	DecimalFormat form = new DecimalFormat("$#,###.00");
	String receipt = "";
	Date deliveryDate;		
	Alert alert = new Alert(AlertType.CONFIRMATION);		
	double subTotal=0;
	double discount = 0;
	double tax =0;
	double total = 0;

	/**
	 * Method to load new data onto page
	 * @param cust: customer object
	 * @param ol: the current orddr line
	 * @param deliveDate: expect delivery date
	 * @throws SQLException
	 */
	
	public  void initData(Customer cust, ObservableList<OrderLine> ol, Date deliveDate) throws SQLException
	{
		this.aCustm = cust;
		
		//set updated orderline list
		if(!ol.equals(null))
			this.tableOfOrder = ol;		
		this.deliveryDate = deliveDate;
		//Call open page method
		OpenPage();
	}
	
	/**
	 * Method to initialize
	 */
	public void initialize()	{}		
  
	/**
	 * method to open page and set scene items
	 * @throws SQLException
	 */
	public void OpenPage() throws SQLException {
		//table view columns
		foodColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("mealName")) ;
		cookColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("cookName")) ;
		priceColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Double>("price")) ;
		quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("quantity"));
		subtotalColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Double>("subTotal"));
		//add items to tableview
		tableView.setItems(tableOfOrder);
		//disable print button
	 	printReceiptButton.setDisable(true);
	 	//get calculated values
	 	subTotal = getSubtotal();     	 	
	 	tax = getTax();
	 	total = getTotal();
	 	//set calculated values
		subtotalLabel.setText(form.format(subTotal));
		discountLabel.setText(form.format(discount));
		deliveryLabel.setText(form.format(FINAL_DELIVERY));
		taxLabel.setText(form.format(tax));	
		totalLabel.setText(form.format(total));  
		//If there are no order, disable the submit button
		
	}
	
	/**
	 * Method to delete any unwanted items from the cart
	 */
	public void deleteItems()
	
	{//If table of order size is 0 show an error
		if(tableOfOrder.size() == 0)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Mom's Food");
			alert.setHeaderText("Error");
			alert.setContentText("Sorry, there are no orders to delete");						        
			alert.showAndWait();
		}
		else 
		{
			int selectedRow = tableView.getSelectionModel().getSelectedIndex();		
			// remove the row
			tableView.getItems().remove(selectedRow);	
		}		
	}
	
	/**
	 * Method to change from order page to the customer search page
	 * @param e
	 */
	public void changeToTheSearchPage(ActionEvent e) {
		// Create the scene as null
		Scene scene = null;
		try {
			// Create new loder object
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
				//Create new parent
				Parent parent=loader.load();
				//Create new scene
				scene=new Scene(parent);
				//Create new contoller
				CustomerController controller=loader.getController();
				//Call iniDtaa
				controller.iniData(this.aCustm, tableOfOrder);

			}
				///Catch exception errors and set scene and titles
		catch(Exception ex)
			{
				System.out.print("Error: " +ex.getMessage());
			}
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		//Set the scene and title
		stage.setScene(scene);
		stage.setTitle("Customer Search");
		stage.show();
	}
	
	/**
	 * Method to go back to the customer search page
	 * @param e
	 */
    public void backButtonListener(ActionEvent e)
    {// Create the scene as null
		Scene scene = null;
    	try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
			//Create new parent and scene
			Parent parent=loader.load();
			scene=new Scene(parent);
			//Create new contoller
			CustomerController controller=loader.getController();
			//call ini data method
			controller.iniData(this.aCustm, tableOfOrder);

		}
    	//Catch exception errors and set scene and titles
    	catch(Exception ex)
    	{
    		System.out.print("Error: " +ex.getMessage());
    	}
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Customer Search");
		stage.show();
    }
    	
	public void changeToProfilePage (ActionEvent e) {
		// Create the scene as null	
		Scene scene = null;
			
			try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
			//Create parent and set scene
			Parent parent=loader.load();
			scene=new Scene(parent);
			//Create new contoller
			CustomerProfileController controller=loader.getController();
			//call initdata method
			controller.initData(this.aCustm, this.tableOfOrder);
			
		}
			//Catch exception errors and set scene and titles
			catch(Exception ex) {
				System.out.print("Error: " +ex.getMessage());
			}
			Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Customer Profile");
			stage.show();
		}
	
	public void changeToOrderpage(ActionEvent e)
	{	// Create the scene as null
		Scene scene = null;
		try {
			//Create new Loader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Checkout.fxml"));
			//Create parent and set the scene
			Parent parent =loader.load();
			scene=new Scene(parent);
			//Create new contoller
			OrderController controller =loader.getController();
			controller.initData(aCustm, this.tableOfOrder, null);
		
		}
		//Catch exception errors and set scene and titles
		catch (Exception ex) {
			System.out.print("Error: " +ex.getMessage());
		}
			Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Check out Page");
			stage.show();
		
	}
		
		public void changeToHomePage(ActionEvent e) throws SQLException {
			// Create the scene as null
			Scene scene = null;
			try {
				//Create new FXML loader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("HomePage.fxml")); 
			//Create parent and set the scene
			Parent parent=loader.load();
			scene= new Scene(parent);
			//Create the new controller
			HomepageController controller=loader.getController();
			//call iniData object
			controller.iniData(this.aCustm, tableOfOrder);
			
			
			} //Catch exception errors and set scene and titles
			catch (IOException ex) {
				
				System.out.print(ex.getMessage());
			}		
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Home Page");
			stage.show();
		}
		
		public void discountButton()
		{		//If table of order is 0 show an error	
			if(tableOfOrder.size() == 0)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Mom's Food");
				alert.setHeaderText("Error");
				alert.setContentText("Sorry, there are no orders to apply a discount for");						        
				alert.showAndWait();
			}
			else
			{
				//Variable and constants to be used
				final double DISCOUNT_05=0.05;
				final double DISCOUNT_10=0.10;				
				String inputString = JOptionPane.showInputDialog("Please insert your discount code.");
				String discountCode= inputString;
				String firstPromo = "mis768";
				String secondPromo = "pizza";
				
				//If discount code = mis768
			if(discountCode.equals(firstPromo))
			{
				
				alert.setContentText("5% Discount has been added");
				
				discount = subTotal * DISCOUNT_05;
				total -= discount;
				
	    	 	subtotalLabel.setText(form.format(subTotal));
				discountLabel.setText(form.format(discount));
				deliveryLabel.setText(form.format(FINAL_DELIVERY));
				taxLabel.setText(form.format(tax));	
				totalLabel.setText(form.format(total));
				discountButton.setDisable(true);
				
			}
			//if discount code equals pizza
			else if (discountCode.equals(secondPromo))
			{
				alert.setContentText("10% Discount has been added");
				
				discount = subTotal * DISCOUNT_10;	
				total -= discount;
	    	 	subtotalLabel.setText(form.format(subTotal));
				discountLabel.setText(form.format(discount));
				deliveryLabel.setText(form.format(FINAL_DELIVERY));
				taxLabel.setText(form.format(tax));	
				totalLabel.setText(form.format(total));
				discountButton.setDisable(true);
			}
			
			else
			{//Else let the user know the discount does not exist
				alert.setContentText("Discount code not found");
				discount = 0.00;		
	    	 	subtotalLabel.setText(form.format(subTotal));
				discountLabel.setText(form.format(discount));
				deliveryLabel.setText(form.format(FINAL_DELIVERY));
				taxLabel.setText(form.format(tax));	
				totalLabel.setText(form.format(total));
			}		
		}
	}
		
	
		/**
		 * Method for submit button
		 */
		public void submitListener(ActionEvent e) throws ParseException 
		{	//if size of table is 0 show an error
			if(tableOfOrder.size() == 0)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Mom's Food");
				alert.setHeaderText("Error");
				alert.setContentText("Sorry, there are no orders to submit");						        
				alert.showAndWait();
			}
			else
		{		
				//Enable the print button
			printReceiptButton.setDisable(false);
			//Show an alert
			 Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Mom's Food");
			 alert.setHeaderText("Order Confirmation");
			//insertion flag
			 boolean flag = false;
			//insert the order into the database
			int orderKey = 0;
			//define order item
			SimpleDateFormat form = new SimpleDateFormat("MM/DD/YYYY");
			Date date = new java.util.Date();
			anOrder = new Order(form.format(date), form.format(this.deliveryDate), 1, 0, 0);
			orderKey = new OrderDAO().insertOrder(anOrder, this.aCustm.getUserID());
			//insert orderlines into the database
			if(orderKey != 0) {
				//insert orderlines and check if it worked
				for(int i = 0; i < tableOfOrder.size(); i++) {
					OrderLine item = tableOfOrder.get(i);
					if(new OrderDAO().insertOrderLine(orderKey, new MealDAO().getMealID(item.getMealName()), item.getQuantity()))
						flag = true;
				}
				//notify the user if order is received
				if(flag) {
					alert.setContentText("Your order has been confirmed!");						        
					alert.showAndWait();
					tableOfOrder.clear();
				}
				else {
					alert.setContentText("Sorry, some items on your order could not be processed. Please try again later");						        
					alert.showAndWait();
				}
			}
			else { //full order couldnt be entered
				alert.setContentText("Sorry, your order could not be processed. Please try again later");						        
				alert.showAndWait();
			}			 
			 //disable submit button after clicking
			 submitButton.setDisable(true);
		}
	}
		
		/**
		 * Method for the print button
		 * @param e
		 * @throws IOException
		 * @throws SQLException
		 */
		
		public void printReceiptListener(ActionEvent e) throws IOException, SQLException {	
			//String to ask user a question
			String fileName=JOptionPane.showInputDialog("Please select a name for your file.");
			//Create new scanner object
			Scanner in = new Scanner(System.in);
			if(!fileName.equals("")) {
				//get the file name if not null
				//fileName = in.nextLine();
				FileWriter fw = new FileWriter(fileName + ".rtf");
				PrintWriter receipt = new PrintWriter(fw); 
				//add receipt to file and close it
				receipt.write(this.toString());
				receipt.close();
				//show alert telling the user receipt is saved
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Mom's Food");
				alert.setHeaderText("Order Confirmation");
				alert.setContentText("Your receipt has been saved to the file");						        
				alert.showAndWait();
				 //clear the order and table view
				 tableOfOrder.clear();
				 tableView.setItems(tableOfOrder);
				 //redirect back to the 
				 changeToHomePage(e);
			}
			else {
				//show alert telling the user receipt is saved
				 Alert alert = new Alert(AlertType.WARNING);
				 alert.setTitle("Mom's Food");
				 alert.setHeaderText("Order Confirmation");
				 alert.setContentText("Please enter a filename to save your receipt");						        
				 alert.showAndWait();
			}	
			//Close the scanner
			in.close();
		}
		
		/**
		 * Method to get the subtotal
		 * @return
		 */
		public double getSubtotal()
		{		
			//Create for loop to add all the prices
			for(int i=0;i<tableView.getItems().size();i++) 
			{
				//Add price to subtotal
				subTotal=subTotal+tableView.getItems().get(i).calcSubtotal();
			}
			//return subtotal
			return subTotal;
		}
		
		/**
		 * Method to get tax
		 * @return
		 */
		
		public double getTax()
		{
			//Tax = subtotal * tax rate
			tax = subTotal *.0825;
			return tax;
		}
		
		/**
		 * Method to get the total
		 * @return
		 */
		public double getTotal()
		{
			total = subTotal + tax + FINAL_DELIVERY;
			return total;
		}
		
		/**
		 * Method to string for the receipt
		 */
		public String toString()
		{						
			receipt += "*******************************Thank you********************************\n";
			
		   	//current date
	    	SimpleDateFormat dates = new SimpleDateFormat("E, dd MMM yyyy");
			receipt += "Order Date: "+ dates.format(new Date()) + "\n";
			receipt += "Earliest Delivery Date: "+ this.deliveryDate + "\n";
			receipt += "First Name: "+aCustm.getFirstName() + "\n";
			receipt += "Last Name: "+aCustm.getLastName() + "\n\n";
			
			//add the meals and their cooks
			for(int i = 0; i < tableOfOrder.size(); i++) {
				OrderLine item = tableOfOrder.get(i);
				receipt +="Your order of " + item.getQuantity() + " "+ item.getMealName() + " will be prepared by "+ item.getCookName() + "\n";
			}
			//add dollar totals
			receipt += ("************************************************************************\n");
			receipt += ("Your receipt is as follows:"+"\n");
			receipt += ("Subtotal: "+"\t"+" "+ subtotalLabel.getText()) +"\n";
			receipt += ("Discount: "+"\t"+" "+ discountLabel.getText()) + "\n";
			receipt += ("Delivery: "+"\t"+" "+ deliveryLabel.getText()) +"\n";
			receipt += ("Tax: "+"\t"+"       "+ taxLabel.getText()) + "\n";
			receipt += ("Total: "+"\t"+" "+ totalLabel.getText())+"\n";
			receipt += ("************************************************************************");			
			return receipt;
		}
		
}
