package com.Main;

import com.BL.DB_I;
import com.BL.UI_I;
import com.DB.DB_SQL;
import com.DB.DB_TXT;
import com.UI.Console;
import com.UI.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class Final_Main extends Application {
	Button button = new Button("Submit");
	
	RadioButton MySql = new RadioButton("MySQL");
	RadioButton File = new RadioButton("File");
	RadioButton console = new RadioButton("Console");
	RadioButton JavaFx = new RadioButton("UI");
	
	ToggleGroup DBToggle = new ToggleGroup();
	ToggleGroup UIToggle = new ToggleGroup();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public GridPane GetOptions() {
		GridPane gridPane = new GridPane();
		
		button.setMinWidth(80);
		button.getStyleClass().removeAll();
		button.getStyleClass().add("SimpleButton");
		
		
		Label DB = new Label("Select DB:");
		Label UI = new Label("Select UI:");
		
		DB.setStyle("-fx-text-fill: #ffffff");
		UI.setStyle("-fx-text-fill: #ffffff");
		MySql.setStyle("-fx-text-fill: #ffffff");
		File.setStyle("-fx-text-fill: #ffffff");
		console.setStyle("-fx-text-fill: #ffffff");
		JavaFx.setStyle("-fx-text-fill: #ffffff");
		
		MySql.setToggleGroup(DBToggle);
		File.setToggleGroup(DBToggle);
		console.setToggleGroup(UIToggle);
		JavaFx.setToggleGroup(UIToggle);
		
		gridPane.add(DB, 0, 0, 1, 1);
		gridPane.add(MySql, 1, 0, 1, 1);
		gridPane.add(File, 2, 0, 1, 1);
		gridPane.add(UI, 0, 1, 1, 1);
		gridPane.add(console, 1, 1, 1, 1);
		gridPane.add(JavaFx, 2, 1, 1, 1);
		gridPane.add(button, 1, 3, 1, 1);
		
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(20));
		gridPane.setStyle("-fx-background-color: #484a49");
		
		return gridPane;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Choose");
		primaryStage.getIcons().add(new Image("com/Additional/Icon.png"));
		
		Scene scene = new Scene(GetOptions(), 300, 250);
		
		scene.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
		final DB_I[] db = new DB_I[1];
		final UI_I[] ui = new UI_I[1];
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (MySql.isSelected()) {
					db[0] = new DB_SQL();
				} else if (File.isSelected()) {
					db[0] = new DB_TXT();
				}
				
				
				if (console.isSelected()) {
					primaryStage.close();
					Console.main(db[0]);
				} else if (JavaFx.isSelected()) {
					try {
						primaryStage.close();
						ui[0] = new Main(db[0]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				primaryStage.close();
			}
		});
	}
}
