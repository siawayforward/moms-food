package images;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import momsfood.classes.*;



public class loadImages {

	    
		//new SetCountryItems().getImage();
		
		
		
	  @FXML
	    private Label label;

	    @FXML
	    private Button button;

	    @FXML
	    private TextField field;
	    
	    @FXML
	    private RadioButton thing;
	    
	    @FXML
	    private ComboBox<String> items;


		public void initialize() {			
			label.setPrefSize(200, 200);
			ImageView view = new DisplayImage().getImage(3);
			view.fitHeightProperty().bind(label.heightProperty());
			view.fitWidthProperty().bind(label.widthProperty());
			label.setGraphic(view);
		}
	

}
