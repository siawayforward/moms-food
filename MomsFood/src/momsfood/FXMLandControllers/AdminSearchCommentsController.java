package momsfood.FXMLandControllers;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import momsfood.classes.*;

public class AdminSearchCommentsController {
		@FXML
	    private DatePicker commentDatePicker;

	    @FXML
	    private Button searchButton;
	    
	    @FXML
	    private Button resetButton;

	    @FXML
	    private TableView<Comment> tableView;
	    
	    @FXML
	    private TableColumn<Comment, String> mealColumn;
	    
	    @FXML
	    private TableColumn<Comment, String> lastNameColumn;
	    
	    @FXML
	    private TableColumn<Comment, String> firstNameInitialColumn;

	    @FXML
	    private TableColumn<Comment, Integer> IDColumn;

	    @FXML
	    private TableColumn<Comment, String> commentColumn;

	    @FXML
	    private TableColumn<Comment, Date> dateColumn;
	    
	    @FXML
	    private Button deleteButton;

	    @FXML
	    private TextField commentSearchTextField;

	    @FXML
	    private Button logoButton;

	    @FXML
	    private TextField customerSearchTextField;

	    @FXML
	    private Button logoutButton;

	    @FXML
	    private TextField idSearchTextField;

	    @FXML
	    private Button backButton;	

	    //global comments list and admin
		ObservableList<Comment> comments = FXCollections.observableArrayList();
		Admin search = new Admin();
	/**
	 * Method to initialize page when scene loads
	 * @throws SQLException 
	 */
	public void initialize() throws SQLException {
		// set up the columns in the table
		IDColumn.setCellValueFactory(new PropertyValueFactory<Comment, Integer>("commentID"));
		firstNameInitialColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("custFirstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("custLastName"));
		mealColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("mealName"));
		commentColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("comment"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Comment, Date>("date"));
		
		//load all possible comments
		comments = search.displayAllComments();
		tableView.setItems(comments);
	}
	
	/**
	 * Search comments by specified parameters
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public void searchComments() throws NumberFormatException, SQLException {
		//If nothing is entered
		if(idSearchTextField.getText().equals("") && customerSearchTextField.getText().equals("")
				&& commentSearchTextField.getText().equals("") && commentDatePicker.getValue().toString() == null) {
			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Admin User Updates");
			alert.setContentText("Please enter a search parameter");
	    	alert.showAndWait();
			comments = search.displayAllComments();
			tableView.setItems(comments);
		}
		//call search methods depending on textfield/datepicker selected and return a list
		else if(!idSearchTextField.getText().equals("")) {
			comments = search.searchCommentsByID(Integer.parseInt(idSearchTextField.getText()));
			idSearchTextField.setText("");
			customerSearchTextField.setText("");
			commentSearchTextField.setText("");
			commentDatePicker.setValue(null);
		}			
		else if(!customerSearchTextField.getText().equals("")) {
			comments = search.searchCommentsByCustomer(customerSearchTextField.getText());
			idSearchTextField.setText("");
			customerSearchTextField.setText("");
			commentSearchTextField.setText("");
			commentDatePicker.setValue(null);
		}			
		else if(!commentSearchTextField.getText().equals("")) {
			comments = search.searchCommentsByNote(commentSearchTextField.getText());
			idSearchTextField.setText("");
			customerSearchTextField.setText("");
			commentSearchTextField.setText("");
			commentDatePicker.setValue(null);
		}			
		else if(!commentDatePicker.getValue().equals("")) {
			comments = search.searchCommentsByDate(commentDatePicker.getValue());
			idSearchTextField.setText("");
			customerSearchTextField.setText("");
			commentSearchTextField.setText("");
			commentDatePicker.setValue(null);
		}	
				
		//display the comments on the table view
		tableView.setItems(comments);
	}
	
	public void resetComments() throws SQLException {
		//empty all textfields
		idSearchTextField.setText("");
		customerSearchTextField.setText("");
		commentSearchTextField.setText("");
		commentDatePicker.setValue(null);
		//load all possible comments
		comments = search.displayAllComments();
		tableView.setItems(comments);
	}
	
	/**
	 * delete customer comments
	 * @throws SQLException
	 */
	public void deleteComments() throws SQLException {
		// get the index of the item selected in the TableView
		int selectedRow = tableView.getSelectionModel().getSelectedIndex();	
		//delete comment from database
		String confirm = "The comment could not be deleted. Please try again later.";
		boolean flag = new CommentDAO().deleteComment(tableView.getSelectionModel().getSelectedItem().getCommentID());
		if(flag) {
			confirm = "The comment was deleted";
			// remove the row
			tableView.getItems().remove(selectedRow);	
		}		
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Admin User Updates");
    	alert.setContentText(confirm);
    	alert.showAndWait();
	}
	
	/**
	 * Method to go back to the admin profile/people search section
	 * @throws IOException
	 */
	public void back(ActionEvent e) throws IOException {
    	//FXML Loader defined
    	FXMLLoader loader = new FXMLLoader();
    	//set the scene to load
    	loader.setLocation(getClass().getResource("AdminProfile.fxml"));
    	//find the controller class and use the initData method
    	Parent parent = loader.load();
		// set the scene
		Scene scene = new Scene(parent);
    	//new SideMenuController instance
		AdminProfileController controller = loader.getController();   
		//get the current window
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); 
		//set scene to stage
		stage.setScene(scene);
		//change the title to match the side menu
		stage.setTitle("Mom's Food - Admin View");
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
