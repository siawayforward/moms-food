package momsfood.FXMLandControllers;


import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import momsfood.classes.*;


public class CustomerController {


	  @FXML
	    private Button profilButton;

	    @FXML
	    private Button logoButton;

	    @FXML
	    private Button cartButton;
	    
	    @FXML
	    private Button logOutButton;

	    @FXML
	    private ComboBox<String> foodCountryComboBox;

	    @FXML
	    private TableColumn<Meal, String> countryColumn;

	    @FXML
	    private Button searchFoodButton1;

	    @FXML
	    private TextField nameTextfield;

	    @FXML
	    private ToggleGroup unit;	    

	    @FXML
	    private TableView<Meal> tableView;

	    @FXML
	    private ComboBox<String> categoryComboBox;

	    @FXML
	    private RadioButton cookNameRadioButton;

	    @FXML
	    private RadioButton mealNameRadioButton;

	    @FXML
	    private Button searchFoodButton;

	    @FXML
	    private TableColumn<Meal, String> mealNameColumn;

	    @FXML
	    private TableColumn<Meal, Double> mealRateColumn;

	    @FXML
	    private TableColumn<Meal, String> pictureColumn;

	    @FXML
	    private Button seeDetailButton;
	    
	    @FXML
	    private Button clearButton;
	    
	    //Create and initialize varibales to be used in class
		Alert alert = new Alert(AlertType.ERROR);
		int mealID;
		Customer aCustomer;
		ObservableList<Meal> observe;
		String inputName;		
	    ObservableList<OrderLine> items = FXCollections.observableArrayList();
	    
		//method to receive customer from other pages
		public void  iniData(Customer Customer, ObservableList<OrderLine> list)
		{
			this.aCustomer=Customer;				
			//pass current cart items
	    	if(list.size() != 0)
	    		this.items = list;
	    	//Call openPgae method
			openPage();
		}
		
		/**
		 * Initialize method
		 */
		public void initialize() {}
	    
		/**
		 * OpenPage Method used to load everything on to page when it first opens
		 */
	public void openPage() {
		//set table view columns
		mealNameColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("mealName")) ;
		mealRateColumn.setCellValueFactory(new PropertyValueFactory<Meal, Double>("rating")) ;
		pictureColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("foodCategory")) ;
		countryColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Country"));

		try {
			//Create new file object
			File file = new File("src/FoodCategories.txt");
			//Create new Scanner object
			Scanner input = new Scanner(file);
			//Create new observable list named categories
			ObservableList<String> categories = FXCollections.observableArrayList();
			//While the input has next, add it to the observable list
			while(input.hasNext())	
				categories.add(input.next());
			//Set the categories into a combo box
			categoryComboBox.setItems(categories);
			//Close the scanner
			input.close();	
			//create new observable list countries
			ObservableList<String> countries = new CountryDAO().getCountryNames();
			//load the combo box with the countries
			foodCountryComboBox.setItems(countries);
			
		}
		//Catch exception and show error
		catch(IOException e)
		{
			System.out.print("Error "+e.getMessage());
		}	
}
	/**
	 * Method used to clear the data
	 */
	public void clearButton()
	{
		nameTextfield.setText("");
		categoryComboBox.setValue(null);
		foodCountryComboBox.setValue(null);		
		categoryComboBox.setDisable(false);
		foodCountryComboBox.setDisable(false);	
		tableView.getItems().clear();		
		mealNameRadioButton.setDisable(false);
		cookNameRadioButton.setDisable(false);		
		cookNameRadioButton.setSelected(false);	
		mealNameRadioButton.setSelected(false);
	}
	
	/**
	 * SearchListener method to adjust the search input boxes based on search parameter selected
	 * @throws SQLException
	 */
	public void SearchListener() throws SQLException {
		//initialize variables
		MealDAO searchMeal=new MealDAO();
		List<Meal> mealList=new ArrayList<Meal>();
		inputName=nameTextfield.getText();
		
		//return meals based on specific cook and meal name search
		if (cookNameRadioButton.isSelected()||mealNameRadioButton.isSelected())
		{
			//validate search input
			 if(inputName.length() ==0)
			{
				alert.setContentText("You need to select type of selection.(Cook name/Meal name)");
			}
			 else //if search term is entered
			 {	//If cook name is selected
				 if(cookNameRadioButton.isSelected()) {
						//run query and get and display results for cook
						mealList= searchMeal.searchByCookName(inputName);
						observe=FXCollections.observableArrayList(mealList);
				 		tableView.setItems(observe);
				 }
				 //If meal name is selected
				 if(mealNameRadioButton.isSelected()) {
					 //run query and get and display results for cook
					 mealList=searchMeal.searchByMealName(inputName);
					 observe=FXCollections.observableArrayList(mealList);
					 tableView.setItems(observe);
				 }
			 }
		} //combo box searches
		else if(categoryComboBox.getSelectionModel().getSelectedIndex()!=-1 || foodCountryComboBox.getSelectionModel().getSelectedIndex()!=-1)
		{	
			//if category is search parameter
		   if(categoryComboBox.getSelectionModel().getSelectedIndex()!=-1)
			{ //run query and get and display results for cook
			   String selectionCategory=categoryComboBox.getValue();
				mealList= searchMeal.searchByFoodCategory(selectionCategory);
				observe=FXCollections.observableArrayList(mealList);
		 		tableView.setItems(observe);	 		
			} //if food country is search parameter
	 		else if(foodCountryComboBox.getSelectionModel().getSelectedIndex()!=-1)
			{ //run query and get and display results for cook
				String selectionCountry=foodCountryComboBox.getValue();
				mealList= searchMeal.country(selectionCountry);
				observe=FXCollections.observableArrayList(mealList);
		 		tableView.setItems(observe);
			}	
		}	
	}
	
	/**
	 * Method to check what search parameter input node is occupied and disable the others
	 */
	public void checkCondition() {
	try{
		//disable all others when cook name is search parameter
		if (cookNameRadioButton.isSelected())
		{
		categoryComboBox.setDisable(true);
		foodCountryComboBox.setDisable(true);
		
		}
		//disable all others when meal name is search parameter
	 if(mealNameRadioButton.isSelected())
		{	
		categoryComboBox.setDisable(true);
		foodCountryComboBox.setDisable(true);
		
		}
	//disable all others when meal category is search parameter
	 if(!(categoryComboBox.getValue().equals(null)))
		{
			mealNameRadioButton.setDisable(true);
			cookNameRadioButton.setDisable(true);
			foodCountryComboBox.setDisable(true);			
			
		}
	 //disable all others when meal country is search parameter 
	 if(!(foodCountryComboBox.getValue().equals(null)))
		{
			mealNameRadioButton.setDisable(true);
			cookNameRadioButton.setDisable(true);
			categoryComboBox.setDisable(true);
		
		}
		
		else //if nothing is selected, display error message
		{
			alert.setContentText("Pick a name or category or country.");
		}		
	} catch(Exception e){
		System.out.println("");
	}
	}
	
	/**
	 * Method to allow for meal details to display when a specific meal in the search results is selected
	 * @param e action event listener
	 */
	public void DetailListener (ActionEvent e)
	{
		//selected object
		Meal selectedRow = tableView.getSelectionModel().getSelectedItem();	
	    mealID=selectedRow.getMealID();
	    int cookID = new MealDAO().GetCookID(mealID);	 //define cookID of meal         
	    
		Scene scene = null;
		try {
	    	//FXML Loader defined
	    	FXMLLoader loader = new FXMLLoader();
	    	//set the scene to load
	    	loader.setLocation(getClass().getResource("Meal.fxml"));
	    	//find the controller class and use the initData method
	    	//set the scene/load UI
	    	Parent parent = loader.load();
			// set the scene
			scene = new Scene(parent);
	    	//new MealPage controller instance
			MealPageController controller = loader.getController();
			controller.initData(mealID, cookID, aCustomer, items);
		}
		catch(Exception ex) //if scene and stage wont load
		{
			System.out.print("Error: " +ex.getMessage());		
		}
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Meal Page");
		//show stage
		stage.show();
	}
	
	/**
	 * Method to move from customer search page and refresh back to the same page
	 * @param e
	 */
	public void changeToTheSearchPage(ActionEvent e) {
		Scene scene = null;
		try {
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
				
				Parent parent=loader.load();
				scene=new Scene(parent);

				CustomerController controller=loader.getController();
				controller.iniData(this.aCustomer, items);

			}
				
		catch(Exception ex)
		{
			System.out.print("Error: " +ex.getMessage());
		}
		//controller.passCurrentOrderLine(this.items);
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Customer Search");
		stage.show();
	}
	
	/**
	 * Method to move from search page to customer profile page
	 * @param e
	 */
	public void changeToProfilePage (ActionEvent e) {
		Scene scene = null;
		try {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
		Parent parent=loader.load();
		scene=new Scene(parent);
		CustomerProfileController controller=loader.getController();
		controller.initData(aCustomer, this.items);		
		}
		catch(Exception ex) {
			System.out.print("Error: " +ex.getMessage());
		}
		
		//controller.passCurrentOrderLine(this.items);		
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Customer Profile");
		stage.show();
	}
	
	/**
	 * Method to move from customer search page to cart
	 * @param e
	 */
	public void changeToOrderpage(ActionEvent e)
	{
		Scene scene = null;
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Checkout.fxml"));
		    Parent parent =loader.load();
			scene=new Scene(parent);

			OrderController controller =loader.getController();
			controller.initData(aCustomer, items, new Date());
			//controller.receiveCurrentList(this.items);
		
		}
		catch (Exception ex) {
			System.out.print("Error: " +ex.getMessage());
		}
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Check out Page");
		stage.show();	
	}
	
	/**
	 * Method to move to the home page from the search page
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void changeToHomePage(ActionEvent e) throws ClassNotFoundException, SQLException {
		
		try {
			//Create new Loader object
		FXMLLoader loader=new FXMLLoader();
		//Set the location of the page to open
		loader.setLocation(getClass().getResource("HomePage.fxml"));
		//Create parent object
		Parent parent=loader.load();
		//Create scene object
		Scene scene= new Scene(parent);
		//Create new homepage controller
		HomepageController controller=loader.getController();
		//Call iniData controller
		controller.iniData(aCustomer, items);
		Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
		//Set the scene
		stage.setScene(scene);
		//Set the title and show
		stage.setTitle("Home Page");
		stage.show();
		
	}// Catch exception error
		catch (IOException e1) {
		
		System.out.print(e1.getMessage());
	}
		
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
}
