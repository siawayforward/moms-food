package momsfood.FXMLandControllers;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import momsfood.classes.*;

public class CookAddNewFoodController {

    @FXML
    private ComboBox<String> addFoodCountryComboBox;

    @FXML
    private Button addPictureButton;

    @FXML
    private TextField addPriceTextField;

    @FXML
    private Button logOutButton;

    @FXML
    private Button logoButton;  

    @FXML
    private Button backButton;  

    @FXML
    private Button addFoodButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label usernameLabel;    

    @FXML
    private Label countryLabel;
    
    @FXML
    private Label ordersLabel;

    @FXML
    private TextField addFoodNameTextField;

    @FXML
    private ComboBox<String> addCategoryComboBox;
    
    @FXML
    private Label imagePromptLabel;
    
	//global variables for cook data
	OrderDAO load = new OrderDAO();
	int cookID;
	String firstName;
	String cookCountry;
	
	/**
	 * method to get data from log in scene
	 * @param ID :Unique ID for login
	 * @param fName : first name
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
    public void initData(int ID, String fName, String country) throws FileNotFoundException, SQLException {
    	this.cookID = ID;
    	this.firstName = fName;
    	this.cookCountry = country;
    	openPage();
    }
    
    /**
     * Method to open and load the page
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void openPage() throws SQLException, FileNotFoundException {
		//load countries for selection on comboBox
		//get countries and save into list from database
		ObservableList<String> countries = new CountryDAO().getCountryNames();
		//load the combo box with the countries
		addFoodCountryComboBox.setItems(countries);
		addFoodCountryComboBox.setMaxWidth(Double.MAX_VALUE);
		//load food categories from file
		File file = new File("src/FoodCategories.txt");
		Scanner input = new Scanner(file);
		ObservableList<String> categories = FXCollections.observableArrayList();
		while(input.hasNext())	
			categories.add(input.next());
		addCategoryComboBox.setItems(categories);
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
     * Method to initialize 
     */
	public void initialize() {}
	
	/**
	 * Method to add the meal item to the database after retrieving user inputs and validating them
	 * @throws SQLException 
	 */
	public void addItem() throws SQLException {
		//insertion values
		String mealName="", mealDesc = "", country="", mealCategory="";
		double price = 0;
		
		boolean flag = false; //check for errors
		//validate input values
		Alert alert = new Alert(AlertType.CONFIRMATION);
		String message = "Please fill out the missing values";
	
		if(addFoodNameTextField.getText().equals("") || addCategoryComboBox.getValue().equals("") || addFoodCountryComboBox.getValue().equals("") ||
				addPriceTextField.getText().equals("") || addPriceTextField.getText().equals("") || descriptionTextArea.getText().equals("")) {
			flag = true;
			alert.setContentText(message);
		}
		else {
			mealName = addFoodNameTextField.getText();
			mealDesc = descriptionTextArea.getText();
			country = addFoodCountryComboBox.getValue();
			mealCategory = addCategoryComboBox.getValue();
			//check price entry for digits
			try {
				price = Double.parseDouble(addPriceTextField.getText());
			} catch (NumberFormatException e) {
				message = "Please enter a valid price for this meal item";
				alert.setContentText(message);
				flag = true;
			}	
		}
		
		//check whether or not we can insert values	
		if(flag)
			alert.showAndWait();
		else {
			//insert cook into database
			if(new MealDAO().addMeal(mealName, cookID, mealDesc, price, mealCategory, country, 0, this.pictureName)) {
				message = "The meal has been added!";
				//cook's orders
				ordersLabel.setText(load.getCookOrders(this.cookID) + " orders; " + load.getSpecialRequests(this.cookID) + " requests");
				resetPage();
			}
			else
				message = "The new meal could not be added. Please try again later";
			//display appropriate message
			alert.setContentText(message);
			alert.showAndWait();
		}
	}
	
	/**
	 * Method to go back to the profile section
	 * @throws Exception 
	 */
	public void updateMeals(ActionEvent e) throws Exception {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("CookUpdateFood.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		CookUpdateFoodController controller = loader.getController();   
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
	 * Method to reset all controls to empty or null 
	 */
	void resetPage() {
		//reset all input fields
		addFoodNameTextField.setText("");
		addCategoryComboBox.setValue("");
		addFoodCountryComboBox.setValue("");
		addPriceTextField.setText("");
		imagePromptLabel.setText("");
		descriptionTextArea.deleteText(0, descriptionTextArea.getLength());
		addFoodNameTextField.requestFocus();
	}
	
	String pictureName = "";
	/**
	 * Method to retrieve picture from local computer of the user before saving to database
	 * @return the binary large object form of the picture
	 */
	public void retrievePicture() {
		String mealName = "";
		if(addFoodNameTextField.getText().trim().length() == 0) 
			imagePromptLabel.setText("Please enter a meal name for this image");
		else {
			mealName = addFoodNameTextField.getText();		
			FileChooser chooser = new FileChooser();
			File file = chooser.showOpenDialog(null);	
			//get the original file path
			Path temp = null;
			if(file != null) {				
				try {
					temp = Files.move 
					        (Paths.get(file.getPath()),  
					        Paths.get("src/mealImages/"+ mealName + "." + file.getName().substring(file.getName().length()-3)));
				} catch (IOException e) {
					imagePromptLabel.setText("Could not retrieve image at this time");
				} 
				if(temp != null) {
					//get the picture name
					this.pictureName =  mealName + "." + file.getName().substring(file.getName().length()-3);
					imagePromptLabel.setText("Image retrieved!");
				}
				else
					imagePromptLabel.setText("Please select an image to upload");	
			}
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
		controller.initialize();
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
		cook.setUserID(this.cookID);
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
