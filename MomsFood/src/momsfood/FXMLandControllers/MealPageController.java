package momsfood.FXMLandControllers;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import momsfood.classes.*;

public class MealPageController {

    @FXML
    private Label mealDescriptionLabel;

    @FXML
    private Label foodImageView;

    @FXML
    private Label mealCommentsLabel;

    @FXML
    private Button cartButton;

    @FXML
    private Label rateFoodLabel;

    @FXML
    private Label cookNameLabel;

    @FXML
    private Button searchFoodButton;

    @FXML
    private Label rateCookLabel;

    @FXML
    private Button profileButton;

    @FXML
    private Button buyButton;

    @FXML
    private Label foodLabel;
    
    @FXML
    private Label rateLabel;

    @FXML
    private Label priceLabel;    

    @FXML
    private Button logoButton;
    
    @FXML
    private Button logOutButton;
    
    @FXML
    private ComboBox<Integer> quantityComboBox;
    
    @FXML
    private ComboBox<Integer> cookRatingComboBox;
    
    @FXML
    private ComboBox<Integer> mealRatingComboBox;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label disclaimerLabel;

		//global variables
	    Double rating;
	    Double cookRating;
	    String mealDescription;  
	    int mealID;
	    int cookID;
	    Customer cust;
	    ArrayList<Integer> aList = new ArrayList<Integer>();
	    ObservableList<Integer> numbers = FXCollections.observableArrayList();
	    ObservableList<Integer> numbers2 = FXCollections.observableArrayList();
	    ObservableList<OrderLine> items = FXCollections.observableArrayList();
	    
	    /**
	     * Method to initialize the page
	     */
	    public void initialize()  {}
	    
	    /**
	     * Method to receive data from other pages/scenes
	     * @param mealID mealID of meal being selected
	     * @param cookID cookID of cook preparing the meal
	     * @param cust customer object with details of customer who is logged in
	     * @throws Exception
	     */
	    public void initData(int mealID, int cookID, Customer cust, ObservableList<OrderLine> list) throws Exception
	    {	    	
	    	this.mealID = mealID;	    	
	    	this.cookID = cookID;
	    	this.cust = cust;  
	    	//get current cart list
	    	if(list.size() != 0)
	    		this.items = list;
	    	OpenPage();
	    }
	    
	    /**
	     * Method to set initial values when the page loads
	     * @throws Exception
	     */
	    public void OpenPage() throws Exception {
	    
	    	Meal selectedMeal = new MealDAO().selectMealDetail(mealID);
	    	selectedMeal.setMealID(mealID);
	    	Cook cook = new CookDAO().selectCookProfile(cookID);
	    	//money
	    	DecimalFormat form = new DecimalFormat("$#,###.00");
	    	//rating
	    	DecimalFormat rate = new DecimalFormat("#.00");
	    	//Set the text fields by getting the attributes of the selected meal
	    	foodLabel.setText(selectedMeal.getMealName());	    	
	    	rateFoodLabel.setText(rate.format(selectedMeal.getRating())); 
	    	cookNameLabel.setText(selectedMeal.getCookName());	    	
	    	rateCookLabel.setText(rate.format(selectedMeal.getCookRating()));
	    	priceLabel.setText(form.format(selectedMeal.getUnitPrice()));	    	
	    	mealCommentsLabel.setText(selectedMeal.getMealCommentHistory());  
	    	ImageView pic = new MealDAO().getImage(mealID, 237, 186);
	    	foodImageView.setGraphic(pic);
	    	mealDescriptionLabel.setText(selectedMeal.getMealDescription());
	    	disclaimerLabel.setText("This specific cook is available only on " + cook.getAvailability());
	    		    	
	    	for(int i = 1; i<=10; i++)
		    {
	    		numbers.add(i);	    		
		    }	    	
	    	quantityComboBox.setItems(numbers);
	    	//set rating values
	    	for(int i = 1; i <= 5; i++) {
	    		numbers2.add(i);
	    	}
	    	cookRatingComboBox.setItems(numbers2);
	    	mealRatingComboBox.setItems(numbers2);	
	    	//check customer history for rating purposes
	    	addRatings();
	    }
	   
	    /**
	     * Method to return customer back to the search meals page
	     * @param e
	     * @throws IOException
	     */
	  public void searchButtonListener(ActionEvent e) throws IOException {
	    	
	    	{
	    		 try {
	    			 //Create new FXML loader
				    	FXMLLoader loader = new FXMLLoader();
				    	// specify the file location
				    	loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
				    	// the object representing the root node of the scene, load the UI
				    	Parent parent = loader.load(); 
						// set the scene
						Scene scene = new Scene(parent);    	
						//scene.getStylesheets().add("src/MyStyles.css");	
				    	CustomerController controller = loader.getController();	
				    	controller.iniData(cust, items);
				    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				    	// change the title of the window
				    	//stage.setTitle(sceneTitle);
				    	// set the scene for the stage  	
				    			
				    	stage.setScene(scene);
				    	// show the stage
				    	stage.show();}
					 catch(Exception ex)
	    		 {
						 System.out.print(ex.getMessage());	
	    		 }
	    	}
	  }
	    
	  /**
	   * Method to return customer to profile page
	   * @param e
	   * @throws IOException
	   */
	  public void profileButtonListener(ActionEvent e) throws IOException {
		 Scene scene = null;
 		 try {
		    	FXMLLoader loader = new FXMLLoader();
		    	// specify the file location
		    	loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
		    	// the object representing the root node of the scene, load the UI
		    	Parent parent = loader.load(); 
				// set the scene
				scene = new Scene(parent);    	
				//scene.getStylesheets().add("src/MyStyles.css");	
		    	CustomerProfileController controller = loader.getController();						    	
		    	controller.initData(cust, this.items);					    	
		    }
			 catch(Exception ex)
	 		{
				 System.out.print(ex.getMessage());	
	 		}
 		 Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
 		 // set the scene for the stage		
 		 stage.setScene(scene);
 		 // show the stage
 		 stage.show();	
	  }	    	
	  
	  /**
	   * Method to send customer to their cart
	   * @param e
	   * @throws IOException
	   */
	  public void cartButtonListener(ActionEvent e) throws IOException {
	    	     		
	    		try {
					    	FXMLLoader loader = new FXMLLoader();
					    	// specify the file location
					    	loader.setLocation(getClass().getResource("Checkout.fxml"));
					    	// the object representing the root node of the scene, load the UI
					    	Parent parent = loader.load(); 
							// set the scene
							Scene scene = new Scene(parent);    	
							//scene.getStylesheets().add("src/MyStyles.css");	
					    	OrderController controller = loader.getController();		
							//controller.receiveCurrentList(this.items);
					    	controller.initialize();
					    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					    	// change the title of the window
					    	// set the scene for the stage
		
					    	stage.setScene(scene);
					    	// show the stage
					    	stage.show();
					    }
						 catch(Exception ex)
	    				{
							 System.out.print(ex.getMessage());	 	
	    				}   	    	
	    		}	  
	  
	 
	  /**
	   * Method that allows customer to buy item which adds it to their cart
	   * @param e
	   * @throws IOException
	   */
	  	public void buyButtonListener(ActionEvent e) throws IOException {
	    	
	  		if(quantityComboBox.getValue() == null)
	  		{
	  			Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("Quantity");
		        alert.setContentText("Please select quantity");
		        alert.showAndWait();
	  		}
	  		
	  		else if(datePicker.getValue() == null){
	  			Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Mom's Food");
		        alert.setHeaderText("Delivery Date");
		        alert.setContentText("Please select a delivery date");
		        alert.showAndWait();
	  		}
	  		else {
	  			
	  			Integer quantity = quantityComboBox.getValue();
	
	  		try {
		    	FXMLLoader loader = new FXMLLoader();
		    	// specify the file location
		    	loader.setLocation(getClass().getResource("Checkout.fxml"));
		    	// the object representing the root node of the scene, load the UI
		    	Parent parent = loader.load(); 
				// set the scene
				Scene scene = new Scene(parent);    	
				//scene.getStylesheets().add("src/MyStyles.css");	
		    	OrderController controller = loader.getController();	
		    	//get the date
		    	Date date = Date.valueOf(datePicker.getValue());
		    	//get meal details
			    Meal meal = new MealDAO().selectMealDetail(mealID);			
			    meal.setMealID(mealID);
			    //create orderline
			    OrderLine anOrderLine= new OrderLine(meal.getMealName(), meal.getCookName() ,meal.getUnitPrice(), quantity); 
			    //add to the global orderline arraylist
			    items.add(anOrderLine);
		    	controller.initData(this.cust, items, date);
		    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    	// change the title of the window
		    	// set the scene for the stage		
		    	stage.setScene(scene);
		    	// show the stage
		    	stage.show();
		    }
			 catch(Exception ex)
	 		{
				 System.out.print(ex.getMessage());	
	 		}
	  	}
	  }
	 
	/**
	 * Method to add ratings to the cook and meal if a customer has made the order before
	 * @throws SQLException 
	 */
	 public void addRatings() throws SQLException {
		 //Alert for rating updates
		 Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Meal Feedback");
		 alert.setHeaderText("Meal and Cook Ratings");
		 //disable values unless customer qualifies
		 if(! new OrderDAO().checkCustomerHistory(this.cust.getUserID(), this.mealID)) {
		    	cookRatingComboBox.setVisible(false);
		    	mealRatingComboBox.setVisible(false);
		    	rateLabel.setVisible(false);
		 }
		 else {
			 	cookRatingComboBox.setVisible(true);
		    	mealRatingComboBox.setVisible(true);
		    	rateLabel.setVisible(true);
		    	//if rating is added, save it to the database and update current meal and cook average ratings
		    	if(cookRatingComboBox.getValue() != null) {
		    		if(new CookRatingDAO().addCookRating(cookID, this.cust.getUserID(), cookRatingComboBox.getValue())) {
		    			//update average rating
		    			if(new CookDAO().updateAverageRating(cookID))
		    				alert.setContentText("Thank you for rating this cook!");
		    		}		    			
		    		else
		    			alert.setContentText("Sorry, we couldn't get your feedback at this time. Please tell us again later.");
		    		alert.showAndWait();
		    	}
		    	if(mealRatingComboBox.getValue() != null) {
		    		if(new MealRatingDAO().addMealRating(mealID, this.cust.getUserID(), mealRatingComboBox.getValue())) {
		    			//update avaerage rating
		    			if(new MealDAO().updateAverageRating(mealID))
			    			alert.setContentText("Thank you for rating this cook!");
		    		}		    			
		    		else
		    			alert.setContentText("Sorry, we couldn't get your feedback at this time. Please tell us again later.");
		    		//notify user based on whether or not value was saved
		    		alert.showAndWait();
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
    	loader.setLocation(getClass().getResource("Homepage.fxml"));
    	//find the controller class and use the initData method
    	//set the scene/load UI
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		HomepageController controller = loader.getController();   
    	//controller.passCurrentOrderLine(items);
		controller.iniData(this.cust, items);
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
