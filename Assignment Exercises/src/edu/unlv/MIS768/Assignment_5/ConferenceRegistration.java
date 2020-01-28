package edu.unlv.MIS768.Assignment_5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Application class that allows to load the conference registration application and set the title for the registration
 * @author Sia Mbatia
 * Date: April 8th 2019
 */
public class ConferenceRegistration extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Load the FXML file
		Parent parent = FXMLLoader.load(getClass().getResource("ConferenceRegistration.fxml"));
		//set the scene to show the stage
		Scene scene = new Scene(parent);
		
		//Display our window using the scene (set the scene to stage)
		primaryStage.setTitle("UNLV MIS Conference");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
