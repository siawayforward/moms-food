package momsfood.FXMLandControllers;


import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import momsfood.classes.*;

public class HomepageController {
	

    @FXML
    private VBox vBox3;

    @FXML
    private VBox vBox4;

    @FXML
    private Button logOutButton;
    
    @FXML
    private Label rate3Label;

    @FXML
    private Label price5Label;

    @FXML
    private VBox vBox2;

    @FXML
    private Label nameFood6Label;

    @FXML
    private Button cartButton;

    @FXML
    private Label price2Label;

    @FXML
    private Label food6ImageView;

    @FXML
    private Label nameFood3Label;

    @FXML
    private Label food3ImageView;

    @FXML
    private Button seeMoreFood2Button;

    @FXML
    private Button seeMoreFood4Button;
    
    @FXML
    private Button seeMoreFood6Button;

    @FXML
    private Label nameFood1Label;

    @FXML
    private Label rate5Label;

    @FXML
    private Label nameFood4Label;

    @FXML
    private Label food2ImageView;

    @FXML
    private Label food5ImageView;

    @FXML
    private Label cook6Label;

    @FXML
    private Button searchFoodButton;

    @FXML
    private Label rate4Label;

    @FXML
    private Label rate1Label;

    @FXML
    private Label cook3Label;

    @FXML
    private Label cook4Label;

    @FXML
    private Label cook1Label;

    @FXML
    private Button profilButton;

    @FXML
    private Button seeMoreFood3Button;

    @FXML
    private Button logoButton;

    @FXML
    private VBox VBox1;

    @FXML
    private Label price6Label;

    @FXML
    private Button seeMoreFood5Button;

    @FXML
    private Label price3Label;

    @FXML
    private Label rate2Label;

    @FXML
    private Button seeMoreFood1Button;

    @FXML
    private HBox HboxHeader;

    @FXML
    private Label cook2Label;

    @FXML
    private Label nameFood5Label;

    @FXML
    private Label food1ImageView;

    @FXML
    private Label cook5Label;

    @FXML
    private Label nameFood2Label;

    @FXML
    private Label food4ImageView;

    @FXML
    private Label price1Label;

    @FXML
    private Label rate6Label;

    @FXML
    private VBox vBox5;

    @FXML
    private ImageView homePageButton;

    @FXML
    private Label price4Label;

    @FXML
    private VBox vBox6;

    @FXML
    private ImageView image;
    
    
    //Global variables
    MealDAO aMeal=new MealDAO();
    Meal meal1;
    Meal meal2;
    Meal meal3;
    Meal meal4;
    Meal meal5;
    Meal meal6;
    Customer aCus;
    ObservableList<OrderLine> items = FXCollections.observableArrayList();
  
    
    /**
     * Method to receive data from other pages
     * @param aCustomer
     */
    public void iniData(Customer aCustomer, ObservableList<OrderLine> list)
    {
    	this.aCus=aCustomer;
    	//add items to the list if newer and not empty
    	if(list.size() != 0)
    		this.items = list;
    }
    
    /**
     * Initializing the page and setting values
     */
	public void initialize() {
		
		//create an object or the meal class and display	
		meal1 = aMeal.SelectFood("1");
		setValue(nameFood1Label,price1Label, cook1Label, rate1Label , food1ImageView, meal1);		
		meal2 = aMeal.SelectFood("1,1");
		setValue(nameFood2Label,price2Label, cook2Label, rate2Label , food2ImageView, meal2);	
		meal3 = aMeal.SelectFood("2,1");
        setValue(nameFood3Label,price3Label, cook3Label, rate3Label , food3ImageView, meal3);	
        meal4 = aMeal.SelectFood("3,1");
		setValue(nameFood4Label,price4Label, cook4Label, rate4Label , food4ImageView, meal4);
		meal5 = aMeal.SelectFood("4,1");
		setValue(nameFood5Label,price5Label, cook5Label, rate5Label , food5ImageView, meal5);
		meal6 = aMeal.SelectFood("5,1");
		setValue(nameFood6Label,price6Label, cook6Label, rate6Label , food6ImageView, meal6);
	}  
	
	/**
	 * Method to set the values for the main meal items on the home page		
	 * @param Name meal name label
	 * @param price of the meal; label
	 * @param cook name of the cook; label
	 * @param rate average rating of the meal; label
	 * @param picture display of meal image; label
	 * @param meal object to display
	 */
	public void setValue(Label Name, Label price, Label cook, Label rate,  Label picture, Meal meal)
	{
		
		DecimalFormat form = new DecimalFormat("$#,###.00");
		try {
			Name.setText(meal.getMealName());
			price.setText(form.format(meal.getUnitPrice()));
			cook.setText(meal.getCookName());
			rate.setText(Double.toString(meal.getRating()));
			ImageView image = new MealDAO().getImage(meal.getMealID(), 350, 150);
			picture.setGraphic(image);
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * Method to go the the mealSearch page
	 * @param e
	 */
	public void changeToTheSearchPage(ActionEvent e) {
		try {
			//Creat new FXML loader object
				FXMLLoader loader=new FXMLLoader();
				//Send to requested page
				loader.setLocation(getClass().getResource("CustomerSearch.fxml"));
				//Create parent object
				Parent parent=loader.load();
				//Create new scene object
				Scene scene=new Scene(parent);
				//Create new controller
				CustomerController controller=loader.getController();
				//Call iniData method
				controller.iniData(aCus, items);
				//controller.passCurrentOrderLine(this.items);
				Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
				//Set the scene
				stage.setScene(scene);
				//Set the title for the page
				stage.setTitle("Customer Search");
				stage.show();
			}
				//Catch exception error
		catch(Exception ex)
		{
			System.out.print("Error: " +ex.getMessage());
		}
	}
		
	/**
	 * Method to go to the customer profile
	 * @param e
	 */
	public void changeToProfilePage (ActionEvent e) {
			//Create null scene
			Scene scene = null;
			try {
				//Create FXML loader object
			FXMLLoader loader=new FXMLLoader();
			//Send to customer profile
			loader.setLocation(getClass().getResource("CustomerProfile.fxml"));
			//Create parent object
			Parent parent=loader.load();
			//Create scene object
			scene=new Scene(parent);
			//Controller object
			CustomerProfileController controller=loader.getController();
			//Call the initData object
			controller.initData(this.aCus, this.items);
			
			
		}
			//Catch exception error
			catch(Exception ex) {
				System.out.print("Error: " +ex.getMessage());
			}
			//Create stage object
			Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
			//Set the scene
			stage.setScene(scene);
			//Set the title
			stage.setTitle("Customer Profile");
			stage.show();
	}
	
	/**
	 * Method to go to the order page
	 * @param e
	 */
	public void changeToOrderpage(ActionEvent e)
	{
		//Create null scene
		Scene scene = null;
		try {
			//Create FXML loader object
			FXMLLoader loader=new FXMLLoader();
			//senf to check out page
			loader.setLocation(getClass().getResource("Checkout.fxml"));
			//Create parent object
			Parent parent =loader.load();
			// set the scene
			scene=new Scene(parent);
			//Create new controller
			OrderController controller =loader.getController();
			//Call initData method
			controller.initData(aCus, items, new Date());
			
		}
		//Catch exception errors
		catch (Exception ex) {
			System.out.print("Error: " +ex.getMessage());
		}
		//Create stage object
		Stage stage=(Stage)((Node) e.getSource()).getScene().getWindow();
		//Set the scene
		stage.setScene(scene);
		//Set the title
		stage.setTitle("Check out Page");
		stage.show();
		
	}
		
	/**
	 * go ot home page
	 * @param e
	 */
	public void changeToHomePage(ActionEvent e) {
		//Create scene as null
		Scene scene = null;
		try {
			//Create new FXML loader
		FXMLLoader loader=new FXMLLoader();
		//Send home page
		loader.setLocation(getClass().getResource("HomePage.fxml"));
		//Create new parent
		Parent parent=loader.load();
		//Set the scene
		scene= new Scene(parent);
		//Create new controller
		HomepageController controller=loader.getController();
		//Call iniData
		controller.iniData(aCus, items);

		
		} //Catch exception errors
			catch (IOException e1) {
		
			//System.out.print(e1.getMessage());
		}
		//Stage object
		Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
		//Set the scene
		stage.setScene(scene);
		//Set the title
		stage.setTitle("Home Page");
		stage.show();
		
	}
		
		/**
		 * go to the meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button1(ActionEvent e) throws Exception{
			//Create new variables
			int cookid=meal1.getCookID();
			int mealId=meal1.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("Meal.fxml"));
				//Create new parent
				Parent parent=loader.load();
				//Set the scene
				scene= new Scene(parent);
				//Create new controller
				MealPageController controller=loader.getController();
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);			
			} 
			//Catcg exception errors
			catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			//Set the scene
			stage.setScene(scene);
			//Set the title
			stage.setTitle("Meal Page");
			stage.show();		
		}
		
		/**
		 * go to meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button2(ActionEvent e) {
			//Create new variables
			int cookid=meal2.getCookID();
			int mealId=meal2.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Meal.fxml"));
			//Create and load new parent
			Parent parent=loader.load();
			//Set the scene
			scene= new Scene(parent);
			///Create new controller
			MealPageController controller=loader.getController();
			try {
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);
			} catch (Exception e1) {
			}

			//Catch exception errors and set scene and title
		} catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Meal Page");
			stage.show();
		
		}
		
		/**
		 * go to meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button3(ActionEvent e) {
			//Create new variables and store
			int cookid=meal3.getCookID();
			int mealId=meal3.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Meal.fxml"));
			//Create and load new parent
			Parent parent=loader.load();
			//Set the scene
			scene= new Scene(parent);
			///Create new controller
			MealPageController controller=loader.getController();
			try {
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);
			} catch (Exception e1) {
			}

			
		} //Catch exception errors and set scene and title
			catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Meal Page");
			stage.show();
		
		}
		
		/**
		 * go to meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button4(ActionEvent e) {
			//Create new variables and store
			int cookid=meal4.getCookID();
			int mealId=meal4.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Meal.fxml"));
			//Create and load new parent
			Parent parent=loader.load();
			//Set the scene
			scene= new Scene(parent);
			///Create new controller
			MealPageController controller=loader.getController();
			try {
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);
			} catch (Exception e1) {
			}

			
		} //Catch exception errors and set scene and title
			catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Meal Page");
			stage.show();
		}
		
		/**
		 * go to meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button5(ActionEvent e) {
			//Create new variables and store
			int cookid=meal5.getCookID();
			int mealId=meal5.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Meal.fxml"));
			//Create and load new parent
			Parent parent=loader.load();
			//Set the scene
			scene= new Scene(parent);
			///Create new controller
			MealPageController controller=loader.getController();
			try {
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);
			} catch (Exception e1) {
			}

			
			}//Catch exception errors and set scene and title 
			catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Meal Page");
			stage.show();
		}
		
		/**
		 * go to meal item
		 * @param e
		 * @throws Exception
		 */
		public void seeMoreFood1Button6(ActionEvent e) {
			//Create new variables and store
			int cookid=meal6.getCookID();
			int mealId=meal6.getMealID();
			//Create scene as null
			Scene scene = null;
			try {
				//Create new FXMLloader object
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("Meal.fxml"));
			//Create and load new parent
			Parent parent=loader.load();
			//Set the scene
			scene= new Scene(parent);
			///Create new controller
			MealPageController controller=loader.getController();
			try {
				//controller.passCurrentOrderLine(this.items);
				controller.initData(mealId,cookid, this.aCus, items);
			} catch (Exception e1) {
			}

			
		} //Catch exception errors and set scene and title
			catch (IOException e1) {}
			Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Meal Page");
			stage.show();
		}
		
		/**
		 * Method to go to the mealrequest page for a special meal request
		 * @throws IOException 
		 * @throws SQLException 
	    */
		public void specialRequests(ActionEvent e) throws IOException, SQLException {
	    	//FXML Loader defined
	    	FXMLLoader loader = new FXMLLoader();
	    	//set the scene to load
	    	loader.setLocation(getClass().getResource("CustomerMealRequest.fxml"));
	    	//find the controller class and use the initData method
	    	//set the scene/load UI
	    	Parent parent = loader.load();
			// set the scene
			Scene scene = new Scene(parent);
	    	//new SideMenuController instance
			CustomerMealRequestController controller = loader.getController();   
			controller.initData(this.aCus, this.items);
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


