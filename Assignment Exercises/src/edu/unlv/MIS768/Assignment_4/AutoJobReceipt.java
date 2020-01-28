package edu.unlv.MIS768.Assignment_4;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Joe's Automotive
 * Implementation class to run the auto job application for Joe's receipt generation
 * Date: March 30, 2019
 * @author Sia Mbatia
 */
public class AutoJobReceipt extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		//Load the FXML file
		Parent parent = FXMLLoader.load(getClass().getResource("AutoJobReceipt.fxml"));
		//set the scene to show the stage
		Scene scene = new Scene(parent);
		
		//Display our window using the scene (set the scene to stage)
		primaryStage.setTitle("Joe's Auto Shop");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
