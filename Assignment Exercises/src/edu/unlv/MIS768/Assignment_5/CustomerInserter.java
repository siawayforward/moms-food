package edu.unlv.MIS768.Assignment_5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Coffee Customer Inserter
 * Implementation class to run the customer inserter application to add a user to the database
 * Date: April 9, 2019
 * @author Sia Mbatia
 */
public class CustomerInserter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Load the FXML file
		Parent parent = FXMLLoader.load(getClass().getResource("CustomerInserter.fxml"));
		//set the scene to show the stage
		Scene scene = new Scene(parent);
		
		//Display our window using the scene (set the scene to stage)
		primaryStage.setTitle("KCDL Customer Portal");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
