package momsfood.FXMLandControllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import momsfood.classes.*;


public class CookUpdateFoodController {
	
	@FXML
    private TextField editPriceTextField;

    @FXML
    private ComboBox<String> editCategoryComboBox;    

    @FXML
    private ComboBox<String> editFoodCountryComboBox;

    @FXML
    private TableView<Meal> tableView;    

    @FXML
    private TableColumn<Meal, Integer> IDColumn;
    
    @FXML
    private TableColumn<Meal, String> categoryColumn;

    @FXML
    private TableColumn<Meal, String> namecolumn;    

    @FXML
    private TableColumn<Meal, String> countryColumn;    

    @FXML
    private TableColumn<Meal, String> priceColumn;

    @FXML
    private TableColumn<Meal, String> descriptionColumn;

    @FXML
    private Button editPictureButton;

    @FXML
    private TextField editFoodNameTextField;

    @FXML
    private Button deleteFoodButton;

    @FXML
    private Button editFoodButton;    

    @FXML
    private Button logoButton;  

    @FXML
    private Button logOutButton;  

    @FXML
    private Button backButton;  

    @FXML
    private Label usernameLabel;    

    @FXML
    private Label countryLabel;
    
    @FXML
    private Label ordersLabel;
    
    @FXML
    private Label imagePromptLabel;

    @FXML
    private TextArea descriptionTextArea;
    
    //global variables across methods
    int selectedMealID = 0;
    int cookID;
    String firstName;
    String cookCountry;
    Meal selected;
	ObservableList<Meal> meals = null;
	OrderDAO load = new OrderDAO();

	//method to get data from log in scene
    public void initData(int ID, String fName, String country) throws SQLException, Exception {
    	this.cookID = ID;
    	this.firstName = fName;
    	this.cookCountry = country;
    	openPage();
    }
    
    private void openPage() throws SQLException, Exception {
    	//disable meal name
    			editFoodNameTextField.setDisable(true);
    			//set up table view
    			IDColumn.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("mealID"));
    			namecolumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("mealName"));
    			descriptionColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("mealDescription"));
    			categoryColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("foodCategory"));
    			countryColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("Country"));
    			priceColumn.setCellValueFactory(new PropertyValueFactory<Meal, String>("unitPrice"));
    			//select all meals by this cook
    			meals = new MealDAO().getCookMeals(cookID);
    			//display them to the tableview and assign selected meal
    			tableView.setItems(meals);	
    			//set cook username, country and number of orders once they log into their profile
    			usernameLabel.setText("Hello, " + this.firstName + "!");
    			//orders
    			int orders = new OrderDAO().getCookOrders(cookID);
    			ordersLabel.setText(orders + " active orders");
    			//load the country combo box items
    			//get countries and save into list from database
    			ObservableList<String> countries = new CountryDAO().getCountryNames();
    			//load the combo box with the countries
    			editFoodCountryComboBox.setItems(countries);
    			editFoodCountryComboBox.setMaxWidth(Double.MAX_VALUE);
    			//load food categories from file
    			File file = new File("src/FoodCategories.txt");
    			Scanner input = new Scanner(file);
    			ObservableList<String> categories = FXCollections.observableArrayList();
    			while(input.hasNext())	
    				categories.add(input.next());
    			editCategoryComboBox.setItems(categories);
    			input.close();		
    			//cook's username
    			usernameLabel.setText("Hello, " + this.firstName + "!");
    			//cook's orders
    			ordersLabel.setText(load.getCookOrders(this.cookID) + " orders; " + load.getSpecialRequests(this.cookID) + " requests");
    			//cook's country
    			ImageView coun = new ImageView(new CountryDAO().getCountryFlag(this.cookCountry));
    			countryLabel.setGraphic(coun);
    			
	}
	/**
     * Values to load and display when the scene opens
     * @throws Exception
     */
	public void initialize() {}
	
	/**
	 * Delete a selected meal item
	 * @throws SQLException 
	 */
	public void deleteItem() throws SQLException {
		//define selected row
		int selectedRow = tableView.getSelectionModel().getSelectedIndex();
		
		if(selectedRow != -1)
		{
			//check with user if they really want to delete the item
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Are you sure you would like to delete this item from your offered items?");		
			alert.showAndWait();	
			if (alert.getResult().getText().toLowerCase().equals("ok")) {
				//delete item
				if(new MealDAO().deleteMeal(tableView.getSelectionModel().getSelectedItem().getMealID())) {
					tableView.getItems().remove(selectedRow);
					alert.setContentText("Item deleted");
					//cook's orders
					ordersLabel.setText(load.getCookOrders(this.cookID) + " orders; " + load.getSpecialRequests(this.cookID) + " requests");
					resetPage();
				}			
			
				else
					alert.setContentText("Item could not be deleted. Please try again later");
				alert.showAndWait();
			}			
		}
		else
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);			
			alert.setContentText("There are no items to be deleted at this point");
			alert.showAndWait();
		}
	}
	
	/**
	 * Method to go back to the profile section
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void addFood(ActionEvent e) throws IOException, SQLException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CookAddNewFood.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new Cook profile Controller instance
		CookAddNewFoodController controller = loader.getController(); 
		controller.initData(this.cookID, this.firstName, this.cookCountry);
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
	 * Update a meal item that is being selected
	 * @throws Exception
	 */
	public void updateItem() throws Exception {
		//confirmation message
		selected = tableView.getSelectionModel().getSelectedItem();
		
		if(selected != null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("The item could not be updated");
			//get meal data from detail viewed item and update database
			selected.setCountry(editFoodCountryComboBox.getValue());
			selected.setFoodCategory(editCategoryComboBox.getValue());
			//price
			int index = editPriceTextField.getText().indexOf('$');
			selected.setUnitPrice(Double.parseDouble(editPriceTextField.getText().substring(index + 1)));
			selected.setMealDescription(descriptionTextArea.getText());
			//run update and display message based on action completed
			if(new MealDAO().updateMeal(selected)) {
				meals = new MealDAO().getCookMeals(cookID);
				alert.setContentText("Meal item " + selected.getMealName() + " has been updated.");
				//select updated meals by this cook
				meals = new MealDAO().getCookMeals(cookID);
				//display them to the tableview and assign selected meal
				tableView.setItems(meals);			
			}
				alert.showAndWait();	
				resetPage();
		}
		else 
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Please select a meal to update");
			alert.showAndWait();	
		}
	}
	
	/**
	 * ResetPage method to set everything as null
	 */
	private void resetPage() {
		//empty all fields and set focus to name field
		editFoodNameTextField.setText("");
		editCategoryComboBox.setValue("");
		editFoodCountryComboBox.setValue("");
		editPriceTextField.setText("");
		descriptionTextArea.setText("");
		editFoodNameTextField.requestFocus();
		imagePromptLabel.setText("");
	}
	
	/**
	 * Get picture to upload for the meal update
	 * @throws SQLException
	 */
	public void retrievePicture() throws SQLException {
		String mealName = "";
		if(editFoodNameTextField.getText().trim().length() == 0) 
			imagePromptLabel.setText("Please enter a meal name for this image");
		else {
			mealName = editFoodNameTextField.getText();		
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(null);	
			//get the original file path
			Path temp = null;
			if(file != null) {				
				try { //set new and old file paths
					temp = Files.move 
					        (Paths.get(file.getPath()),  
					        Paths.get("src/mealImages/"+ mealName + "." + file.getName().substring(file.getName().length()-3)));
				} catch (IOException e) {
					imagePromptLabel.setText("Could not retrieve image at this time");
				} 
				if(temp != null)  //if new file path is retrieved and image is moved
				{ //notify user
					imagePromptLabel.setText("Image retrieved!");
					selected.setPictureName(mealName + "." + file.getName().substring(file.getName().length()-3));
				}
					
				else //notify user if not retrieved or name of meal is not entered or image isnt selected
					imagePromptLabel.setText("Please select an image to upload");	
			}
		}	
	}
	
	/**
	 * Method to display meal details on the boxes to update
	 */
	public void displayMealDetails() {	
		selected = tableView.getSelectionModel().getSelectedItem();
		//display the items on the controls *except picture
		try{
			if(!selected.getMealName().equals("")) {
				editFoodNameTextField.setText(selected.getMealName());
				editCategoryComboBox.setValue(selected.getFoodCategory());
				editFoodCountryComboBox.setValue(selected.getCountry());
				DecimalFormat form = new DecimalFormat("$##.00");
				editPriceTextField.setText(form.format(selected.getUnitPrice()));
				descriptionTextArea.setText(selected.getMealDescription());
			}	
		}
		catch(NullPointerException e) {
			System.out.print("");
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
	/**
    * Method to move back to the home page. 
	 * @throws IOException 
	 * @throws SQLException 
    */
	public void goToHomePage(ActionEvent e) throws IOException, SQLException {		
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CookProfile.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CookProfileController controller = loader.getController();  
		Cook cook = new CookDAO().selectCookProfile(this.cookID);
		cook.setUserID(cookID);
		controller.initData(cook);
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
