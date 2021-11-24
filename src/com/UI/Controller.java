package com.UI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
public class Controller {
	private Button StartButton;
	private AnchorPane scenePane;
	Stage primaryStage;

	public void Start(MouseEvent mouseEvent) {
		System.out.println("The Start Button is Working");
	}
	public void Stop(MouseEvent mouseEvent) {
		System.out.println("The Stop Button is Working");
	}
	public void Next(MouseEvent mouseEvent) {
		System.out.println("The Next Button is Working");
	}
	public void Restart(MouseEvent mouseEvent) {
		System.out.println("The Restart Button is Working");
	}
	public void Lexicon(MouseEvent mouseEvent) {
		System.out.println("The Lexicon Button is Working");
	}
	public void Manage(MouseEvent mouseEvent) {
		System.out.println("The Manage Button is Working");
	}
}
